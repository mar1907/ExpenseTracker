package com.example.expensetracker.expenselist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Transformations
import com.example.expensetracker.database.Expense
import com.example.expensetracker.database.ExpenseDAO

class ExpenseListViewModel(
        val database: ExpenseDAO,
        application: Application) : AndroidViewModel(application) {

    private val expenses = database.getAllExpenses()

    //function to change the list (delete stuff)
    val actualExpenses = Transformations.map(expenses) {
            expenses -> formatExpenses(expenses)
    }

    // Get a list of all elements, then change the list by applying delete
    // operations of elements (through a map). Return a list sorted by time.
    private fun formatExpenses(expenseList: List<Expense>) : List<Expense> {
        val expenseMap: MutableMap<Long, Expense> = mutableMapOf()
        for (e in expenseList) {
            if (e.deleteId == 0L) {
                expenseMap[e.expenseId] = e
            }
            else {
                expenseMap.remove(e.deleteId)
            }
        }

        val finalList = expenseMap.toList().map { it.second}
        return finalList.sortedBy { it.time }
    }
}