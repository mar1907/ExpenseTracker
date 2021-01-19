package com.example.expensetracker.expenselist

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.expensetracker.database.Expense
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("expenseDateFormatted")
fun TextView.setExpenseDate(item: Expense) {
    text = SimpleDateFormat("dd.MM.yyyy").format(Date(item.time))
}

@BindingAdapter("expenseAmountFormatted")
fun TextView.setExpenseAmount(item: Expense) {
    text = item.amount.toString()
}