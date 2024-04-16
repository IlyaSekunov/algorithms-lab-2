package ru.ilyasekunov.ru.ilyasekunov

data class Rectangle(
    val p1: Point,
    val p2: Point
)

fun generateRectangles(n: Int): List<Rectangle> =
    ArrayList<Rectangle>().apply {
        repeat(n) {
            add(
                Rectangle(
                    p1 = Point(
                        x = 10 * it.toLong(),
                        y = 10 * it.toLong()
                    ),
                    p2 = Point(
                        x = 10 * (2 * n - it.toLong()),
                        y = 10 * (2 * n - it.toLong())
                    )
                )
            )
        }
    }