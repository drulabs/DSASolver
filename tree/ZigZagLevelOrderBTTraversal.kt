/*
Problem: Binary Tree Zigzag Level Order Traversal

Given the root of a binary tree, return the zigzag level order traversal
of its nodes' values.

The first level is traversed from left to right,
the second from right to left,
the third from left to right, and so on.

Return the result as a list of levels.
*/

import java.util.*

class TreeNode(
    val value: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null
)

private fun solve(root: TreeNode?): List<List<Int>> {
    if (root == null) return listOf()
    
    val q = ArrayDeque<TreeNode>()
    q.addLast(root)

    val result = mutableListOf<List<Int>>()
    var goRight = true

    while(q.isNotEmpty()) {
        val size = q.size
        val currentList = mutableListOf<Int>()
        repeat (size) {
            val node = q.removeFirst()
            currentList.add(node.value)            
            node.left?.let { q.addLast(it) }
            node.right?.let { q.addLast(it) }
        }
        result.add(if (goRight) currentList else currentList.reversed())
        goRight = !goRight
    }

    return result
}

fun main() {
    // Test Case 1
    //         3
    //       /   \
    //      9     20
    //           /  \
    //          15   7
    //
    // Expected:
    // [[3], [20, 9], [15, 7]]
    val root1 = TreeNode(3)
    root1.left = TreeNode(9)
    root1.right = TreeNode(20)
    root1.right?.left = TreeNode(15)
    root1.right?.right = TreeNode(7)

    // Test Case 2
    //      1
    //
    // Expected:
    // [[1]]
    val root2 = TreeNode(1)

    // Test Case 3
    // null tree
    //
    // Expected:
    // []
    val root3: TreeNode? = null

    // Test Case 4
    //          1
    //        /   \
    //       2     3
    //      / \   / \
    //     4  5  6   7
    //
    // Expected:
    // [[1], [3,2], [4,5,6,7]]
    val root4 = TreeNode(1)
    root4.left = TreeNode(2)
    root4.right = TreeNode(3)
    root4.left?.left = TreeNode(4)
    root4.left?.right = TreeNode(5)
    root4.right?.left = TreeNode(6)
    root4.right?.right = TreeNode(7)

    // Test Case 5
    //          1
    //        /   \
    //       2     3
    //        \   /
    //         5 6
    //
    // Expected:
    // [[1], [3,2], [5,6]]
    val root5 = TreeNode(1)
    root5.left = TreeNode(2)
    root5.right = TreeNode(3)
    root5.left?.right = TreeNode(5)
    root5.right?.left = TreeNode(6)

    val inputs = listOf(root1, root2, root3, root4, root5)

    val expected = listOf(
        listOf(listOf(3), listOf(20, 9), listOf(15, 7)),
        listOf(listOf(1)),
        emptyList(),
        listOf(listOf(1), listOf(3, 2), listOf(4, 5, 6, 7)),
        listOf(listOf(1), listOf(3, 2), listOf(5, 6))
    )

    println("----------------------------------")
    for (i in inputs.indices) {
        val output = solve(inputs[i])
        println("input => Tree root ${inputs[i]?.value}")
        println("output => $output - ${if (output == expected[i]) "✅" else "❌"}")
        println("----------------------------------")
    }
}