package segmenttree

import ru.ilyasekunov.ru.ilyasekunov.intersect
import ru.ilyasekunov.ru.ilyasekunov.isIntersection

class PersistentSegmentTree(array: Array<Long>) {
    var trees = mutableListOf(buildTree(array))

    private fun Node.copyLeft() {
        left = left?.copy()
    }

    private fun Node.copyRight() {
        right = right?.copy()
    }

    private fun stateAt(index: Int) = trees[index]

    private fun Node.pushModifier() {
        if (modifier != 0L) {
            if (left != null) {
                copyLeft()
                left!!.modifier += modifier
            }
            if (right != null) {
                copyRight()
                right!!.modifier += modifier
            }
        }
    }

    private fun Node.countValue(upcomingModifier: Long) =
        value * (modifier + upcomingModifier) * (bounds.second - bounds.first + 1)

    fun lastState() = trees.last()

    fun getValue(left: Int, right: Int, stateIndex: Int): Long {
        fun getValue(
            currentNode: Node,
            boundsToBeModified: Pair<Long, Long>,
            upcomingModifier: Long
        ): Long = with(currentNode) {
            when {
                !isIntersection(bounds, boundsToBeModified) -> 0
                intersect(bounds, boundsToBeModified) == bounds -> countValue(upcomingModifier)
                else -> getValue(
                    currentNode = this.left!!,
                    boundsToBeModified = boundsToBeModified,
                    upcomingModifier = modifier + upcomingModifier
                ) + getValue(
                    currentNode = this.right!!,
                    boundsToBeModified = boundsToBeModified,
                    upcomingModifier = modifier + upcomingModifier
                )
            }
        }

        return getValue(
            currentNode = stateAt(stateIndex),
            boundsToBeModified = left.toLong() to right.toLong(),
            upcomingModifier = 0
        )
        /*fun getValue(root: Node): Long {
            val boundsToBeModified = left.toLong() to right.toLong()
            if (!isIntersection(root.bounds, boundsToBeModified)) {
                return 0
            }

            countModifier(root)
            return with(root) {
                when {
                    isLeaf() -> root.value
                    intersect(bounds, boundsToBeModified) == bounds -> root.value
                    else -> getValue(this.left!!) + getValue(this.right!!)
                }
            }
        }
        return getValue(stateAt(stateIndex))*/
    }

    fun add(left: Int, right: Int, value: Long) {
        fun add(root: Node, boundsToBeModified: Pair<Long, Long>): Node {
            if (!isIntersection(root.bounds, boundsToBeModified)) {
                return root
            }

            val newNode = root.copy()
            return newNode.apply {
                if (modifier != 0L) {
                    copyLeft()
                    copyRight()
                    countModifier()
                }

                val intersection = intersect(bounds, boundsToBeModified)
                if (intersection == bounds) {
                    modifier += value
                    return@apply
                }

                this.value += value * (intersection.second - intersection.first + 1)
                this.left = this.left?.let { add(it, boundsToBeModified) }
                this.right = this.right?.let { add(it, boundsToBeModified) }
            }
        }

        val lastTree = trees.last()
        val addResult = add(lastTree, left.toLong() to right.toLong())
        if (addResult !== lastTree) {
            trees += addResult
        }
    }

    private fun Node.countModifier() {
        value += modifier * (bounds.second - bounds.first + 1)
        pushModifier()
        modifier = 0
    }

    private fun buildTree(array: Array<Long>): Node {
        fun buildTree(left: Int, right: Int): Node {
            return if (left == right) {
                Node(
                    value = array[left],
                    bounds = left.toLong() to right.toLong()
                )
            } else {
                val middle = (left + right) / 2
                val leftNode = buildTree(left, middle)
                val rightNode = buildTree(middle + 1, right)
                Node(
                    value = leftNode.value + rightNode.value,
                    bounds = left.toLong() to right.toLong(),
                    left = leftNode,
                    right = rightNode
                )
            }
        }
        return buildTree(0, array.lastIndex)
    }

    data class Node(
        var value: Long,
        var modifier: Long = 0,
        var bounds: Pair<Long, Long>,
        var left: Node? = null,
        var right: Node? = null
    )
}