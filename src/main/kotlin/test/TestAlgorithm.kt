package test

import ru.ilyasekunov.ru.ilyasekunov.Point
import ru.ilyasekunov.ru.ilyasekunov.Rectangle
import ru.ilyasekunov.ru.ilyasekunov.generatePoints
import ru.ilyasekunov.ru.ilyasekunov.generateRectangles
import kotlin.system.measureTimeMillis

const val TESTS_COUNT = 1000
const val RECTANGLES_COUNT = 256
const val START_POINTS_COUNT = 4
const val END_POINTS_COUNT = RECTANGLES_COUNT * RECTANGLES_COUNT
val STEP = { previous: Int -> previous * 2 }

fun testAlgorithm(
    algorithm: (rectangles: List<Rectangle>, points: List<Point>) -> Array<Long>
): List<Pair<Number, Number>> = ArrayList<Pair<Number, Number>>(TESTS_COUNT).apply {
    val rectangles = generateRectangles(RECTANGLES_COUNT)
    var pointsCount = START_POINTS_COUNT
    while (pointsCount <= END_POINTS_COUNT) {
        val points = generatePoints(pointsCount)
        testAlgorithm(TESTS_COUNT) { algorithm(rectangles, points) }
        val averageResult = testAlgorithm(TESTS_COUNT) { algorithm(rectangles, points) }.average()
        add(pointsCount to averageResult)
        pointsCount = STEP(pointsCount)
    }
}

fun testAlgorithm(
    testsCount: Int,
    algorithm: () -> Unit
): List<Long> = ArrayList<Long>(testsCount).apply {
    repeat(testsCount) {
        val result = measureTimeMillis(algorithm)
        add(result)
    }
}