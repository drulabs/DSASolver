fun main() {
    // Problem: Reverse nodes in k-groups.
    // Example: Head = [1,2,3,4,5], k = 2
    // Output: [2,1,4,3,5]
    
    val inputs = listOf(
        listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13) to 4,
        listOf(1, 2, 3, 4, 5) to 3,
        listOf(1, 2, 3, 4, 5, 6) to 2
    )
    val expected = listOf(
        listOf(4, 3, 2, 1, 8, 7, 6, 5, 12, 11, 10, 9, 13),
        listOf(3, 2, 1, 4, 5),
        listOf(2, 1, 4, 3, 6, 5)
    )

    println("--------------------------------------")
    for (i in inputs.indices) {
        val (list, k) = inputs[i]
        println("Input => $list, k => $k")
        val result = solve(list, k)
        println("Result = $result, Expected = ${expected[i]}")
        println("\n--------------------------------------")
    }
}

private fun solve(nums: List<Int>, k: Int): List<Int> {
    val head: Node? = Node.makeLinkedList(nums)
    var current: Node? = head
    var result: Node? = null
    var previous: Node? = null

    while (current != null) {
        var temp = current
        var count = 0
        while (count < k && temp != null) {
            temp = temp.next
            count++
        }
        if (count < k) {
            break
        }
        val start = current
        val reversedHead = revert(start, temp)
        if (result == null) {
            result = reversedHead
        }
        previous?.next = reversedHead
        start.next = temp
        previous = start
        current = temp
    }
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