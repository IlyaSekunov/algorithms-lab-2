package algorithms

import ru.ilyasekunov.ru.ilyasekunov.Point
import ru.ilyasekunov.ru.ilyasekunov.Rectangle
import ru.ilyasekunov.ru.ilyasekunov.getUniqueCoordinates
import segmenttree.PersistentSegmentTree
import java.util.*
import kotlin.math.max

fun persistentSegmentTreeAlgorithm(
    rectangles: List<Rectangle>,
    points: List<Point>
): Array<Long> {
    val uniqueYCoordinates = getUniqueCoordinates(rectangles) { y }
    val modifications = getModifications(rectangles)
    val uniqueXCoordinates = modifications.map { it.key }
    val persistentSegmentTree = getPersistentSegmentTree(modifications, uniqueYCoordinates)
    return Array(points.size) { 0L }.also {
        val xRange = uniqueXCoordinates.first()..<uniqueXCoordinates.last()
        val yRange = uniqueYCoordinates.first()..<uniqueYCoordinates.last()
        points.forEachIndexed { i, point ->
            if (point.x in xRange && point.y in yRange) {
                val stateIndex = uniqueXCoordinates.findIndex(point.x)
                val yIndex = uniqueYCoordinates.findIndex(point.y)
                it[i] = persistentSegmentTree.getValue(
                    left = yIndex,
                    right = yIndex,
                    stateIndex = stateIndex
                )
            }
        }
    }
}

fun List<Long>.findIndex(key: Long) = binarySearch(key).let {
    val insertionIndex = if (it < 0) -(it + 1) else it
    if (this[insertionIndex] == key) insertionIndex
    else max(insertionIndex - 1, 0)
}

private fun getModifications(rectangles: List<Rectangle>): SortedMap<Long, out List<Modification>> {
    return TreeMap<Long, MutableList<Modification>>().apply {
        rectangles.forEach { rectangle ->
            val bottomY = rectangle.p1.y
            val topY = rectangle.p2.y
            val leftX = rectangle.p1.x
            val rightX = rectangle.p2.x
            getOrPut(leftX) { mutableListOf() } += Modification(
                yBounds = bottomY to topY,
                modifier = 1
            )
            getOrPut(rightX) { mutableListOf() } += Modification(
                yBounds = bottomY to topY,
                modifier = -1
            )
        }
    }
}

private fun getPersistentSegmentTree(
    modifications: SortedMap<Long, out List<Modification>>,
    uniqueYCoordinates: List<Long>
): PersistentSegmentTree {
    val persistentSegmentTree = PersistentSegmentTree(Array(uniqueYCoordinates.size) { 0L })
    val persistentTreeNodes = mutableListOf<PersistentSegmentTree.Node>()
    modifications.forEach {
        val modificationsForX = it.value
        modificationsForX.forEachIndexed { i, modification ->
            val bottomY = modification.yBounds.first
            val topY = modification.yBounds.second
            val bottomYIndex = uniqueYCoordinates.binarySearch(bottomY)
            val topYIndex = uniqueYCoordinates.binarySearch(topY)
            persistentSegmentTree.add(
                left = bottomYIndex,
                right = topYIndex - 1,
                value = modification.modifier
            )
            if (i == modificationsForX.lastIndex) {
                persistentTreeNodes.add(persistentSegmentTree.lastState())
            }
        }
    }
    return persistentSegmentTree.apply {
        trees = persistentTreeNodes
    }
}

private data class Modification(
    val yBounds: Pair<Long, Long>,
    val modifier: Long
)