package com.example.expensetracker.expenselist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.database.Expense
import com.example.expensetracker.databinding.ListItemExpenseBinding

class ExpenseAdapter(val clickListener: ExpenseListener) : ListAdapter<Expense, ExpenseAdapter.ViewHolder>(ExpenseDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItemExpenseBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(
            item: Expense,
            clickListener: ExpenseListener
        ) {
            binding.expense = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemExpenseBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

}

class ExpenseDiffCallback : DiffUtil.ItemCallback<Expense>() {
    override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean {
        return oldItem.expenseId == newItem.expenseId
    }

    override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean {
        return oldItem == newItem
    }

}

class ExpenseListener(val clickListener: (expenseId: Long) -> Unit) {
    fun onClick(expense: Expense) = clickListener(expense.expenseId)
}