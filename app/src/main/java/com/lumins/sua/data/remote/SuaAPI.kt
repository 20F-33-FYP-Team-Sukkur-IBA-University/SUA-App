package com.lumins.sua.data.remote

import com.lumins.sua.data.local.db.ChatMessage
import com.lumins.sua.data.model.EmailAlertKtor
import com.lumins.sua.data.model.MessageResponse
import com.lumins.sua.data.model.TimetableResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

const val API_URL = "http://128.199.130.37:3000/api/"
const val TIMETABLE_CLASS_ENDPOINT = "timetable/class/"
const val CHAT_BOT_ENDPOINT = "chatbot/chat/"
const val EMAIL_ALERT_ENDPOINT = "email-alerts/all/"

class SuaAPI {

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            })
        }
    }

    suspend fun getAllClassTimetables(): TimetableResponse =
        httpClient.get(API_URL + TIMETABLE_CLASS_ENDPOINT + "all").body()

    suspend fun getClassTimetable(className: String): TimetableResponse =
        httpClient.get(API_URL + TIMETABLE_CLASS_ENDPOINT + className).body()

    @Throws(Exception::class)
    suspend fun sendMessageToAI(className: String, chatMessages: List<ChatMessage>): MessageResponse {

        val messages = chatMessages.map { ChatBotRequestMessage(it.role!!, it.message!!) }
        val requestMessage = ChatBotRequest(className, messages)
        val response = httpClient.post{
            url(API_URL + CHAT_BOT_ENDPOINT)
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(requestMessage)
        }
        return response.body()
    }


    suspend fun getEmailAlerts(): List<EmailAlertKtor> =
        httpClient.get(API_URL + EMAIL_ALERT_ENDPOINT).body()

    @Serializable
    data class ChatBotRequest(val className: String, val messages: List<ChatBotRequestMessage>)
    @Serializable
    data class ChatBotRequestMessage(val role: String, val content: String)
}