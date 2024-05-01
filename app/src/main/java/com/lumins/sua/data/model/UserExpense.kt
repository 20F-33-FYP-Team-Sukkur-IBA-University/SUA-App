package com.lumins.sua.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserExpense(
    val id: Int,
    @SerialName("user_id")
    val userId: Int,
    @SerialName("budget_id")
    val budgetId: Int,
    @SerialName("expense_name")
    val expenseName: String?,
    @SerialName("expense_amount")
    val expenseAmount: Double,
    @SerialName("expense_datetime")
    val expenseDatetime: String
)
