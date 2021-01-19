package com.example.expensetracker.network

data class ExchangeRateJSON (
    val rates: ExchangeRateCurrency,
    val base: String,
    val date: String
)

data class ExchangeRateCurrency(
    val EUR: Double,
    val USD: Double
)
