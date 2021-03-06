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
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.androiddevchallenge.ui.theme.AdTheme

class MainActivity : AppCompatActivity() {

    private val viewModel: AdViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdTheme {
                AdScreen(
                    viewModel::onTimeChanged,
                    viewModel.time.observeAsState(initial = AdViewModel.MAX_TOTAL_TIME).value
                )
            }
        }
    }
}

@Composable
fun AdScreen(
    onTotalTimeChange: (Int) -> Unit,
    time: Int
) {
    Surface(color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            IntegerSlider(
                value = time,
                onValueChange = { onTotalTimeChange(it) },
                valueRange = 0..AdViewModel.MAX_TOTAL_TIME
            )
            Image(
                modifier = Modifier.fillMaxWidth(),
                alignment = Alignment.Center,
                contentScale = ContentScale.FillWidth,
                painter = painterResource(id = R.drawable.hourglass00 + time),
                contentDescription = "Hourglass $time visual representation"
            )
        }
    }
}

@Composable
fun IntegerSlider(
    value: Int,
    onValueChange: (Int) -> Unit,
    valueRange: IntRange = 0..100,
) {
    Slider(
        value = value.toFloat(),
        onValueChange = { onValueChange(it.toInt()) },
        valueRange = valueRange.first.toFloat()..valueRange.last.toFloat()
    )
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    val viewModel: AdViewModel = AdViewModel()
    AdTheme {
        AdScreen(
            viewModel::onTimeChanged,
            viewModel.time.observeAsState(initial = 0).value
        )
    }
}
