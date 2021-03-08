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

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.androiddevchallenge.ui.theme.AdTheme
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

class MainActivity : AppCompatActivity() {

    private val viewModel: AdViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdTheme {
                AdScreen(
                    viewModel.totalTimeString.observeAsState(initial = "0").value,
                    viewModel::onTotalTimeStringChanged,
                    viewModel.running.observeAsState(initial = false).value,
                    viewModel::onRunningChanged,
                    viewModel.time.observeAsState(initial = 0).value
                )
            }
        }
    }
}

@Composable
fun AdScreen(
    totalTimeString: String,
    onTotalTimeChange: (String) -> Unit,
    running: Boolean,
    onRunningChange: (Boolean) -> Unit,
    time: Int
) {
    Surface(color = MaterialTheme.colors.background) {
        Column(
            Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = totalTimeString,
                onValueChange = { onTotalTimeChange(it) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = {
                    Text("Time")
                })
            Button(onClick = { onRunningChange(!running) }) {
                if (running) Text("Pause")
                else Text("Start")
            }
            Text(text = "$time")
        }
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    val viewModel: AdViewModel = AdViewModel()
    AdTheme {
        AdScreen(
            viewModel.totalTimeString.observeAsState(initial = "0").value,
            viewModel::onTotalTimeStringChanged,
            viewModel.running.observeAsState(initial = false).value,
            viewModel::onRunningChanged,
            viewModel.time.observeAsState(initial = 0).value
        )
    }
}
