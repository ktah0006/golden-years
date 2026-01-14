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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun RecordScreen() {
    val allRecords = remember { mutableStateListOf(
        "Date: 01/11/2025\nBP: 120/80\nGlucose: 111\nbefore meal",
        "Date: 09/11/2025\nBP: 139/70\nGlucose: 100\n<2 hours after meal",
        "Date: 11/11/2025\nBP: 130/90\nGlucose: 99\n<2 hours after meal",
        "Date: 19/11/2025\nBP: 120/80\nGlucose: 111\nbefore meal",
        "Date: 11/12/2025\nBP: 120/80\nGlucose: 111\n>2 hours after meal",
        "Date: 01/01/2026\nBP: 139/70\nGlucose: 100\n<2 hours after meal",
        "Date: 03/01/2026\nBP: 130/90\nGlucose: 99\nbefore meal")}

    Column( modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {

        Spacer(
            modifier = Modifier
                .height(32.dp))
        Text(
            text ="All Records:",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        LazyColumn (
            modifier = Modifier
                .weight(1f)
        ) {
            items(allRecords.size) { index ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = allRecords[index],
                        modifier = Modifier
                            .padding(8.dp)
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)){
                        IconButton(onClick = {},
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                modifier = Modifier.size(24.dp),
                                contentDescription = "Edit record",
                            )
                        }
                        IconButton(onClick = {
                            allRecords.removeAt(index)
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
                    color = Color.LightGray
                )
            }
        }
    }
}