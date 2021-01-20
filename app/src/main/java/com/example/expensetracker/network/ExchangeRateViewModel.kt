package com.example.expensetracker.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ExchangeRateViewModel: ViewModel() {
    private val _response = MutableLiveData<ExchangeRateJSON>()

    val response: LiveData<ExchangeRateJSON>
        get() = _response

    init {
        getExchangeRates()
    }

    private fun getExchangeRates() {
        viewModelScope.launch {
            try {
                _response.value = ExchangeRateAPI.retrofitService.getProperties()
            } catch (e: Exception) {
                // no value, it will be computed from the last saved one
                _response.value = null
            }
        }
    }
}
