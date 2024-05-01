package com.lumins.sua.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserBudget(
    val id: Int,
    @SerialName("user_id")
    val userId: Int,
    @SerialName("budget_type")
    val budgetType: BudgetType,
    @SerialName("budget_amount")
    val budgetAmount: Double,
)

enum class BudgetType {
    FOOD, TRANSPORT
}
