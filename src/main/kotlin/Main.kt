package ru.ilyasekunov.ru.ilyasekunov

import algorithms.bruteForceAlgorithm
import algorithms.mapAlgorithm
import algorithms.persistentSegmentTreeAlgorithm
import javafx.application.Application
import javafx.stage.Stage
import test.testAlgorithm

class MainTest : Application() {
    override fun start(stage: Stage?) {
        val bruteForceAlgorithmResults = testAlgorithm(::bruteForceAlgorithm)
        val mapAlgorithmResults = testAlgorithm(::mapAlgorithm)
        val persistentSegmentTreeAlgorithmResults = testAlgorithm(::persistentSegmentTreeAlgorithm)
        createLineChart(
            chartTitle = "Algorithms",
            xAxisName = "Points count",
            yAxisName = "Time (nanoseconds)",
        ).apply {
            enrichLineChart("Brute force", bruteForceAlgorithmResults)
            enrichLineChart("Compressed map", mapAlgorithmResults)
            enrichLineChart("Persistent segment tree", persistentSegmentTreeAlgorithmResults)
        }.draw(stage)
    }
}

fun main() {
    Application.launch(MainTest::class.java)
}