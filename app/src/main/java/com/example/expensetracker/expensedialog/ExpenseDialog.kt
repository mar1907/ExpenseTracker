package com.example.expensetracker.expensedialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.R
import com.example.expensetracker.database.ExpenseDatabase
import com.example.expensetracker.databinding.FragmentExpenseDialogBinding

class ExpenseDialog : DialogFragment() {

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
            // It looks like we can't directly call ViewModel functions, from XML, WITH parameters
            // taken from UI elements, this is the second best thing - it could be done (maybe) with
            // two-way data binding but seems a bit error prone - maybe refactor later.
            databaseAppend()
        }

        val application = requireNotNull(this.activity).application
        val dataSource = ExpenseDatabase.getInstance(application).expenseDAO
        val viewModelFactory = ExpenseDialogViewModelFactory(dataSource, application)
        val expenseDialogViewModel =
            ViewModelProvider(this, viewModelFactory)
                .get(ExpenseDialogViewModel::class.java)

        binding.lifecycleOwner = this

        binding.expenseDialogViewModel = expenseDialogViewModel

        return binding.root
    }

    private fun databaseAppend() {
        binding.expenseDialogViewModel?.onNewExpense(
            binding.amountEditText.text.toString().toDouble(),
            binding.commentEditText.text.toString(),
            binding.typeSpinner.text.toString()
        )

        dismiss()
    }

}