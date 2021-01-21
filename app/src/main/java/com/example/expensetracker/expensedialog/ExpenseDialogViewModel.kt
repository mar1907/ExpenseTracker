package com.example.expensetracker.expensedialog

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.expensetracker.SavedPreference
import com.example.expensetracker.database.Expense
import com.example.expensetracker.database.ExpenseDAO
import com.example.expensetracker.firebase.FirebaseDatabaseRepo
import kotlinx.coroutines.launch

class ExpenseDialogViewModel(
        private val expenseKey: Long,
        val database: ExpenseDAO,
        application: Application) : AndroidViewModel(application) {

    private val firebaseDatabase = FirebaseDatabaseRepo.instance

    val dismiss = MutableLiveData<Boolean>(false)

    // add "current" entry - for update
    private val expense: LiveData<Expense> = if (expenseKey == 0L)
        MutableLiveData<Expense>(Expense())
    else
        database.get(expenseKey)

    fun getExpense() = expense

    fun onNewExpense(currency: String) {
        // convert value using currency
        var rate = 1.0
        if (currency != "RON") {
            val context = getApplication<Application>()
            rate = when(currency) {
                "USD" -> SavedPreference.getExchangeRateUSD(context)!!.toDouble()
                "EUR" -> SavedPreference.getExchangeRateEUR(context)!!.toDouble()
                else -> 1.0
            }
        }

        val newExpense = expense.value!!
        newExpense.amount *= (1.0 / rate)

        viewModelScope.launch {
            insert(newExpense)
        }
    }

    // Insert new expense item. This may be an update - if the expenseID is not 0. If so, insert
    // a delete object and the new object.
    private suspend fun insert(newExpense: Expense) {

        if (newExpense.expenseId != 0L) {
            val delExpense = Expense(deleteId = newExpense.expenseId)
            val newId = database.insert(delExpense)
            delExpense.expenseId = newId
            firebaseDatabase.insert(delExpense)
            newExpense.expenseId = 0L
            Log.i("insert", "delete")
        }
        val newId = database.insert(newExpense)
        Log.i("insert", "insert")
        newExpense.expenseId = newId
        firebaseDatabase.insert(newExpense)
        dismiss.value = true
    }

    fun onDeleteExpense() {
        viewModelScope.launch {
            delete()
        }
    }

    // Delete an expense item. What actually happens is that a delete object is inserted.
    private suspend fun delete() {
        val delExpense = Expense(deleteId = expense.value!!.time)
        val newId = database.insert(delExpense)
        delExpense.expenseId = newId
        firebaseDatabase.insert(delExpense)
        dismiss.value = true
    }
}