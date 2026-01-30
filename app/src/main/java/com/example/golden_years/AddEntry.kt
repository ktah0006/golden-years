package com.example.golden_years

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.focus.focusProperties
import com.example.golden_years.ui.theme.VerySoftCoral

@Composable
fun AddEntry() {
    var systolicBloodPressure by remember { mutableStateOf("") }
    var diastolicBloodPressure by remember { mutableStateOf("") }
    var glucose by remember { mutableStateOf("") }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize()
                        .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "New Record",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier.fillMaxWidth(0.7f),
                horizontalAlignment = Alignment.Start
            ) {

                // date picker
                DisplayDatePicker()

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Blood Pressure",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                OutlinedTextField(
                    value = systolicBloodPressure,
                    onValueChange = { systolicBloodPressure = it },
                    label = { Text("Systolic BP") }
                )

                OutlinedTextField(
                    value = diastolicBloodPressure,
                    onValueChange = { diastolicBloodPressure = it },
                    label = { Text("Diastolic BP") }
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    "Glucose",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                OutlinedTextField(
                    value = glucose,
                    onValueChange = { glucose = it },
                    label = { Text("Glucose") }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            DropDown()
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(0.7f),
                horizontalArrangement = Arrangement.Center
            )
            {
                Button(
                    onClick = {},
                ) {
                    Text("+ Add")
                }

                Spacer(modifier = Modifier.width(16.dp))

                OutlinedButton(
                    onClick = {},
                ) {
                    Text("cancel")
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayDatePicker(
    modifier: Modifier = Modifier,
    label: String = "Date"
) {
    val calendar = Calendar.getInstance()
    var recordDate by remember { mutableStateOf("") }
    val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis()
    )
    var showDatePicker by remember {
        mutableStateOf(false)
    }

    var selectedDate by remember {
        mutableStateOf(calendar.timeInMillis)
    }

    Column() {
        OutlinedTextField(
            value = recordDate,
            onValueChange = {recordDate=it},
            readOnly = true,
            label = { Text(label) },
            modifier = modifier,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.CalendarToday,
                    contentDescription = "Select Date",
                    modifier = Modifier
                        .clickable { showDatePicker = true }
                        .size(30.dp)
                )
            }
        )

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = {
                    showDatePicker = false
                },
                confirmButton = {
                    TextButton(onClick = {
                        showDatePicker = false
                        selectedDate = datePickerState.selectedDateMillis!!
                        recordDate = "${formatter.format(Date(selectedDate))}"
                    }) {
                        Text(text = "OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showDatePicker = false
                    }) {
                        Text(text = "Cancel")
                    }
                }
            )
            {
                DatePicker(
                    state = datePickerState,
                    colors = DatePickerDefaults.colors(
                        selectedDayContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedDayContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDown(){
    val states = listOf("Before Meal", "After Meal")
    var isExpanded by remember { mutableStateOf(false) }
    var selectedMeal = remember { mutableStateOf(states[0])}
    Column(
        modifier = Modifier
            .fillMaxWidth(0.7f)
    )
    {
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = it },
        ) {
            TextField(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .focusProperties {
                        canFocus = false
                    }
                    .padding(bottom = 8.dp),
                readOnly = true,
                value = selectedMeal.value,
                onValueChange = {},
                label = { Text("Meal") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                )
            )
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false },
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.tertiary, // SoftCoral
                    )
            )
            {
                states.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            selectedMeal.value = selectionOption
                            isExpanded = false},
                        contentPadding =
                            ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
    }
}