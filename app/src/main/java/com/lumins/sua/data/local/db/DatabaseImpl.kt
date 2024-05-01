package com.lumins.sua.data.local.db

import app.cash.sqldelight.EnumColumnAdapter
import app.cash.sqldelight.adapter.primitive.IntColumnAdapter
import com.lumins.sua.SuaDatabase
import com.lumins.sua.data.model.BudgetType
import com.lumins.sua.data.local.db.User as ModelUser
import com.lumins.sua.data.local.db.UserBudget as ModelUserBudget
import com.lumins.sua.data.local.db.UserExpense as ModelUserExpense
import com.lumins.sua.data.local.db.Timetable as ModelTimetable
import com.lumins.sua.data.local.db.EmailAlert as ModelEmailAlert
import com.lumins.sua.data.local.db.ChatMessage as ModelChatMessage
import com.lumins.sua.data.model.User
import com.lumins.sua.data.model.UserBudget
import com.lumins.sua.data.model.UserExpense

internal class DatabaseImpl(databaseDriverFactory: DatabaseDriverFactory) : Database {
    private val database: SuaDatabase = SuaDatabase(
            databaseDriverFactory.createDriver(),
        ChatMessageAdapter= ModelChatMessage.Adapter(IntColumnAdapter),
        EmailAlertAdapter = ModelEmailAlert.Adapter(IntColumnAdapter),
        TimetableAdapter = ModelTimetable.Adapter(IntColumnAdapter),
        UserAdapter = ModelUser.Adapter(IntColumnAdapter),
        UserBudgetAdapter = ModelUserBudget.Adapter(IntColumnAdapter,IntColumnAdapter,EnumColumnAdapter()),
        UserExpenseAdapter= ModelUserExpense.Adapter(IntColumnAdapter,IntColumnAdapter,IntColumnAdapter),
        )

    private val userQueries = database.userQueries
    private val financeQueries = database.financeQueries
    private val timetableQueries = database.timetableQueries
    private val chatMessageQueries = database.chatMessageQueries
    private val emailAlertQueries = database.emailAlertQueries


    override fun clearDatabase() {
        database.transaction {
            userQueries.deleteAllUsers()
            financeQueries.deleteAllBudgets()
            financeQueries.deleteAllUserExpenses()
        }
    }

    override fun insertUser(user: User) {
        val userId = if (user.id == -1) null else user.id
        userQueries.insertOrReplaceUser(userId, user.name)
    }

    override fun getUserById(userId: Int): User? {
        return userQueries.getUserById(userId, ::mapUser).executeAsOneOrNull()
    }

    override fun insertOrReplaceBudget(budget: UserBudget) {
        val budgetId = if (budget.id == -1) null else budget.id
        financeQueries.insertOrReplaceUserBudget(
            budgetId,
            budget.userId,
            budget.budgetType,
            budget.budgetAmount
        )
    }

    override fun getAllBudgets(): List<UserBudget> {
        return financeQueries.getAllBudgets(::mapBudget).executeAsList()
    }


    override fun getBudgetById(budgetId: Int): UserBudget? {
        return financeQueries.getUserBudgetById(budgetId, ::mapBudget).executeAsOneOrNull()
    }

    override fun insertOrReplaceExpense(expense: UserExpense) {
        financeQueries.insertOrReplaceUserExpense(
            budget_id = expense.budgetId,
            user_id = expense.userId,
            expense_name = expense.expenseName,
            expense_amount = expense.expenseAmount,
            expense_datetime = expense.expenseDatetime
        )
    }

    override fun getAllExpenses(): List<UserExpense> {
        return financeQueries.getAllUserExpenses(::mapExpense).executeAsList()
    }


    override fun getExpensesByBudgetId(budgetId: Int): List<UserExpense> {
        return financeQueries.getUserExpenseByBudgetId(budgetId, ::mapExpense).executeAsList()
    }

    override fun getExpenseByExpenseId(expenseId: Int): UserExpense? {
        return financeQueries.getUserExpenseById(expenseId, ::mapExpense).executeAsOneOrNull()
    }

    override fun insertTimetable(timetable: ModelTimetable) {
        return timetableQueries.insertTimetable(
            class_ = timetable.class_,
            course = timetable.course,
            time = timetable.time,
            day = timetable.day,
            room = timetable.room,
            teacher = timetable.teacher,
            starred = false
        )
    }

    override fun insertTimetables(timetables: List<ModelTimetable>) {
        timetableQueries.transaction {
            timetables.forEach { timetable ->
                insertTimetable(timetable)
            }
        }
    }

    override fun updateTimetable(timetable: ModelTimetable) {
        return timetableQueries.updateTimetable(
            id = timetable.id,
            class_ = timetable.class_,
            course = timetable.course,
            time = timetable.time,
            day = timetable.day,
            room = timetable.room,
            teacher = timetable.teacher,
            starred = timetable.starred
        )
    }

    override fun getStarredTimetable(): List<ModelTimetable> {
        return timetableQueries.getStarredTimetables().executeAsList()
    }

    override fun getAllClassTimetables(): List<ModelTimetable> {
        return timetableQueries.getTimetableForAllClasses().executeAsList()
    }

    override fun getClassTimetableByName(name: String): List<ModelTimetable> {
        return timetableQueries.getTimetableForClass(name).executeAsList()
    }

    override fun clearTimetableDatabase() {
        return timetableQueries.clearTimetableDatabase()
    }

    override fun clearTimetableDatabaseByClass(className: String) {
        return timetableQueries.clearTimetableDatabaseByClass(className)
    }

    override fun getClassNames(): List<String> {
        return timetableQueries.getClassNames().executeAsList().map { it.class_ ?: "" }
    }

    // ############## ChatBot Queries ############## \\

    override fun getChatMessages(): List<ModelChatMessage> {
        return chatMessageQueries.getChatMessages().executeAsList()
    }

    override fun insertChatMessage(chatMessage: ModelChatMessage) {
        chatMessageQueries.insertChatMessage(
            message = chatMessage.message,
            role = chatMessage.role
        )
    }

    override fun deleteChatMessage(id: Int) {
        chatMessageQueries.deleteChatMessage(id)
    }

    override fun clearChatMessages() {
        chatMessageQueries.clearChatMessages()
    }

    // ############## Email Alert Queries ############## \\

    override fun insertEmailAlert(emailAlert: ModelEmailAlert) {
        emailAlertQueries.insertEmailAlert(
            email = emailAlert.email,
            subject = emailAlert.subject,
            body = emailAlert.body,
            links = emailAlert.links,
            alertTime = emailAlert.alertTime
        )
    }

    override fun getAllEmailAlerts(): List<ModelEmailAlert> {
        return emailAlertQueries.getAllEmailAlerts().executeAsList()
    }

    override fun deleteEmailAlert(emailAlert: ModelEmailAlert) {
        emailAlertQueries.deleteEmailAlert(emailAlert.id)
    }

    // ############## MAPPING FUNCTIONS ############## \\

    private fun mapUser(id: Int, name: String) = User(id, name)

    private fun mapBudget(
        id: Int,
        userId: Int,
        budgetType: BudgetType,
        amount: Double
    ): UserBudget {
        return UserBudget(id, userId, budgetType, amount)
    }

    private fun mapExpense(
        id: Int,
        userId: Int,
        budgetId: Int,
        expenseName: String?,
        expenseAmount: Double,
        expenseDatetime: String
    ): UserExpense {
        return UserExpense(
            id = id,
            userId = userId,
            budgetId = budgetId,
            expenseName = expenseName,
            expenseAmount = expenseAmount,
            expenseDatetime = expenseDatetime
        )
    }

}
