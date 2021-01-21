package com.example.expensetracker.expensedialog

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.R
import com.example.expensetracker.database.ExpenseDatabase
import com.example.expensetracker.databinding.FragmentExpenseDialogBinding

class ExpenseDialog private constructor(): DialogFragment() {

    private lateinit var binding: FragmentExpenseDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExpenseDialogBinding.inflate(inflater, container, false)

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        binding.addButton.setOnClickListener {
            databaseAppend()
        }

        val expenseId = requireArguments().getLong("expenseId", 0)

        val application = requireNotNull(this.activity).application
        val dataSource = ExpenseDatabase.getInstance(application).expenseDAO
        val viewModelFactory = ExpenseDialogViewModelFactory(expenseId, dataSource, application)
        val expenseDialogViewModel =
            ViewModelProvider(this, viewModelFactory)
                .get(ExpenseDialogViewModel::class.java)

        binding.lifecycleOwner = this

        binding.expenseDialogViewModel = expenseDialogViewModel

        expenseDialogViewModel.getExpense().observe(this, Observer {
            it?.let {
                binding.expense = it
            }
        })

        binding.addButton.text = if (expenseId == 0L)
            "Add"
        else
            "Update"

        if (expenseId != 0L) {
            binding.deleteButton.visibility = View.VISIBLE
            binding.deleteButton.setOnClickListener {
                databaseDelete()
            }
        }

        expenseDialogViewModel.dismiss.observe(this, Observer {
            it?.let {
                if (it) dismiss()
            }
        })

        return binding.root
    }

    // not added to the layout XML file because we need to also dismiss the dialog
    private fun databaseAppend() {
        binding.expenseDialogViewModel?.onNewExpense(binding.currencySpinner.selectedItem.toString())
//        dismiss()
    }

    // not added to the layout XML file because we need to also dismiss the dialog
    private fun databaseDelete() {
        binding.expenseDialogViewModel?.onDeleteExpense()
//        dismiss()
    }

    companion object {
        fun newInstance(expenseId: Long) : ExpenseDialog {
            val dialog = ExpenseDialog()
            val args = Bundle()
            args.putLong("expenseId", expenseId)
            dialog.arguments = args
            return dialog
        }
    }

}