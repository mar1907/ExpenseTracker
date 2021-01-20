@file:Suppress("DEPRECATION")

package com.example.expensetracker

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.expensetracker.network.ExchangeRateCurrency
import com.example.expensetracker.network.ExchangeRateJSON

object SavedPreference {
    const val EMAIL = "email"
    const val USERNAME = "username"
    const val EXCHANGE_RATE_DATE = "1996-07-19"
    const val EXCHANGE_RATE_EUR = "1"
    const val EXCHANGE_RATE_USD = "1"

    private  fun getSharedPreference(ctx: Context?): SharedPreferences? {
        return PreferenceManager.getDefaultSharedPreferences(ctx)
    }

    private fun editor(context: Context, const:String, string: String){
        getSharedPreference(
            context
        )?.edit()?.putString(const,string)?.apply()
    }

    fun getEmail(context: Context)= getSharedPreference(
        context
    )?.getString(EMAIL,"")

    fun setEmail(context: Context, email: String){
        editor(
            context,
            EMAIL,
            email
        )
    }

    fun getUsername(context: Context) = getSharedPreference(
        context
    )?.getString(USERNAME,"")

    fun setUsername(context: Context, username: String){
        editor(
            context,
            USERNAME,
            username
        )
    }

    fun getExchangeRateDate(context: Context) = getSharedPreference(
        context
    )?.getString(EXCHANGE_RATE_DATE,"")

    fun setExchangeRateDate(context: Context, exchangeRateDate: String) {
        editor(
            context,
            EXCHANGE_RATE_DATE,
            exchangeRateDate
        )
    }

    fun getExchangeRateEUR(context: Context) = getSharedPreference(
        context
    )?.getString(EXCHANGE_RATE_EUR,"")

    fun setExchangeRateEUR(context: Context, exchangeRateEUR: String) {
        editor(
            context,
            EXCHANGE_RATE_EUR,
            exchangeRateEUR
        )
    }

    fun getExchangeRateUSD(context: Context) = getSharedPreference(
        context
    )?.getString(EXCHANGE_RATE_USD,"")

    fun setExchangeRateUSD(context: Context, exchangeRateUSD: String) {
        editor(
            context,
            EXCHANGE_RATE_USD,
            exchangeRateUSD
        )
    }

}