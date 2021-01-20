package com.example.expensetracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.expensetracker.databinding.ActivitySummaryBinding

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
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val currency = parent?.getItemAtPosition(position).toString()

        binding.summaryExchangeRate.text = when (currency) {
                "EUR" -> "1 EUR = ${String.format("%.3f", 1 / SavedPreference.getExchangeRateEUR(this)!!.toDouble())} RON"
                "USD" -> "1 USD = ${String.format("%.3f", 1 / SavedPreference.getExchangeRateUSD(this)!!.toDouble())} RON"
                else  ->  ""
            }

        fillSummaries(currency)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // nothing to do
    }

    private fun fillSummaries(currency: String) {
        binding.summaryFood.text = "Food: 0 $currency"
        binding.summaryClothes.text = "Clothes: 0 $currency"
        binding.summaryEntertainment.text = "Entertainment: 0 $currency"
        binding.summaryTransport.text = "Transport: 0 $currency"
        binding.summaryBills.text = "Bills: 0 $currency"
        binding.summaryOther.text = "Other: 0 $currency"

    }
}
