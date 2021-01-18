package com.example.expensetracker.expensedialog

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.database.Expense
import com.example.expensetracker.database.ExpenseDAO
import kotlinx.coroutines.launch

class ExpenseDialogViewModel(
        val database: ExpenseDAO,
        application: Application) : AndroidViewModel(application) {

    // add "current" entry - for update

    // val addButtonVisible = Transformations.map(currentExpense) TODO???

    fun onNewExpense(amount: Double, comment: String, type: String) {
        viewModelScope.launch {
            val newExpense = Expense(amount = amount, comment = comment, type = type)
            insert(newExpense)
        }
    }

    // Insert new expense item. This may be an update - if the expenseID is not 0. If so, insert
    // a delete object and the new object
    private suspend fun insert(expense: Expense) {
        if (expense.expenseId != 0L) {
            val delExpense = Expense(deleteId = -expense.expenseId)
            database.insert(delExpense)
            expense.expenseId = 0L
        }
        database.insert(expense)
    }
}