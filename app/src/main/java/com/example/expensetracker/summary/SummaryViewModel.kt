package com.example.expensetracker.summary

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.expensetracker.SavedPreference
import com.example.expensetracker.database.Expense
import com.example.expensetracker.database.ExpenseDAO
import kotlinx.coroutines.launch
import kotlin.math.exp

class SummaryViewModel(
    val database: ExpenseDAO,
    application: Application) : AndroidViewModel(application) {

    private var expenses = database.getAllExpenses()

    val summary = Transformations.map(expenses) {
            expenses -> fillSummary(expenses)
    }

    private fun fillSummary(expenses: List<Expense>) : SummaryData{
        Log.i("log", "changed")
        val actualList = formatExpenses(expenses)
        val newSummary = SummaryData()
        for (e in actualList) {
            when (e.type) {
                "Food" -> newSummary.food += e.amount
                "Clothes" -> newSummary.clothes += e.amount
                "Entertainment" -> newSummary.entertainment += e.amount
                "Transport" -> newSummary.transport += e.amount
                "Bills" -> newSummary.bills += e.amount
                "Other" -> newSummary.other += e.amount
            }
        }

        return newSummary
    }

    // Get a list of all elements, then change the list by applying delete
    // operations of elements (through a map). Return a list sorted by time.
    private fun formatExpenses(expenseList: List<Expense>) : List<Expense> {
        val expenseMap: MutableMap<Long, Expense> = mutableMapOf()
        val sortedList = expenseList.sortedBy { it.time }
        for (e in sortedList) {
            if (e.deleteId == 0L) {
                expenseMap[e.time] = e
            }
            else {
                expenseMap.remove(e.deleteId)
            }
        }

        val finalList = expenseMap.toList().map { it.second}
        return finalList.sortedBy { it.time }
    }
}