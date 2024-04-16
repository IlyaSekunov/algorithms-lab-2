package ru.ilyasekunov.ru.ilyasekunov

import kotlin.math.pow

data class Point(val x: Long, val y: Long)

fun generatePoints(n: Int): List<Point> =
    ArrayList<Point>().apply {
        val pX = 494737L
        val pY = 570487L
        repeat(n) { i ->
            add(
                Point(
                    x = hashPoint(i.toLong(), pX, n.toLong()),
                    y = hashPoint(i.toLong(), pY, n.toLong())
                )
            )
        }
    }

private fun hashPoint(i: Long, p: Long, n: Long) = ((i * p).toDouble().pow(31) % (20 * n)).toLong()

fun Point.isInRectangle(rectangle: Rectangle): Boolean {
    val x1 = rectangle.p1.x
    val y1 = rectangle.p1.y
    val x2 = rectangle.p2.x
    val y2 = rectangle.p2.y
    return (x in x1..<x2) && (y in y1..<y2)
}