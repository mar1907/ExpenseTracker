package com.example.expensetracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Summary : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        this.setTitle("${getString(R.string.app_name)} - ${getString(R.string.summary_activity_title)}")
    }
}
