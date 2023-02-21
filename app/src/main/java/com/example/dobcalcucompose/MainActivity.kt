package com.example.dobcalcucompose

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                CalculateAgeScreen()
            }
        }
    }
}

@Composable
fun CalculateAgeScreen() {
    val context = LocalContext.current
    var selectedDate by remember { mutableStateOf("") }
    var ageInMinutes by remember { mutableStateOf("") }
    var datePicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Calculate your",
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "AGE",
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.h4,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "IN MINUTES",
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.h4,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                datePicker = true
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Select Date")
        }
        Text(
            text = selectedDate,
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = "Selected date",
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(top = 8.dp)
        )
        TextField(
            value = ageInMinutes,
            onValueChange = { ageInMinutes = it },
            label = {
                Text(
                    text = "In minutes till date",
                    color = MaterialTheme.colors.onSurface,
                    style = MaterialTheme.typography.caption
                )
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
    }

    if (datePicker) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                var selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"
                ageInMinutes = calculateAgeInMinutes(selectedDate)
                datePicker = false
                selectedDate = selectedDate
            },
            year,
            month,
            day
        ).show()
    }
}


fun calculateAgeInMinutes(selectedDate: String): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
    val theDate = sdf.parse(selectedDate)
    theDate?.let {
        val selectedDateInMinutes = it.time/60000
        val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
        currentDate?.let {
            val currentDateToMinutes = currentDate.time/60000
            val differenceInMinutes = currentDateToMinutes - selectedDateInMinutes
            return differenceInMinutes.toString()
        }
    }
    return ""
}
