package com.example.golden_years

import HealthRecord
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import java.util.Date
import java.util.Locale
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.focus.focusProperties
import androidx.navigation.NavController
import android.util.Log

@Composable
fun AddEntry(
    recordViewModel: RecordViewModel,
    userId: String,
    navController: NavController
) {
    var systolicBloodPressure by remember { mutableStateOf("") }
    var diastolicBloodPressure by remember { mutableStateOf("") }
    var glucose by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf<Long>(System.currentTimeMillis()) }
    var sysBPError by remember { mutableStateOf<String?>(null) }
    var diasBPError by remember { mutableStateOf<String?>(null) }
    var glucoseError by remember { mutableStateOf<String?>(null) }
    var selectedMeal by remember { mutableStateOf("Before Meal") }

    // takes in the data and decides if an error should be generated
    fun validate(field: String): String? {
        if (field.isBlank()){
            return "Required"
        } else if (field.toIntOrNull() == null){
            return "This must be a number"
        } else
            return null
    }

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
            Spacer(modifier = Modifier.height(18.dp))

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
//                DisplayDatePicker()
                DisplayDatePicker(
                    selectedDate = selectedDate,
                    onDateSelected = { selectedDate = it }
                )

                Spacer(modifier = Modifier.height(18.dp))

//                Text(
//                    "Blood Pressure",
//                    style = MaterialTheme.typography.headlineSmall,
//                    fontWeight = FontWeight.Bold,
//                    color = MaterialTheme.colorScheme.primary
//                )
                OutlinedTextField(
                    value = systolicBloodPressure,
                    onValueChange = {
                        systolicBloodPressure = it
                        sysBPError = null
                    },
                    label = { Text("Systolic Blood Pressure") },
                    supportingText = { sysBPError?.let { Text(it) } },
                    isError = (sysBPError != null)
                )
                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = diastolicBloodPressure,
                    onValueChange = {
                        diastolicBloodPressure = it
                        diasBPError = null
                    },
                    label = { Text("Diastolic Blood Pressure") },
                    supportingText = { diasBPError?.let { Text(it) } },
                    isError = (diasBPError != null)
                )

                Spacer(modifier = Modifier.height(8.dp))

//                Text(
//                    "Glucose",
//                    style = MaterialTheme.typography.headlineSmall,
//                    fontWeight = FontWeight.Bold,
//                    color = MaterialTheme.colorScheme.primary
//                )
                OutlinedTextField(
                    value = glucose,
                    onValueChange = {
                        glucose = it
                        glucoseError = null
                    },
                    label = { Text("Glucose") },
                    supportingText = { glucoseError?.let { Text(it) } },
                    isError = (glucoseError != null)

                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            DropDown(
                selectedMeal = selectedMeal,
                onMealSelected = { selectedMeal = it }
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(0.7f),
                horizontalArrangement = Arrangement.Center
            )
            {
                Button(
                    onClick = {

                        sysBPError = validate(systolicBloodPressure)
                        diasBPError = validate(diastolicBloodPressure)
                        glucoseError = validate(glucose)

                        if (sysBPError==null && diasBPError==null && glucoseError==null){
                            handleAddEntry(
                                recordViewModel,
                                navController,
                                userId,
                                systolicBloodPressure,
                                diastolicBloodPressure,
                                glucose,
                                selectedMeal,
                                selectedDate
                            )
                        }

                    },
                ) {
                    Text("+ Add")
                }

                Spacer(modifier = Modifier.width(16.dp))

                OutlinedButton(
                    onClick = {
                        systolicBloodPressure = ""
                        diastolicBloodPressure = ""
                        glucose = ""
                        navController.popBackStack()
                    },
                ) {
                    Text("Cancel")
                }
            }

        }
    }
}

fun handleAddEntry(
    recordViewModel: RecordViewModel,
    navController: NavController,
    userId: String,
    systolicBloodPressure: String,
    diastolicBloodPressure: String,
    glucose: String,
    selectedMeal: String,
    selectedDate: Long
){
    val convertedSystolic = systolicBloodPressure.toIntOrNull()
    val convertedDiastolic = diastolicBloodPressure.toIntOrNull()
    val convertedGlucose = glucose.toIntOrNull()


    if (convertedSystolic == null
        || convertedDiastolic == null
        || convertedGlucose == null) {
        return
    }

    val healthRecord = HealthRecord(
        userId = userId,
        bpSystolic = convertedSystolic,
        bpDiastolic = convertedDiastolic,
        glucose = convertedGlucose,
        mealTiming = selectedMeal,
        createdAt = selectedDate

    )
    Log.d("ROOM_TEST", "Inserting record: $healthRecord")
    recordViewModel.insertRecord(healthRecord)
    navController.navigate(Destinations.RECORD.route){
        popUpTo(OtherDestinations.ADDENTRY.route) { inclusive = true }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayDatePicker(
    selectedDate: Long?,
    onDateSelected: (Long) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Date"
) {
    val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val datePickerState = rememberDatePickerState(
//        initialSelectedDateMillis = selectedDate ?: System.currentTimeMillis()
        initialSelectedDateMillis = selectedDate
    )
    var showDatePicker by remember { mutableStateOf(false) }
    val displayDate = selectedDate?.let { formatter.format(Date(it)) } ?: ""


    Column() {
        OutlinedTextField(
            value = displayDate,
//            onValueChange = {recordDate=it},
            onValueChange = {},
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
                        datePickerState.selectedDateMillis?.let {
                            onDateSelected(it)
                        }
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
fun DropDown(
    selectedMeal: String,
    onMealSelected: (String) -> Unit
){
    val states = listOf("Before Meal", "After Meal")
    var isExpanded by remember { mutableStateOf(false) }
//    var selectedMeal = remember { mutableStateOf(states[0])}
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
                value = selectedMeal,
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
                        color = MaterialTheme.colorScheme.tertiary,
                    )
            )
            {
                states.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            onMealSelected(selectionOption)
                            isExpanded = false},
                        contentPadding =
                            ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
    }
}