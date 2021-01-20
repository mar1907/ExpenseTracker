package com.example.expensetracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.expensetracker.databinding.ActivitySummaryBinding
import java.text.SimpleDateFormat
import java.util.*

class SummaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySummaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySummaryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        this.title = "${getString(R.string.app_name)} - ${getString(R.string.summary_activity_title)}"

//        val valid_until = "2021-01-19"
//        val today = "2021-01-20"
        val sdf = SimpleDateFormat("yyyy-MM-dd")
//        binding.summaryText.text = (sdf.parse(today).after(sdf.parse(valid_until))).toString()
        binding.summaryText.text = SavedPreference.getExchangeRateEUR(this)
    }
}
