package algorithms

import ru.ilyasekunov.ru.ilyasekunov.Point
import ru.ilyasekunov.ru.ilyasekunov.Rectangle
import ru.ilyasekunov.ru.ilyasekunov.getUniqueCoordinates

fun mapAlgorithm(
    rectangles: List<Rectangle>,
    points: List<Point>
): Array<Long> {
    val uniqueXCoordinates = getUniqueCoordinates(rectangles) { x }
    val uniqueYCoordinates = getUniqueCoordinates(rectangles) { y }
    val compressedMatrix = getFilledCompressedMatrix(rectangles, uniqueXCoordinates, uniqueYCoordinates)
    return Array(points.size) { 0L }.also {
        val xRange = uniqueXCoordinates.first()..<uniqueXCoordinates.last()
        val yRange = uniqueYCoordinates.first()..<uniqueYCoordinates.last()
        points.forEachIndexed { i, point ->
            if (point.x in xRange && point.y in yRange) {
                val xIndex = uniqueXCoordinates.findIndex(point.x)
                val yIndex = uniqueYCoordinates.findIndex(point.y)
                it[i] = compressedMatrix[yIndex][xIndex]
            }
        }
    }
}

private fun getFilledCompressedMatrix(
    rectangles: List<Rectangle>,
    uniqueXCoordinates: List<Long>,
    uniqueYCoordinates: List<Long>
) = Array(uniqueYCoordinates.size - 1) {
    Array(uniqueXCoordinates.size - 1) { 0L }
}.apply { fillMatrix(rectangles, uniqueXCoordinates, uniqueYCoordinates) }


private fun Array<Array<Long>>.fillMatrix(
    rectangles: List<Rectangle>,
    uniqueXCoordinates: List<Long>,
    uniqueYCoordinates: List<Long>
) {
    rectangles.forEach { rectangle ->
        val rightX = rectangle.p2.x
        val topY = rectangle.p2.y
        val leftXIndex = uniqueXCoordinates.binarySearch(rectangle.p1.x)
        val bottomYIndex = uniqueYCoordinates.binarySearch(rectangle.p1.y)
        for (i in leftXIndex..<size) {
            if (topY < uniqueYCoordinates[i + 1]) break
            for (j in bottomYIndex..<this[i].size) {
                if (rightX < uniqueXCoordinates[j + 1]) break
                this[i][j]++
            }
        }
    }
}