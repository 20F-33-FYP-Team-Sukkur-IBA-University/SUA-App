package com.lumins.sua.repo

import com.lumins.sua.data.local.db.ChatMessage
import com.lumins.sua.data.local.db.DatabaseDriverFactory
import com.lumins.sua.data.local.db.DatabaseImpl
import com.lumins.sua.data.local.db.EmailAlert
import com.lumins.sua.data.local.db.Timetable
import com.lumins.sua.data.remote.SuaAPI
import kotlinx.datetime.Clock

class SuaRepository(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = DatabaseImpl(databaseDriverFactory)
    private val api = SuaAPI()

    companion object {
        const val LINKS_DELIMITER = "=|==|="
    }

    // #################### Timetable #################### \\
    @Throws(Exception::class)
    suspend fun getAllClassTimetables(forcedReload: Boolean): List<Timetable> {
        val cached = database.getAllClassTimetables()
        if (cached.isEmpty() || forcedReload) {
            val apiData = api.getAllClassTimetables()
            database.clearTimetableDatabase()
            val timetables = mutableListOf<Timetable>()
            apiData.timeTable.forEach { timetable ->
                val className = timetable.className
                timetable.courses.forEach { course ->
                    timetables.add(
                        Timetable(
                            id = -1,
                            class_ = className,
                            course = course.courseName,
                            time = course.time,
                            day = course.day,
                            room = course.room,
                            teacher = course.teacher,
                            starred = false
                        )
                    )
                }
            }
            database.insertTimetables(timetables)

            return timetables
        }
        return cached
    }

    @Throws(Exception::class)
    suspend fun getClassTimetableByName(forcedReload: Boolean, className: String): List<Timetable> {
        val cached = database.getClassTimetableByName(className)
        if (cached.isEmpty() || forcedReload) {
            val apiData = api.getAllClassTimetables();
            database.clearTimetableDatabase()
            val timetables = mutableListOf<Timetable>()

            apiData.timeTable.forEach { timetable ->
                val class_ = timetable.className
                timetable.courses.forEach { course ->
                    timetables.add(
                        Timetable(
                            id = -1,
                            class_ = class_,
                            course = course.courseName,
                            time = course.time,
                            day = course.day,
                            room = course.room,
                            teacher = course.teacher,
                            starred = false
                        )
                    )
                }
            }
            database.insertTimetables(timetables)

            return timetables.filter { it.class_ == className }
        }
        return cached
    }


    @Throws(Exception::class)
    suspend fun getClassNames(forcedReload: Boolean): List<String> {
        val cached = database.getClassNames()
        if (cached.isEmpty() || forcedReload) {
            val apiData = api.getAllClassTimetables()
            database.clearTimetableDatabase()
            val timetables = mutableListOf<Timetable>()
            apiData.timeTable.forEach { timetable ->
                val class_ = timetable.className
                timetable.courses.forEach { course ->
                    timetables.add(
                        Timetable(
                            id = -1,
                            class_ = class_,
                            course = course.courseName,
                            time = course.time,
                            day = course.day,
                            room = course.room,
                            teacher = course.teacher,
                            starred = false
                        )
                    )
                }
            }
            database.insertTimetables(timetables)

            return timetables.map { it.class_ ?: "" }
        }
        return cached
    }


    @Throws(Exception::class)
    fun getStarredTimetables(): List<Timetable> {
        return database.getStarredTimetable()
    }

    @Throws(Exception::class)
    fun updateTimetable(timetable: Timetable) {
        database.updateTimetable(timetable)
    }

    // #################### ChatBot #################### \\

    fun insertChatMessage(chatMessage: ChatMessage) {
        database.insertChatMessage(chatMessage)
    }

    fun getDbChatMessages() : List<ChatMessage> {
        return database.getChatMessages()
    }

    suspend fun sendChatMessage(className: String, messages: List<ChatMessage>): Boolean {
        return try {
            val response = api.sendMessageToAI(className, messages)
            val chatMessage = ChatMessage(0, message = response.message, role = response.role)
            database.insertChatMessage(chatMessage)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun clearChatMessages() {
        database.clearChatMessages()
    }

    // #################### Finance #################### \\


    // #################### EmailAlerts #################### \\

    suspend fun getEmailAlerts(forcedReload: Boolean): List<EmailAlert> {
        val cached = database.getAllEmailAlerts()
        if (cached.isEmpty() || forcedReload) {
            val alerts = api.getEmailAlerts()

            alerts.forEach { alert
                ->
                database.insertEmailAlert(
                    EmailAlert(
                        id = -1,
                        email = alert.from,
                        subject = alert.subject,
                        body = alert.body,
                        links = alert.links.joinToString(LINKS_DELIMITER),
                        alertTime = Clock.System.now().toEpochMilliseconds().toString()
                    )
                )
            }
        }
        return database.getAllEmailAlerts()
    }

    suspend fun deleteEmailAlert(emailAlert: EmailAlert) {
        database.deleteEmailAlert(emailAlert)
    }
}