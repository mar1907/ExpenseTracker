package com.example.expensetracker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExpenseDAO {

    @Insert
    suspend fun insert(expense: Expense)

    @Query("SELECT * from expense_main_table WHERE expenseId = :key")
    suspend fun get(key: Long): Expense

    @Query("SELECT * from expense_main_table ORDER BY time DESC")
    fun getAllExpenses(): LiveData<List<Expense>>

    //others
}