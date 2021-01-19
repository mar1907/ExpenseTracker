package com.example.expensetracker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.expensetracker.network.ExchangeRateAPI
import com.example.expensetracker.network.ExchangeRateCurrency
import com.example.expensetracker.network.ExchangeRateJSON
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel: ViewModel() {
    private val _response = MutableLiveData<ExchangeRateJSON>()

    val response: LiveData<ExchangeRateJSON>
        get() = _response

    init {
        getExchangeRates()
    }

    private fun getExchangeRates() {
        ExchangeRateAPI.retrofitService.getProperties().enqueue(
            object: Callback<ExchangeRateJSON> {
                override fun onResponse(call: Call<ExchangeRateJSON>, response: Response<ExchangeRateJSON>) {
                    _response.value = response.body()
                }

                override fun onFailure(call: Call<ExchangeRateJSON>, t: Throwable) {
                    // default value, legit old data from 2021-01-19
                    _response.value = ExchangeRateJSON(ExchangeRateCurrency(0.2051534548, 0.2488921713), "RON", date="2021-01-19")
                }
            })
    }
}