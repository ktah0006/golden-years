package com.example.golden_years

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
import com.github.mikephil.charting.utils.ColorTemplate

@Composable
fun ReportScreen() {
    var selectedMode by remember { mutableStateOf("BP Chart") }
    val modes = listOf("BP Chart", "Glucose Chart")
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Weekly Health Report",
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
            "BP Chart" -> BPChart()
            "Glucose Chart" -> GlucoseChart()
        }
    }
}
@Composable
fun BPChart() {
    val systolicBP = listOf(
        BarEntry(0f, 120f),
        BarEntry(1f, 139f),
        BarEntry(2f, 130f),
        BarEntry(3f, 120f),
        BarEntry(4f, 120f),
        BarEntry(5f, 139f),
        BarEntry(5f, 130f)
    )
    val diastolicBP = listOf(
        BarEntry(0f, 80f),
        BarEntry(1f, 70f),
        BarEntry(2f, 90f),
        BarEntry(3f, 80f),
        BarEntry(4f, 80f),
        BarEntry(5f, 70f),
        BarEntry(5f, 90f)
    )

//    val barDataSet = BarDataSet(systolicBP, "Blood Pressure")
    val systolicSet = BarDataSet(systolicBP, "Systolic (mmHg)").apply {
        color = MaterialTheme.colorScheme.primary.toArgb()
    }
    val diastolicSet = BarDataSet(diastolicBP, "Diastolic (mmHg)").apply {
        color = MaterialTheme.colorScheme.secondary.toArgb()
    }

//    barDataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()
    val barData = BarData(systolicSet, diastolicSet)

    // to group the bars
    val groupSpace = 0.2f
    val barSpace = 0.05f
    barData.barWidth = 0.35f

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            BarChart(context).apply {
                data = barData
                description.isEnabled = false
                setFitBars(true)
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.valueFormatter = IndexAxisValueFormatter(
                    listOf(
                        "01/11", "09/11", "11/11",
                        "19/11", "11/12", "01/01", "03/01"
                    )
                )

                axisLeft.axisMinimum = 0f
                axisRight.isEnabled = false
                // to group the bars
                xAxis.axisMinimum = 0f
                xAxis.axisMaximum =
                    0f + barData.getGroupWidth(groupSpace, barSpace) * systolicBP.size

                groupBars(0f, groupSpace, barSpace)

                animateY(1000)
                invalidate()
            }
        }
    )
}

@Composable
fun GlucoseChart() {
    val glucoseEntries = listOf(
        BarEntry(0f, 111f),
        BarEntry(1f, 100f),
        BarEntry(2f, 99f),
        BarEntry(3f, 111f),
        BarEntry(4f, 111f),
        BarEntry(5f, 100f),
        BarEntry(6f, 99f)
    )
    val barDataSet = BarDataSet(glucoseEntries, "Glucose").apply {
        color = MaterialTheme.colorScheme.primary.toArgb()
    }
//    barDataSet.colors = ColorTemplate.PASTEL_COLORS.toList()
    val barData = BarData(barDataSet)

    // to group the bars
    barData.barWidth = 0.5f

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            BarChart(context).apply {
                data = barData
                description.isEnabled = false
                setFitBars(true)
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.valueFormatter = IndexAxisValueFormatter(
                    listOf(
                        "01/11", "09/11", "11/11",
                        "19/11", "11/12", "01/01", "03/01"
                    )
                )

                axisLeft.axisMinimum = 0f
                axisRight.isEnabled = false

                animateY(1000)
                invalidate()
            }
        }
    )
}