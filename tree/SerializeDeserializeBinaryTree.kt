/*
Problem: Serialize and Deserialize Binary Tree

Serialization is the process of converting a binary tree into a string
so that it can later be reconstructed.

Implement the Codec class.

class Codec {
    fun serialize(root: TreeNode?): String
    fun deserialize(data: String): TreeNode?
}

The following should always hold true:

val codec = Codec()
val data = codec.serialize(root)
val restored = codec.deserialize(data)

isSameTree(root, restored) == true

You may choose ANY serialization format you like.

Constraints:
- Number of nodes: [0, 10^4]
- Node values: [-1000, 1000]
*/

class TreeNode(
    val value: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null
)

// Serialization and de-serialization using level order values
// this solution works but takes a shit load of space
// class Codec {

//     fun serialize(root: TreeNode?): String {
//         if (root == null) return ""
        
//         var result = mutableListOf<Int?>()
//         val q = ArrayDeque<TreeNode?>()
        
//         q.addLast(root)

//         while(q.isNotEmpty() && q.any { it != null }) {
//             repeat(q.size) {
//                 val node = q.removeFirst()
//                 result.add(node?.value)
//                 q.addLast(node?.left)
//                 q.addLast(node?.right)
//             }
//         }

//         return result.joinToString(",")
//     }

//     fun deserialize(data: String): TreeNode? {
//         if (data.isEmpty() || data == "null") return null

//         val nodes = data.split(",").map {
//             if (it == "null") null else TreeNode(it.toInt())
//         }

//         nodes.forEachIndexed { i, node ->
//             val leftIndex = 2*i + 1
//             val rightIndex = 2*i + 2
            
//             node?.left = if (leftIndex < nodes.size) nodes[leftIndex] else null
//             node?.right = if (rightIndex < nodes.size) nodes[rightIndex] else null
//         }

//         return nodes.first()
//     }
// }

/** ---- Attempting with just preorder with termination ---- **/
class Codec {

    fun serialize(root: TreeNode?): String {
        if (root == null) return "null,"
        return "${root.value}," + serialize(root.left) + serialize(root.right)
    }

    fun deserialize(data: String): TreeNode? {
        if (data.isEmpty() || data == "null,") return null
        
        val nodes = data.split(",").filter { it.isNotEmpty() }.map {
            if (it == "null") null else TreeNode(it.toInt())
        }.let { ArrayDeque(it) }

        return restore(nodes)
    }

    private fun restore(nodes: ArrayDeque<TreeNode?>): TreeNode? {
        if (nodes.isEmpty()) return null

        val current = nodes.removeFirst()
        current?.left = restore(nodes)
        current?.right = restore(nodes)

        return current
    }
}

private fun isSameTree(a: TreeNode?, b: TreeNode?): Boolean {
    if (a == null && b == null) return true
    if (a == null || b == null) return false
    if (a.value != b.value) return false

    return isSameTree(a.left, b.left) &&
            isSameTree(a.right, b.right)
}

fun main() {

    val codec = Codec()

    // Test Case 1
    //
    //         1
    //       /   \
    //      2     3
    //           / \
    //          4   5
    //
    val root1 = TreeNode(1)
    root1.left = TreeNode(2)
    root1.right = TreeNode(3)
    root1.right?.left = TreeNode(4)
    root1.right?.right = TreeNode(5)

    // Test Case 2
    //
    // null
    //
    val root2: TreeNode? = null

    // Test Case 3
    //
    //      10
    //
    val root3 = TreeNode(10)

    // Test Case 4
    //
    //      1
    //     /
    //    2
    //   /
    //  3
    // /
    //4
    //
    val root4 = TreeNode(1)
    root4.left = TreeNode(2)
    root4.left?.left = TreeNode(3)
    root4.left?.left?.left = TreeNode(4)

    // Test Case 5
    //
    // 1
    //  \
    //   2
    //    \
    //     3
    //      \
    //       4
    //
    val root5 = TreeNode(1)
    root5.right = TreeNode(2)
    root5.right?.right = TreeNode(3)
    root5.right?.right?.right = TreeNode(4)

    // Test Case 6
    //
    //             8
    //          /     \
    //         4       12
    //       /  \     /  \
    //      2    6   10  14
    //     / \      /
    //    1   3    9
    //
    val root6 = TreeNode(8)
    root6.left = TreeNode(4)
    root6.right = TreeNode(12)

    root6.left?.left = TreeNode(2)
    root6.left?.right = TreeNode(6)

    root6.right?.left = TreeNode(10)
    root6.right?.right = TreeNode(14)

    root6.left?.left?.left = TreeNode(1)
    root6.left?.left?.right = TreeNode(3)

    root6.right?.left?.left = TreeNode(9)

    val inputs = listOf(root1, root2, root3, root4, root5, root6)

    println("-------------------------------------")

    for ((index, root) in inputs.withIndex()) {
        val serialized = codec.serialize(root)
        val restored = codec.deserialize(serialized)

        println("Test ${index + 1}")
        println("Serialized : $serialized")
        println("Restored   : ${isSameTree(root, restored)} ${if (isSameTree(root, restored)) "✅" else "❌"}")
        println("-------------------------------------")
    }
}