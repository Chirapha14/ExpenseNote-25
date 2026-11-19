package com.example.expensenote_25

data class ExpenseItem(
    val id: Long = 0,
    val title: String,
    val amount: Double,
    val type: String, // "INCOME" or "EXPENSE"
    val category: String,
    val date: String
)
