package com.example.golden_years

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RecordScreen(
    recordViewModel: RecordViewModel,
    userId: String,
    navController: NavController
) {
    val records by recordViewModel
        .getRecords(userId)
        .collectAsState(initial = emptyList())

    Column( modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(18.dp))

        Text("All Records",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(6.dp))

        // taken and adapted from Lab Week 3
        LazyColumn (modifier = Modifier.weight(1f)
        ) {
            items(
                records.size,
                key = { records[it].recordId }) { index ->
                val record = records[index]
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    val formatter = SimpleDateFormat("dd-MMMM-yyyy", Locale.getDefault())
                    val displayDate = record.createdAt.let { formatter.format(Date(it)) }
                    Text(
                        text =
                            "Date: $displayDate\n" +
                                    "BP: ${record.bpSystolic}/${record.bpDiastolic}\n" +
                                    "Glucose: ${record.glucose}\n" +
                                    record.mealTiming,
                        modifier = Modifier.padding(8.dp)
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)){
                        IconButton(onClick = {
                            recordViewModel.setRecordToEdit(record)
                            navController.navigate(OtherDestinations.EDITRECORD.route)
                        },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                modifier = Modifier.size(24.dp),
                                contentDescription = "Edit record",
                            )
                        }
                        IconButton(
                            onClick = {
                            recordViewModel.deleteRecord(record)
                                },
                        ) {
                            Icon(Icons.Default.Delete,
                                modifier = Modifier.size(24.dp),
                                contentDescription = "Delete",
                            )
                        }
                    }
                }

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}