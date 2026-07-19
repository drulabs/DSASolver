class Queue {
    var head: Node? = null
    var tail: Node? = null
    
    fun enqueue(value: Int) {
        val node = Node(value)
        if (head == null && tail == null) {
            head = node
            tail = node
        } else {
            tail?.next = node
            tail = tail?.next
        }
    }

    fun dequeue(): Int? {
        val num = head?.num
        head = head?.next
        if (head == null) {
            tail = null
        }
        return num
    }

    fun peek(): Int? {
        return head?.num
    }

    fun isEmpty(): Boolean {
        return (head == null)
    }
}

class Node (val num: Int) {
    var next: Node? = null
}

fun main() {
    val queue = Queue()

    println("Is empty: ${queue.isEmpty()}") // Expected: true

    queue.enqueue(10)
    queue.enqueue(20)
    queue.enqueue(30)
    
    println("Peek: ${queue.peek()}")        // Expected: 10
    
    println("Dequeue: ${queue.dequeue()}")  // Expected: 10
    println("Dequeue: ${queue.dequeue()}")  // Expected: 20
    
    queue.enqueue(40)
    println("Dequeue: ${queue.dequeue()}")  // Expected: 30
    println("Dequeue: ${queue.dequeue()}")  // Expected: 40
    println("Is empty: ${queue.isEmpty()}") // Expected: true
}