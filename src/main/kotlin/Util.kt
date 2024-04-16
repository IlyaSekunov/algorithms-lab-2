package ru.ilyasekunov.ru.ilyasekunov

import java.util.*
import kotlin.math.max
import kotlin.math.min

fun isIntersection(a: Pair<Long, Long>, b: Pair<Long, Long>) = a.first <= b.second && a.second >= b.first

fun intersect(a: Pair<Long, Long>, b: Pair<Long, Long>): Pair<Long, Long> {
    if (!isIntersection(a, b)) {
        return -1L to -1L
    }
    return max(a.first, b.first) to min(a.second, b.second)
}

fun getUniqueCoordinates(
    rectangles: List<Rectangle>,
    value: Point.() -> Long
) = TreeSet<Long>().apply {
    rectangles.forEach {
        add(it.p1.value())
        add(it.p2.value())
    }
}.toList()