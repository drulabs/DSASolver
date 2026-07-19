fun main() {
    val inputs = listOf(
        listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
        listOf(3, 4),
        listOf(1),
        listOf(4, 3, 2, 1),
    ).map {
        Node.construct(it)
    }
    
    for (input in inputs) {
        input?.printIt()
        val result = solve(input)
        result?.printIt()
        println("\n")
    }
}

fun solve(head: Node?): Node? {
    var prev: Node? = null
    var next: Node? = null
    var current: Node? = head

    while (current != null) {
        next = current.next
        current.next = prev
        prev = current
        current = next
    }
    return prev
}

class Node(val data: Int) {
    var next: Node? = null

    fun printIt() {
        var current: Node? = this
        println("-------------------------")
        while(current != null) {
            print("${current.data} -> ")
            current = current.next
        }
        print("null\n")
    }

    companion object {
        fun construct(values: List<Int> = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)): Node? {
            var head: Node? = null
            var tempNode: Node? = null
            for (num in values) {
                val node = Node(num)
                if (head == null) {
                    head = node
                    tempNode = node
                } else {
                    tempNode?.next = node
                    tempNode = tempNode?.next
                }
            }

            return head
        }
    }
}