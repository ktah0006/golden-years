package com.example.golden_years

import com.example.golden_years.record_room.HealthRecord
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.material3.Scaffold
import androidx.navigation.NavController
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState

@Composable
fun EditRecord(
    recordViewModel: RecordViewModel,
    userId: String,
    navController: NavController
) {

    val oldRecord by recordViewModel.currEditingRecord.collectAsState()

    var systolicBloodPressure by remember { mutableStateOf("") }
    var diastolicBloodPressure by remember { mutableStateOf("") }
    var glucose by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(0L) }
    var selectedMeal by remember { mutableStateOf("") }

    LaunchedEffect(oldRecord) {
        oldRecord?.let {
            systolicBloodPressure = it.bpSystolic.toString()
            diastolicBloodPressure = it.bpDiastolic.toString()
            glucose = it.glucose.toString()
            selectedDate = it.createdAt
            selectedMeal = it.mealTiming
        }
    }

    var sysBPError by remember { mutableStateOf<String?>(null) }
    var diasBPError by remember { mutableStateOf<String?>(null) }
    var glucoseError by remember { mutableStateOf<String?>(null) }

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
                "Edit Record",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier.fillMaxWidth(0.7f),
                horizontalAlignment = Alignment.Start
            ) {

                DisplayDatePicker(
                    selectedDate = selectedDate,
                    onDateSelected = { selectedDate = it }
                )

                Spacer(modifier = Modifier.height(18.dp))

                OutlinedTextField(
                    value = systolicBloodPressure,
                    onValueChange = {
                        systolicBloodPressure = it
                        sysBPError = null
                    },
                    label = { Text("Systolic Blood Pressure *") },
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
                    label = { Text("Diastolic Blood Pressure *") },
                    supportingText = { diasBPError?.let { Text(it) } },
                    isError = (diasBPError != null)
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = glucose,
                    onValueChange = {
                        glucose = it
                        glucoseError = null
                    },
                    label = { Text("Glucose *") },
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
                            handleUpdateRecord(
                                oldRecord!!,
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
                    Text("Save Changes")
                }

                Spacer(modifier = Modifier.width(16.dp))

                OutlinedButton(
                    onClick = {
                        systolicBloodPressure = ""
                        diastolicBloodPressure = ""
                        glucose = ""
                        recordViewModel.clearEditingRecord()
                        navController.popBackStack()
                    },
                ) {
                    Text("Cancel")
                }
            }

        }
    }
}


// updates record in firestore and room
fun handleUpdateRecord(
    oldRecord: HealthRecord,
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

    val updatedRecord = oldRecord.copy(
        bpSystolic = convertedSystolic,
        bpDiastolic = convertedDiastolic,
        glucose = convertedGlucose,
        mealTiming = selectedMeal,
        createdAt = selectedDate
    )

    Log.d("EDIT_ROOM_TEST", "updating record: $updatedRecord")

    recordViewModel.updateRecord(userId, updatedRecord)
    recordViewModel.clearEditingRecord()
    navController.navigate(Destinations.RECORD.route){
        popUpTo(OtherDestinations.EDITRECORD.route) { inclusive = true }
    }

}