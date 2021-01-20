package com.example.expensetracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.database.ExpenseDatabase
import com.example.expensetracker.databinding.ActivitySummaryBinding
import com.example.expensetracker.summary.SummaryViewModel
import com.example.expensetracker.summary.SummaryViewModelFactory

class SummaryActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivitySummaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySummaryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        this.title = "${getString(R.string.app_name)} - ${getString(R.string.summary_activity_title)}"

        binding.summarySpinner.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
                this,
                R.array.currencies,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.summarySpinner.adapter = adapter
        }

        val application = requireNotNull(this).application
        val dataSource = ExpenseDatabase.getInstance(application).expenseDAO
        val viewModelFactory = SummaryViewModelFactory(dataSource, application)
        val summaryViewModel =
            ViewModelProvider(this, viewModelFactory)
                .get(SummaryViewModel::class.java)

        summaryViewModel.summary.observe(this, Observer {
            it?.let {
                binding.summary = it
            }
        })
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val currency = parent?.getItemAtPosition(position).toString()

        binding.summaryExchangeRate.text = when (currency) {
                "EUR" -> "1 EUR = ${String.format("%.3f", 1 / SavedPreference.getExchangeRateEUR(this)!!.toDouble())} RON"
                "USD" -> "1 USD = ${String.format("%.3f", 1 / SavedPreference.getExchangeRateUSD(this)!!.toDouble())} RON"
                else  ->  ""
            }

        binding.rate = when (currency) {
            "EUR" -> SavedPreference.getExchangeRateEUR(this)!!.toDouble()
            "USD" -> SavedPreference.getExchangeRateUSD(this)!!.toDouble()
            else  -> 1.0
        }

        binding.currency = currency
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // nothing to do
    }
}
