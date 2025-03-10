package com.example.lab1

import android.os.Environment
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.math.BigDecimal

@Composable
fun AlgorithmScreen(algorithm: Algorithm) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = algorithm.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        when (algorithm) {
            is Algorithm.LinearAlgo -> {
                TwoParameterAlgorithmContent(
                    image = R.drawable.algo_linear,
                    fileName = "linear.txt",
                    param1Name = "C",
                    param2Name = "G",
                    executeAlgorithm = { c, g ->
                        try {
                            val result = algorithm.execute(BigDecimal(c), BigDecimal(g))
                            "Result: $result"
                        } catch (e: ArithmeticException) {
                            "Error: number is too big."
                        } catch (e: Exception) {
                            "Error: ${e.message ?: "Invalid input"}"
                        }
                    }
                )
            }

            is Algorithm.ConditionAlgo -> {
                TwoParameterAlgorithmContent(
                    image = R.drawable.algo_conditional,
                    fileName = "conditional.txt",
                    param1Name = "X",
                    param2Name = "Z",
                    executeAlgorithm = { x, z ->
                        try {
                            val result = algorithm.execute(x.toDouble(), z.toDouble())
                            "Result: $result"
                        } catch (e: Exception) {
                            "Error: ${e.message ?: "Invalid input"}"
                        }
                    }
                )
            }

            is Algorithm.CycleAlgo -> {
                NoParameterAlgorithmContent(image = R.drawable.algo_cycle) {
                    try {
                        val result = algorithm.execute()
                        "Result: ${result.joinToString(", ")}"
                    } catch (e: Exception) {
                        "Error: ${e.message ?: "An error occurred"}"
                    }
                }
            }
        }
    }
}

@Composable
fun ResultCard(resultText: String) {
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

@Composable
fun TwoParameterAlgorithmContent(
    @DrawableRes // we say that 'image: Int' is an resource id rather than int
    image: Int, // Pass R.drawable resource ID
    fileName: String,
    param1Name: String,
    param2Name: String,
    executeAlgorithm: (String, String) -> String
) {
    var param1 by remember { mutableStateOf("") }
    var param2 by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("") }

    fun readFromFile(fileName: String): Pair<String, String> {
        // Choose a public directory where the file will be saved, and can be accessed easily
        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName)
        val fileContent: String?

        if (file.exists()) {
            fileContent = file.readText()
        } else {
            file.createNewFile() // This will create an empty file
            throw FileNotFoundException("File not found\nNew one was created: ${file.absolutePath}")
        }

        val map = fileContent.lines().associate { line ->
            val parts = line.split("=")
            if (parts.size == 2) {
                parts[0].lowercase().trim() to parts[1].trim()
            } else {
                throw IllegalArgumentException()
            }
        }

        val param1Value = map[param1Name.lowercase()]
        val param2Value = map[param2Name.lowercase()]

        if (param1Value != null && param2Value != null) {
            return Pair(param1Value, param2Value)
        } else {
            throw IllegalArgumentException()
        }
    }

    OutlinedTextField(
        value = param1,
        onValueChange = { param1 = it },
        label = { Text(param1Name) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )

    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
        value = param2,
        onValueChange = { param2 = it },
        label = { Text(param2Name) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )

    Spacer(modifier = Modifier.height(24.dp))

    Button(
        onClick = {
            resultText = executeAlgorithm(param1, param2)
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Calculate")
    }

    Spacer(modifier = Modifier.height(8.dp))

    Button(
        onClick = {
            try {
                val (fileParam1, fileParam2) = readFromFile(fileName)
                param1 = fileParam1
                param2 = fileParam2
                resultText = executeAlgorithm(param1, param2)
            } catch (e: FileNotFoundException) {
                resultText = "Error: ${e.message}"
            } catch (e: IOException) {
                resultText = "Error: Unable to read file."
            } catch (e: IllegalArgumentException) {
                resultText = "Error: incorrect data format. Try: <parameter-name>=<value> (e.g. 'c=15')"
            } catch (e: Exception) {
                resultText = "Error: Unexpected error occurred."
            }
        },

        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Read from file")
    }

    Spacer(modifier = Modifier.height(24.dp))

    if (resultText.isNotEmpty()) {
        ResultCard(resultText)
    }
    Image(
        painter = painterResource(image),
        contentDescription = "Algorithm image",
        modifier = Modifier
            .size(250.dp)
    )
}

@Composable
fun NoParameterAlgorithmContent(
    @DrawableRes
    image: Int,
    executeAlgorithm: () -> String
) {
    var resultText by remember { mutableStateOf("") }

    Text(
        text = "This algorithm doesn't require any parameters.",
        fontSize = 14.sp,
        modifier = Modifier.padding(vertical = 8.dp)
    )

    Spacer(modifier = Modifier.height(16.dp))

    Button(
        onClick = {
            resultText = executeAlgorithm()
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Run Algorithm")
    }

    Spacer(modifier = Modifier.height(24.dp))

    if (resultText.isNotEmpty()) {
        ResultCard(resultText)
    }

    Spacer(modifier = Modifier.height(24.dp))

    Image(
        painter = painterResource(image),
        contentDescription = "Algorithm image",
        modifier = Modifier
            .size(250.dp)
    )
}