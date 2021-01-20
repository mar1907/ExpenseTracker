package com.example.expensetracker

import android.app.Application

class ExpenseTrackerApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: ExpenseTrackerApplication
            private set
    }
}