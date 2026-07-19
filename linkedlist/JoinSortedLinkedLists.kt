fun main() {
    val list1 = makeLinkedList(listOf(3,1,4,5,7,9,13,11))
    val list2 = makeLinkedList(listOf(2,3,6,8,10,12,14,15,17,19,21,23))

    list1?.printIt()
    list2?.printIt()

    val result = solve(list1, list2)
    result?.printIt()

}

fun solve(list1: Node? , list2: Node?): Node? {
    var current1 = list1
    var current2 = list2

    var head: Node? = if ((list1?.num ?: Int.MIN_VALUE) < (list2?.num ?: Int.MIN_VALUE)) {
        current1 = current1?.next
        list1
    } else {
        current2 = current2?.next
        list2
    }
    var current: Node? = head

    while (current1 != null && current2 != null) {
        val num1 = current1.num
        val num2 = current2.num
        if (num1 < num2) {
            current?.next = current1
            current1 = current1.next
        } else {
            current?.next = current2
            current2 = current2.next
        }
        current = current?.next
    }

    if (current1 != null)
        current?.next = current1
    
    if (current2 != null)
        current?.next = current2

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
            print("${current.num} ->")
            current = current.next
        }
        println("\n------------------------------")
    }
}