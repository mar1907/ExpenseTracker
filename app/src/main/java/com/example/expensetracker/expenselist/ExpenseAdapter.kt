package com.example.expensetracker.expenselist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.R
import com.example.expensetracker.TextItemViewHolder
import com.example.expensetracker.database.Expense

class ExpenseAdapter: RecyclerView.Adapter<TextItemViewHolder>() {
    var data = listOf<Expense>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.amount.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.expense_item_view, parent, false) as TextView

        return TextItemViewHolder(view)
    }

}