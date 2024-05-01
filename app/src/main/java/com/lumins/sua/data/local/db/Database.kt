package com.lumins.sua.data.local.db

import com.lumins.sua.data.model.User
import com.lumins.sua.data.model.UserBudget
import com.lumins.sua.data.model.UserExpense

internal interface Database {
    fun clearDatabase()

    fun insertUser(user: User)
    fun getUserById(userId: Int): User?

    fun insertOrReplaceBudget(budget: UserBudget)
    fun getAllBudgets(): List<UserBudget>
    fun getBudgetById(budgetId: Int): UserBudget?

    fun insertOrReplaceExpense(expense: UserExpense)
    fun getAllExpenses(): List<UserExpense>
    fun getExpensesByBudgetId(budgetId: Int): List<UserExpense>
    fun getExpenseByExpenseId(expenseId: Int): UserExpense?

    fun insertTimetable(timetable: Timetable)
    fun updateTimetable(timetable: Timetable)
    fun insertTimetables(timetables: List<Timetable>)
    fun getAllClassTimetables(): List<Timetable>
    fun getClassTimetableByName(name: String): List<Timetable>
    fun getStarredTimetable(): List<Timetable>
    fun clearTimetableDatabase()
    fun clearTimetableDatabaseByClass(className: String)
    fun getClassNames(): List<String>

    fun getChatMessages(): List<ChatMessage>
    fun insertChatMessage(chatMessage: ChatMessage)
    fun deleteChatMessage(id: Int)
    fun clearChatMessages()

    fun insertEmailAlert(emailAlert: EmailAlert)

    fun getAllEmailAlerts(): List<EmailAlert>

    fun deleteEmailAlert(emailAlert: EmailAlert)
}