package com.example.expensetracker.expensedialog

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.database.ExpenseDAO
import java.lang.IllegalArgumentException

class ExpenseDialogViewModelFactory(
        private val expenseId: Long,
        private val dataSource: ExpenseDAO,
        private val application: Application) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpenseDialogViewModel::class.java)) {
            return ExpenseDialogViewModel(expenseId ,dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}