package com.example.expensetracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Expense::class], version = 2, exportSchema = false)
abstract class ExpenseDatabase : RoomDatabase(){

    abstract val expenseDAO: ExpenseDAO

    companion object {

        @Volatile
        private var INSTANCE: ExpenseDatabase? = null
        private var INSTANCE2: ExpenseDatabase? = null

        fun getInstance(context: Context): ExpenseDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ExpenseDatabase::class.java,
                        "expense_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

        fun getInstance2(context: Context): ExpenseDatabase {
            synchronized(this) {
                var instance = INSTANCE2

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ExpenseDatabase::class.java,
                        "expense_database2"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE2 = instance
                }
                return instance
            }
        }
    }
}