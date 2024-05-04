package com.dimstyl.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dimstyl.unitconverter.ui.theme.UnitConverterTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConverterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UnitConverter()
                }
            }
        }
    }
}

@Composable
fun UnitConverter() {
    var inputValue by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("0.0") }
    var inputUnit by remember { mutableStateOf("Meters") }
    var outputUnit by remember { mutableStateOf("Meters") }
    var inputExpanded by remember { mutableStateOf(false) }
    var outputExpanded by remember { mutableStateOf(false) }
    var inputConversionFactor by remember { mutableDoubleStateOf(1.00) }
    var outputConversionFactor by remember { mutableDoubleStateOf(1.00) }

    fun convertUnits() {
        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
        result =
            ((inputValueDouble * inputConversionFactor * 100.0 / outputConversionFactor).roundToInt() / 100.0).toString()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Here all UI elements will be stacked below each other
        Text(text = "Unit Converter", style = MaterialTheme.typography.displaySmall)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            value = inputValue,
            onValueChange = {
                inputValue = it
                convertUnits()
            },
            label = { Text("Enter value") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row {
            // Here all UI elements will be stacked next to each other
            // Input box
            Box {
                Button(onClick = { inputExpanded = true }) {
                    Text(inputUnit)
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Input drop down menu"
                    )
                }
                DropdownMenu(
                    expanded = inputExpanded,
                    onDismissRequest = { inputExpanded = false }) {
                    DropdownMenuItem(text = { Text("Centimeters") }, onClick = {
                        inputExpanded = false
                        inputUnit = "Centimeters"
                        inputConversionFactor = 0.01
                        convertUnits()
                    })
                    DropdownMenuItem(text = { Text("Meters") }, onClick = {
                        inputExpanded = false
                        inputUnit = "Meters"
                        inputConversionFactor = 1.0
                        convertUnits()
                    })
                    DropdownMenuItem(text = { Text("Feet") }, onClick = {
                        inputExpanded = false
                        inputUnit = "Feet"
                        inputConversionFactor = 0.3048
                        convertUnits()
                    })
                    DropdownMenuItem(text = { Text("Millimeters") }, onClick = {
                        inputExpanded = false
                        inputUnit = "Millimeters"
                        inputConversionFactor = 0.001
                        convertUnits()
                    })
                }
            }

            Spacer(modifier = Modifier.width(8.dp))
            Image(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Arrow indicating conversion from one unit to another",
                modifier = Modifier.padding(10.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))

            // Output box
            Box {
                Button(onClick = { outputExpanded = true }) {
                    Text(outputUnit)
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Output drop down menu"
                    )
                }
                DropdownMenu(
                    expanded = outputExpanded,
                    onDismissRequest = { outputExpanded = false }) {
                    DropdownMenuItem(text = { Text("Centimeters") }, onClick = {
                        outputExpanded = false
                        outputUnit = "Centimeters"
                        outputConversionFactor = 0.01
                        convertUnits()
                    })
                    DropdownMenuItem(text = { Text("Meters") }, onClick = {
                        outputExpanded = false
                        outputUnit = "Meters"
                        outputConversionFactor = 1.0
                        convertUnits()
                    })
                    DropdownMenuItem(text = { Text("Feet") }, onClick = {
                        outputExpanded = false
                        outputUnit = "Feet"
                        outputConversionFactor = 0.3048
                        convertUnits()
                    })
                    DropdownMenuItem(text = { Text("Millimeters") }, onClick = {
                        outputExpanded = false
                        outputUnit = "Millimeters"
                        outputConversionFactor = 0.001
                        convertUnits()
                    })
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Result: $result ${outputUnit.lowercase()}",
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
    UnitConverterTheme {
        UnitConverter()
    }
}