package com.example.expensetracker.expenselist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.database.ExpenseDAO
import java.lang.IllegalArgumentException

class ExpenseListViewModelFactory (
    private val dataSource: ExpenseDAO,
    private val application: Application) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ExpenseListViewModel::class.java)) {
                return ExpenseListViewModel(dataSource, application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}