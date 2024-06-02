package com.lumins.sua.data.local.db

internal interface Database {
    fun clearDatabase()
    fun insertOrReplaceExpense(expense: UserExpense)
    fun getAllExpenses(): List<UserExpense>
    fun getExpenseById(expenseId: Int): UserExpense?
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

    fun clearAllEmailAlerts()

    fun deleteEmailAlert(emailAlert: EmailAlert)

    fun deleteExpense(expense: UserExpense)
}