// Problem: Implement a Stack using a Linked List.
// The stack should support:
// 1. push(value: Int) - Add an element to the top of the stack.
// 2. pop(): Int?       - Remove and return the top element, or null if empty.
// 3. peek(): Int?      - Return the top element without removing it, or null if empty.
// 4. isEmpty(): Boolean - Return true if the stack is empty.

fun main() {
    val stack = Stack()
    
    // Testing the implementation
    println("Is empty: ${stack.isEmpty()}") // Expected: true
    
    stack.push(10)
    stack.push(20)
    println("Peek: ${stack.peek()}")      // Expected: 20
    
    println("Pop: ${stack.pop()}")        // Expected: 20
    println("Pop: ${stack.pop()}")        // Expected: 10
    println("Is empty: ${stack.isEmpty()}") // Expected: true
}

class Stack {
    var head: Node? = null
    
    fun push(value: Int) {
        val node = Node(value)
        node.next = head
        head = node
    }

    fun pop(): Int? {
        val num = head?.num
        head = head?.next
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
