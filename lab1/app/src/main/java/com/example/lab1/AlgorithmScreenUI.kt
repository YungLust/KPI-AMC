package com.example.lab1

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SortingScreen(
    screenTitle: String,
    Algorithm: (IntArray) -> IntArray
) {
    var inputText by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("") }
    var errorText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = screenTitle,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Enter numbers separated by commas (e.g., 5,3,8,1,2)",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = inputText,
            onValueChange = {
                inputText = it
                errorText = ""
            },
            label = { Text("Input Array") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        if (errorText.isNotEmpty()) {
            Text(
                text = errorText,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                try {
                    // Parse input string to array of integers
                    val inputArray = inputText.split(",").map { it.trim().toInt() }.toIntArray()

                    // Apply sorting algorithm
                    val sortedArray = sortingAlgorithm(inputArray)

                    // Display result
                    resultText = "Sorted Array: ${sortedArray.joinToString(", ")}"
                    errorText = ""
                } catch (e: Exception) {
                    errorText = "Invalid input. Please enter numbers separated by commas."
                    resultText = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sort")
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (resultText.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Result",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = resultText)
                }
            }
        }
    }
}