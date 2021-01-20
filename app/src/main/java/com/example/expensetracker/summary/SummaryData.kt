package com.example.expensetracker.summary

data class SummaryData(
    var food: Double = 0.0,
    var clothes: Double = 0.0,
    var entertainment: Double = 0.0,
    var transport: Double = 0.0,
    var bills: Double = 0.0,
    var other: Double = 0.0,
    var currency: String = "RON"
)
