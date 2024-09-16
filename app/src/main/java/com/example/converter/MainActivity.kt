package com.example.converter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.*
import androidx.compose.ui.graphics.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.converter.ui.theme.ConverterTheme

val LightBlue = Color(0xFF00BFFF)
val LightPurple = Color(0xFFB19CD9)
val NeonGreen = Color(0xFF39FF14)


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CurrencyConverterScreen()
        }
    }
}


@Composable
fun CurrencyConverterScreen() {
    var inputAmount by remember { mutableStateOf("") }
    var resultAmount by remember { mutableStateOf("0.00") }
    var selectedConversion by remember { mutableStateOf("EUR to USD") }
    var isEurToUsdSelected by remember { mutableStateOf(true) }

    val eurToUsdRate = 1.11
    val usdToEurRate = 1 / eurToUsdRate

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(LightPurple, LightBlue)
                )
            )
    ) {
        ConverterTheme {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(26.dp))

                Header("Currency Converter")
                Spacer(modifier = Modifier.height(16.dp))


                InputForm(inputAmount) { newAmount -> inputAmount = newAmount }
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ConversionButton(
                        text = "EUR to USD",
                        isSelected = isEurToUsdSelected,
                        onClick = {
                            isEurToUsdSelected = true
                            selectedConversion = "EUR to USD"
                            resultAmount = "0.00"
                        }
                    )
                    ConversionButton(
                        text = "USD to EUR",
                        isSelected = !isEurToUsdSelected,
                        onClick = {
                            isEurToUsdSelected = false
                            selectedConversion = "USD to EUR"
                            resultAmount = "0.00"
                        }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                ConvertButton(
                    onClick = {
                        val amount = inputAmount.toDoubleOrNull() ?: 0.0
                        val conversionRate = if (isEurToUsdSelected) {
                            eurToUsdRate
                        } else {
                            usdToEurRate
                        }
                        resultAmount = String.format("%.2f", amount * conversionRate)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                ResultText(resultAmount,selectedConversion)
            }
        }
    }
}

@Composable
fun InputForm(amount: String, onAmountChange: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = amount,
            onValueChange = onAmountChange,
            label = { Text("Amount") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .border(1.dp, Color.LightGray, shape = RoundedCornerShape(8.dp))
                .shadow(8.dp, RoundedCornerShape(8.dp))
        )
    }
}

@Composable
fun ResultText(result: String, selectedConversion: String) {

        Text(
            text = if (selectedConversion == "EUR to USD") {
                "Result: $$result"
            } else {
                "Result: $result€"
            },
            style = TextStyle(
                fontSize = 54.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center,
        )
    }


@Composable
fun ConvertButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(16.dp))
            .background(
                color = NeonGreen,
                shape = RoundedCornerShape(16.dp)
            ),

        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
    ) {
        Text("Convert", color = Color.Black)
    }
}

@Composable
fun ConversionButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(4.dp)
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color.LightGray else Color.White
        )
    ) {
        Text(
            text = text,
            color = if (isSelected) Color.Black else Color.Gray,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold
            )
        )
    }
}




