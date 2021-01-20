package com.example.expensetracker

import android.content.res.Resources
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseMethod
import com.example.expensetracker.database.Expense
import java.lang.NumberFormatException
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

@InverseMethod("textToAmount")
fun amountToText(amount: Double): String {
    return if (amount == 0.0)
        ""
    else
        amount.toString()
}

fun textToAmount(amount: String): Double {
    val result: Double
    return try {
        result = amount.toDouble()
        result
    } catch (e: NumberFormatException) {
        0.0
    }
}

@InverseMethod("indexToType")
fun typeToIndex(type: String?): Int {
    if (type == null)
        return 0
    val context = ExpenseTrackerApplication.instance
    val typeList = context.resources.getStringArray(R.array.types)
    val index = typeList.indexOf(type)
    return if (index == -1)
        0
    else
        index
}

fun indexToType(index: Int): String {
    val context = ExpenseTrackerApplication.instance
    val typeList = context.resources.getStringArray(R.array.types)
    return typeList[index]
}