fun main() {
    // Problem: Reverse Linked List II
    // Reverse the nodes from position left to right (1-indexed).
    //
    // Example:
    // Head = [1,2,3,4,5], left = 2, right = 4
    // Output = [1,4,3,2,5]

    val inputs = listOf(
        Triple(listOf(1, 2, 3, 4, 5), 2, 4),
        Triple(listOf(1, 2, 3, 4, 5), 1, 5),
        Triple(listOf(1, 2, 3, 4, 5), 3, 3),
        Triple(listOf(1, 2), 1, 2),
        Triple(listOf(1), 1, 1)
    )

    val expected = listOf(
        listOf(1, 4, 3, 2, 5),
        listOf(5, 4, 3, 2, 1),
        listOf(1, 2, 3, 4, 5),
        listOf(2, 1),
        listOf(1)
    )

    println("--------------------------------------")
    for ((i, input) in inputs.withIndex()) {
        val (list, left, right) = input
        println("Input => $list, left => $left, right => $right")

        val result = solve(list, left, right)
        val passed = result == expected[i]

        println("${if (passed) "✅ PASS" else "❌ FAIL"}")
        println("Result   = $result")
        println("Expected = ${expected[i]}")
        println("\n--------------------------------------")
    }
}

private fun solve(nums: List<Int>, left: Int, right: Int): List<Int> {
    if (left == right) return nums

    val head: Node? = Node.makeLinkedList(nums)
    var current: Node? = head
    var result: Node? = null
    var previous: Node? = null
    var start: Node? = null
    var end: Node? = null
    var next: Node? = null

    // assumuing 1 <= left < right <= size of the list
    for (i in 1..right) {
        if (i == left) {
            start = current
        }
        if (i == right) {
            end = current
            next = current?.next
            break;
        }
        previous = if (start == null) current else previous
        current = current?.next
    }

    val reversedHead = revert(start, next)

    previous?.next = end
    start?.next = next
    
    result = if (left == 1) end else head

    return result?.toList() ?: emptyList()
}

private fun revert(node: Node?, end: Node? = null): Node? {
    var current = node
    var prev: Node? = null

    while (current != null && current != end) {
        val next = current.next
        current.next = prev
        prev = current
        current = next
    }

    return prev
}

// Definition for singly-linked list node:
class Node(val num: Int) {
    var next: Node? = null

    override fun toString(): String {
        var current: Node? = this
        var result = ""
        while (current != null) {
            result += "${current.num} -> "
            current = current.next
        }
        return result
    }

    fun printIt(str: String = "") {
        println("$str------------------------------")
        print(this)
        println("\n$str------------------------------")
    }

    fun toList(): List<Int> {
        val list = mutableListOf<Int>()
        var current: Node? = this
        while (current != null) {
            list.add(current.num)
            current = current.next
        }
        return list
    }

    companion object {
        fun makeLinkedList(nums: List<Int>): Node? {
            var head: Node? = null
            var current: Node? = null

            nums.forEach {
                val node = Node(it)
                if (head == null) {
                    head = node
                    current = head
                } else {
                    current?.next = node
                    current = current?.next
                }
            }

            return head
        }
    }
}