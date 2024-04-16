package ru.ilyasekunov.ru.ilyasekunov

import javafx.scene.Scene
import javafx.scene.chart.LineChart
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import javafx.stage.Stage

fun LineChart<Number, Number>.draw(stage: Stage?) {
    stage?.also {
        it.scene = Scene(this, 800.0, 600.0)
    }?.show()
}

fun createLineChart(
    chartTitle: String,
    xAxisName: String,
    yAxisName: String
): LineChart<Number, Number> {
    val yAxis = NumberAxis().apply { label = yAxisName }
    val xAxis = NumberAxis().apply { label = xAxisName }
    return LineChart(xAxis, yAxis).apply { title = chartTitle }
}

fun LineChart<Number, Number>.enrichLineChart(
    chartName: String,
    data: List<Pair<Number, Number>>
) {
    val series = XYChart.Series<Number, Number>().apply { name = chartName }
    data.forEach {
        series.data.add(XYChart.Data(it.first, it.second))
    }
    getData().add(series)
}