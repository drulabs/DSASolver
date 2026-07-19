fun main() {
    // val list = makeLinkedList(
    //     listOf(
    //         1, 1, 1, 1, 1, 1, 1, 1,
    //         2,
    //         3, 3, 3,
    //         5,
    //         7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
    //         9
    //     )
    // )

    val list = makeLinkedList(
        listOf(
            1, 1, 1,
            2,
            3, 3,
            5,
            7, 7,
            9
        )
    )

    list?.printIt()

    val result = solve(list)

    result?.printIt()
}

fun solve(head: Node?): Node? {
    var current = head
    while (current != null) {
        if (current.num == (current.next?.num ?: -1)) {
            current.next = current.next?.next
        } else {
            current = current.next
        }
    }
    return head
}


fun makeLinkedList(nums: List<Int>): Node? {
    var head: Node? = null
    var current: Node? = null
    nums.sorted().forEach {
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

class Node(val num: Int) {
    var next: Node? = null

    fun printIt() {
        var current: Node? = this
        println("------------------------------")
        while (current != null) {
            print("${current.num} -> ")
            current = current.next
        }
        println("\n------------------------------")
    }
}