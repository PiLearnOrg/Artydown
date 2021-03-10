/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AdViewModel : ViewModel() {

    companion object {
        const val MAX_TOTAL_TIME = 54
    }

    private val _time = MutableLiveData(MAX_TOTAL_TIME)
    val time: LiveData<Int> = _time

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
