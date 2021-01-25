package com.example.expensetracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense_main_table")
data class Expense (
    @PrimaryKey(autoGenerate = true)
    var expenseId: Long = 0L,

    @ColumnInfo(name = "amount")
    var amount: Double = 0.0,

    @ColumnInfo(name = "type")
    var type: String = "",

    @ColumnInfo(name = "comment")
    var comment: String = "",

    @ColumnInfo(name = "time")
    var time: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "delete_id")
    val deleteId: Long = 0L
)