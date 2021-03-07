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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Button
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.viewmodle.ClockViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.color_50
import com.example.androiddevchallenge.ui.theme.color_cc

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {
    Surface(color = MaterialTheme.colors.background) {
        Column {
            TopAppBar(
                elevation = 4.dp,
                contentPadding = PaddingValues(top = 20.dp, start = 10.dp),
            ) {
                Text(
                    text = "CountDownTimer",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Timer()
        }
    }
}

@Composable
fun Timer() {
    val clockViewModel: ClockViewModel = viewModel()
    var textInput by remember { mutableStateOf("") }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(Color(0xFFDBDDEB))
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.Center,
            ) {
                OutProgress(clockViewModel)
                Clock(clockViewModel)
            }
            Spacer(modifier = Modifier.height(60.dp))
            TextField(
                value = textInput,
                onValueChange = { textInput = it },
                label = { Text("Input Time") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.width(280.dp)
            )
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.width(280.dp),
            ) {
                Button(
                    onClick = { clockViewModel.start(textInput.toLong()) },
                    modifier = Modifier.size(100.dp, 50.dp),
                ) {
                    Text("Start")
                }
                Button(
                    onClick = { clockViewModel.cancel() },
                    modifier = Modifier.size(100.dp, 50.dp),
                ) {
                    Text("Cancel")
                }
            }
        }

    }
}


@Composable
fun Clock(viewModel: ClockViewModel) {
    val time by viewModel.time.observeAsState(initial = 0)
    Box(
        modifier = Modifier
            .size(200.dp)
            .background(color = color_cc, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$time",
            color = Color.Black,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun OutProgress(clockViewModel: ClockViewModel) {
    val progress by clockViewModel.progress.observeAsState(initial = 0f)
    CircularProgressIndicator(
        progress = progress,
        strokeWidth = 10.dp,
        modifier = Modifier.size(250.dp),
        color = color_50
    )
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}
