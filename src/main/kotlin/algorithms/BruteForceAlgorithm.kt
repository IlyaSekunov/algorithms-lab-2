package algorithms

import ru.ilyasekunov.ru.ilyasekunov.Point
import ru.ilyasekunov.ru.ilyasekunov.Rectangle
import ru.ilyasekunov.ru.ilyasekunov.isInRectangle

fun bruteForceAlgorithm(
    rectangles: List<Rectangle>,
    points: List<Point>
): Array<Long> = Array(points.size) { 0L }.also {
    points.forEachIndexed { i, point ->
        rectangles.forEach { rectangle ->
            if (point.isInRectangle(rectangle)) {
                it[i]++
            }
        }
    }
}
