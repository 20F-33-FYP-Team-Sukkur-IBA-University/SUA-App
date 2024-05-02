package com.lumins.sua.data.local.db

import app.cash.sqldelight.EnumColumnAdapter
import app.cash.sqldelight.adapter.primitive.IntColumnAdapter
import com.lumins.sua.SuaDatabase
import com.lumins.sua.data.local.db.ChatMessage as ModelChatMessage
import com.lumins.sua.data.local.db.EmailAlert as ModelEmailAlert
import com.lumins.sua.data.local.db.Timetable as ModelTimetable
import com.lumins.sua.data.local.db.User as ModelUser

internal class DatabaseImpl(databaseDriverFactory: DatabaseDriverFactory) : Database {
    private val database: SuaDatabase = SuaDatabase(
            databaseDriverFactory.createDriver(),
        ChatMessageAdapter= ModelChatMessage.Adapter(IntColumnAdapter),
        EmailAlertAdapter = ModelEmailAlert.Adapter(IntColumnAdapter),
        TimetableAdapter = ModelTimetable.Adapter(IntColumnAdapter),
        UserAdapter = ModelUser.Adapter(IntColumnAdapter),
        UserExpenseAdapter= UserExpense.Adapter(IntColumnAdapter, EnumColumnAdapter(), IntColumnAdapter),
        )

    private val userQueries = database.userQueries
    private val financeQueries = database.financeQueries
    private val timetableQueries = database.timetableQueries
    private val chatMessageQueries = database.chatMessageQueries
    private val emailAlertQueries = database.emailAlertQueries


    override fun clearDatabase() {
        database.transaction {
            userQueries.deleteAllUsers()
            financeQueries.deleteAllUserExpenses()
        }
    }


    override fun insertOrReplaceExpense(expense: UserExpense) {
        financeQueries.insertOrReplaceUserExpense(
            expense_type = expense.expense_type,
            name = expense.name,
            amount = expense.amount,
            datetime = expense.datetime
        )
    }

    override fun getAllExpenses(): List<UserExpense> {
        return financeQueries.getAllUserExpenses().executeAsList()
    }


    override fun getExpenseById(expenseId: Int): UserExpense? {
        return financeQueries.getUserExpenseById(expenseId).executeAsOneOrNull()
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

    override fun deleteExpense(expense: UserExpense) {
        financeQueries.deleteExpenseByExpenseId(expense.id)
    }

}
