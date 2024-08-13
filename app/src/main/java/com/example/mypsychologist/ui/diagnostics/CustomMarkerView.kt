package com.example.mypsychologist.ui.diagnostics

import android.content.Context
import android.widget.TextView
import com.example.mypsychologist.R
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF

class CustomMarkerView(
    context: Context,
    layoutResource: Int,
    private val labels: List<String>,
    private val maxValues: ArrayList<Int>
) :
    MarkerView(context, layoutResource) {

    private val tvContent1: TextView = findViewById(R.id.tvContent1)
    private val tvContent2: TextView = findViewById(R.id.tvContent2)
    private val tvLabel: TextView = findViewById(R.id.label_tv)

    override fun refreshContent(e: Entry?, highlight: Highlight?) {

        val index = highlight!!.x.toInt()

        if (e is RadarEntry) {
            // val index = e.x

            val chart = chartView as? CustomRadarChart
            chart?.data?.let { data ->

                val value1 = data.getDataSetByIndex(0).getEntryForIndex(index).y
//                val value2 = data.getDataSetByIndex(1).getEntryForIndex(index).y

//                val maxValues = data.dataSets.map { dataSet ->
//                    dataSet.yMax
//                }
//                val maxValue = maxValues.maxOrNull() ?: 0f

                // Update the TextViews with the retrieved values
                val label = labels.getOrNull(index) ?: ""

                val formattedValue = if(value1.toInt().toFloat() == value1){
                    value1.toInt()
                }else{
                    String.format("%.2f", value1)
                }


                tvContent1.text = "Среднее значение: $formattedValue"
                tvContent2.text = "Максимальное значение: ${maxValues[index]}"
                tvLabel.text = label
            }
        }
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF(-(width / 2).toFloat(), -height.toFloat() - 40)
    }
}