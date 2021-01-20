package com.example.expensetracker.expensedialog

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.expensetracker.database.Expense
import com.example.expensetracker.database.ExpenseDAO
import kotlinx.coroutines.launch

class ExpenseDialogViewModel(
        private val expenseKey: Long,
        val database: ExpenseDAO,
        application: Application) : AndroidViewModel(application) {

    // add "current" entry - for update
    private val expense: LiveData<Expense> = if (expenseKey == 0L)
        MutableLiveData<Expense>(Expense())
    else
        database.get(expenseKey)

    fun getExpense() = expense

    lateinit var actualExpense: Expense

    // val addButtonVisible = Transformations.map(currentExpense) TODO???

    fun onNewExpense(currency: String) {
        // convert value using currency
        viewModelScope.launch {
            insert()
        }
    }

    // Insert new expense item. This may be an update - if the expenseID is not 0. If so, insert
    // a delete object and the new object.
    private suspend fun insert() {
        val newExpense = expense.value!!

        if (newExpense.expenseId != 0L) {
            val delExpense = Expense(deleteId = newExpense.expenseId)
            database.insert(delExpense)
            newExpense.expenseId = 0L
        }
        database.insert(newExpense)
    }

    fun onDeleteExpense() {
        viewModelScope.launch {
            delete()
        }
    }

    // Delete an expense item. What actually happens is that a delete object is inserted.
    private suspend fun delete() {
        val delExpense = Expense(deleteId = expense.value!!.expenseId)
        database.insert(delExpense)
    }
}