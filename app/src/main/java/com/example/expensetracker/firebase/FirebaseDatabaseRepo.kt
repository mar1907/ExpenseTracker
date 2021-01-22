package com.example.expensetracker.firebase

import android.util.Log
import com.example.expensetracker.ExpenseTrackerApplication
import com.example.expensetracker.database.Expense
import com.example.expensetracker.database.ExpenseDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FirebaseDatabaseRepo {

    private val userId: String = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    private val database = FirebaseDatabase
        .getInstance("https://expense-tracker-52c53-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("users")
        .child(userId)
    private val expenseDao = ExpenseDatabase.getInstance(ExpenseTrackerApplication.instance).expenseDAO
    private val expenseDao2 = ExpenseDatabase.getInstance2(ExpenseTrackerApplication.instance).expenseDAO
    private var connected = false

    companion object {
        fun initialize() {
            instance = FirebaseDatabaseRepo()
            instance.addListeners()
        }
        lateinit var instance: FirebaseDatabaseRepo
            private set
    }

    fun insert(expense: Expense) {
        val newExpense = expense.copy()
        if (connected) {
            database
                .child(newExpense.time.toString())
                .setValue(newExpense)
        } else {
            secondaryDatabaseInsert(newExpense)
        }
    }

    private fun addListeners() {
        val expenseListListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val expenseList = snapshot.getValue<HashMap<String, Expense>>()?.toList()?.map { it.second }
                updateDatabase(expenseList)
            }

            override fun onCancelled(error: DatabaseError) {}
        }
        database.addValueEventListener(expenseListListener)

        val connectedRef = FirebaseDatabase
            .getInstance("https://expense-tracker-52c53-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference(".info/connected")
        connectedRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                connected = snapshot.getValue(Boolean::class.java) ?: false
                if (connected) {
                    updateRemoteDatabase()
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun updateDatabase(expenseList: List<Expense>?) {
        if (expenseList == null)
            return
        GlobalScope.launch {
            withContext(Dispatchers.Default) {
                val sortedList = expenseList.sortedBy { -it.time }
                val savedList = expenseDao.getExpenses().map { it.time }

                var expense : Expense
                var i = 0
                while (i < sortedList.size) {
                    expense = sortedList[i]
                    if (!savedList.contains(expense.time)) {
                        expense.expenseId = 0
                        expenseDao.insert(expense)
                    }
                    i++
                }

            }
        }
    }

    fun flushDatabase() {
        GlobalScope.launch {
            withContext(Dispatchers.Default) {
                expenseDao.deleteAll()
                expenseDao2.deleteAll()
            }
        }
    }

    private fun secondaryDatabaseInsert(expense: Expense) {
        GlobalScope.launch {
            withContext(Dispatchers.Default) {
                expense.expenseId = 0
                expenseDao2.insert(expense)
            }
        }
    }

    private fun updateRemoteDatabase() {
        GlobalScope.launch {
            withContext(Dispatchers.Default) {
                val expenseList = expenseDao2.getExpenses()
                var newExpense : Expense
                for (e in expenseList) {
                    newExpense = e.copy()
                    database
                        .child(newExpense.time.toString())
                        .setValue(newExpense)
                }
                expenseDao2.deleteAll()
            }
        }
    }

}