package com.example.expensetracker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExpenseDAO {

    @Insert
    suspend fun insert(expense: Expense): Long

    @Query("SELECT * from expense_main_table WHERE expenseId = :key")
    fun get(key: Long): LiveData<Expense>

    @Query("SELECT * from expense_main_table ORDER BY time DESC")
    fun getAllExpenses(): LiveData<List<Expense>>

    @Query("SELECT * from expense_main_table ORDER BY time DESC")
    suspend fun getExpenses(): List<Expense>

    @Query("DELETE from expense_main_table")
    suspend fun deleteAll()

}