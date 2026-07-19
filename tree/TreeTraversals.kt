import java.util.*

class TreeTraversals {
    
    fun preOrder(node: Node?) {
        node?.value?.let {
            print("$it -> ")
            preOrder(node.left)
            preOrder(node.right)
        }
    }

    fun inOrder(node: Node?) {
        node?.value?.let {
            inOrder(node.left)
            print("$it -> ")
            inOrder(node.right)
        }
    }

    // TODO: Implement Post-order (Left -> Right -> Root)
    fun postOrder(node: Node?) {
        node?.value?.let {
            postOrder(node.left)
            postOrder(node.right)
            print("$it -> ")
        }
    }

    fun levelOrder(node: Node?) {
        if (node == null) return
        val q: Queue<Node> = LinkedList()
        q.add(node)
        while(q.isNotEmpty()) {
            val levelSize = q.size
            for (i in 1..levelSize) {
                val current = q.poll()
                print("${current.value} -> ")
                current.left?.let { q.add(it) }
                current.right?.let { q.add(it) }
            }
            println()
        }
    }

    
    fun verticalOrder(node: Node?) {
        if (node == null) return
        val nodeMap = mutableMapOf<Int, MutableList<Node>>()
        preOrderWithHD(node, 0, nodeMap)
        println()
        val (low, high) = nodeMap.keys.let { it.min() to it.max() }
        for (key in low..high) {
            nodeMap[key]?.let {
                print("hd = $key ==> ")
                println(it.map { "${it.value}" }.joinToString(" -> "))
            }
        }
        println()
    }

    private fun preOrderWithHD(node: Node?, hd: Int = 0, map: MutableMap<Int, MutableList<Node>> = mutableMapOf<Int, MutableList<Node>>()) {
        if (node == null) return
        map.getOrPut(hd) { mutableListOf<Node>() }.add(node)
        preOrderWithHD(node.left, hd - 1, map)
        preOrderWithHD(node.right, hd + 1, map)
    }
}

fun main() {
    // Constructing a simple tree:
    //      1
    //    /   \
    //   2     3
    //  / \     \
    // 4   5     6
    //  \ /
    //  7 8
    val root = Node(1)
    root.left = Node(2)
    root.right = Node(3)
    root.right?.right = Node(6)
    root.left?.left = Node(4)
    root.left?.left?.right = Node(7)
    root.left?.right = Node(5)
    root.left?.right?.left = Node(8)

    val traverser = TreeTraversals()

    print("Pre-order: ")
    traverser.preOrder(root) // Expected: 1 2 4 7 5 8 3 6
    println()

    print("In-order: ")
    traverser.inOrder(root)  // Expected: 4 7 2 8 5 1 3 6
    println()

    print("Post-order: ")
    traverser.postOrder(root) // Expected: 7 4 8 5 2 6 3 1
    println()

    print("Level-order: \n")
    traverser.levelOrder(root) // Expected: 1 2 3 4 5 6 7 8
    println()

    print("Vertical-order: \n")
    traverser.verticalOrder(root) // Expected: [7], [4], [2, 8], [1, 5], [3], [6]
    println()
}

class Node(val value: Int) {
    var left: Node? = null
    var right: Node? = null
}