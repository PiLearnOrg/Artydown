package com.example.androiddevchallenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class AdViewModel : ViewModel() {

    companion object {
        const val MAX_TOTAL_TIME = 54;
    }

    private val _totalTime = MutableLiveData(MAX_TOTAL_TIME)
    val totalTime: LiveData<Int> = _totalTime
    private val _time = MutableLiveData(MAX_TOTAL_TIME)
    val time: LiveData<Int> = _time

    fun onTotalTimeStringChanged(newTotalTime: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            _totalTime.value = newTotalTime
            _time.value = newTotalTime.toInt()
        }
    }

    fun onTimeChanged(newTime: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            _time.value = newTime
        }
    }

    init {
        GlobalScope.launch {
            while (true) {
                delay(1000)
                val currentTime = time.value ?: 0
                if (currentTime > 0)
                    onTimeChanged(currentTime - 1)
            }
        }
    }
}