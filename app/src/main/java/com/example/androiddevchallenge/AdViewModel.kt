package com.example.androiddevchallenge

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class AdViewModel : ViewModel() {

    private val _totalTimeString = MutableLiveData("20")
    val totalTimeString: LiveData<String> = _totalTimeString
    private val _running = MutableLiveData(false)
    val running: LiveData<Boolean> = _running
    private val _time = MutableLiveData(20)
    val time: LiveData<Int> = _time

    fun onTotalTimeStringChanged(newTotalTime: String) {
        GlobalScope.launch(Dispatchers.Main) {
            _totalTimeString.value = newTotalTime
        }
    }

    fun onRunningChanged(newRunning: Boolean) {
        GlobalScope.launch(Dispatchers.Main) {
            if (newRunning) {
                onTimeChanged(totalTimeString.value?.toIntOrNull() ?: 0)
            }
            _running.value = newRunning
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
                val currentTime = time.value ?: 0
                val currentRunning = running.value ?: false
                delay(1000)
                if (currentRunning && currentTime > 0)
                    onTimeChanged(currentTime - 1)
            }
        }
    }
}