package com.example.golden_years

import com.example.golden_years.record_room.HealthRecord
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ReportScreen(
    recordViewModel: RecordViewModel,
    userId: String
) {

    val recentRecords by recordViewModel
        .getRecentRecords(userId)
        .collectAsState(initial = emptyList())

    val reversed = recentRecords.sortedBy { it.createdAt }

    var selectedMode by remember { mutableStateOf("BP Chart") }
    val modes = listOf("BP Chart", "Glucose Chart")

    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(
            modifier = Modifier
                .height(18.dp))
        Text("Health Report",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            modes.forEach { mode ->
                FilterChip(
                    selected = selectedMode == mode,
                    onClick = { selectedMode = mode },
                    label = {
                        Text(
                            text = mode,
                            color = if (selectedMode == mode) Color.White
                            else Color.Black
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        containerColor = MaterialTheme.colorScheme.tertiary
                    )
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        when (selectedMode) {
            "BP Chart" -> BPChart(reversed)
            "Glucose Chart" -> GlucoseChart(reversed)
        }

    }
}

// taken and adapted from Lab Week 6
@Composable
fun BPChart(recordsForChart: List<HealthRecord>) {

    if (recordsForChart.isEmpty()) {
        Text("No data to present")
        return
    }

    val systolicBP = recordsForChart.mapIndexed { index, singleRecord ->
        BarEntry(index.toFloat(), singleRecord.bpSystolic.toFloat())
    }

    val diastolicBP = recordsForChart.mapIndexed { index, singleRecord ->
        BarEntry(index.toFloat(), singleRecord.bpDiastolic.toFloat())
    }

    val systolicSet = BarDataSet(systolicBP, "Systolic (mmHg)").apply {
        color = MaterialTheme.colorScheme.primary.toArgb()
    }
    val diastolicSet = BarDataSet(diastolicBP, "Diastolic (mmHg)").apply {
        color = MaterialTheme.colorScheme.secondary.toArgb()
    }

    val barData = BarData(systolicSet, diastolicSet)

    // to group the bars
    val groupSpace = 0.2f
    val barSpace = 0.05f
    barData.barWidth = 0.35f

    val formatter = SimpleDateFormat("dd/MM", Locale.getDefault())
    val xAxisLabels = recordsForChart.map {
        formatter.format(Date(it.createdAt))
    }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            BarChart(context).apply {
                data = barData
                description.isEnabled = false
                setFitBars(true)
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabels)
                xAxis.granularity = 1f

                axisLeft.axisMinimum = 0f
                axisRight.isEnabled = false
                // to group the bars
                xAxis.axisMinimum = 0f
                xAxis.axisMaximum = barData.getGroupWidth(groupSpace, barSpace) * recordsForChart.size

                groupBars(0f, groupSpace, barSpace)

                animateY(1000)
                invalidate()
            }
        }
    )
}

// taken and adapted from Lab Week 6
@Composable
fun GlucoseChart(recordsForChart: List<HealthRecord>) {

    if (recordsForChart.isEmpty()) {
        Text("No data to present")
        return
    }

    val glucoseEntries = recordsForChart.mapIndexed { index, singleRecord ->
        BarEntry(index.toFloat(), singleRecord.glucose.toFloat())
    }

    val barDataSet = BarDataSet(glucoseEntries, "Glucose").apply {
        color = MaterialTheme.colorScheme.primary.toArgb()
    }


    val barData = BarData(barDataSet)

    // to group the bars
    barData.barWidth = 0.5f

    val formatter = SimpleDateFormat("dd/MM", Locale.getDefault())
    val xAxisLabels = recordsForChart.map {
        formatter.format(Date(it.createdAt))
    }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            BarChart(context).apply {
                data = barData
                description.isEnabled = false
                setFitBars(true)
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabels)

                axisLeft.axisMinimum = 0f
                axisRight.isEnabled = false

                animateY(1000)
                invalidate()
            }
        }
    )
}