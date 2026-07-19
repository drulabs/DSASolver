/*
Problem: BST Warm-up

Given the root of a Binary Search Tree (BST), implement:

1. findMin(root) -> returns the minimum value, or null if empty.
2. findMax(root) -> returns the maximum value, or null if empty.
3. findAverage(root) -> returns the average of all node values,
   or null if the tree is empty.
*/

class TreeNode(
    val value: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null
)

private fun findMin(root: TreeNode?): Int? {
    var current = root
    while (current?.left != null) {
        current = current.left
    }
    return current?.value
}

private fun findMax(root: TreeNode?): Int? {
    var current = root
    while (current?.right != null) {
        current = current.right
    }
    return current?.value
}

private fun findAverage(root: TreeNode?): Double? {
    if (root == null) return null
    var count = 0
    var sum = 0.0
    val q = ArrayDeque<TreeNode>()
    q.addLast(root)

    while (q.isNotEmpty()) {
        val node = q.removeFirst()
        count++
        sum += node.value
        node.left?.let { q.addLast(it) }
        node.right?.let { q.addLast(it) }
    }
    
    return sum/count
}

fun main() {

    // Test Case 1
    //        4
    //      /   \
    //     2     7
    //    / \
    //   1   3
    //
    // Min = 1
    // Max = 7
    // Avg = 3.4
    val root1 = TreeNode(4)
    root1.left = TreeNode(2)
    root1.right = TreeNode(7)
    root1.left?.left = TreeNode(1)
    root1.left?.right = TreeNode(3)

    // Test Case 2
    //      5
    //
    // Min = 5
    // Max = 5
    // Avg = 5.0
    val root2 = TreeNode(5)

    // Test Case 3
    //        8
    //       /
    //      6
    //     /
    //    4
    //   /
    //  2
    //
    // Min = 2
    // Max = 8
    // Avg = 5.0
    val root3 = TreeNode(8)
    root3.left = TreeNode(6)
    root3.left?.left = TreeNode(4)
    root3.left?.left?.left = TreeNode(2)

    // Test Case 4
    //      1
    //       \
    //        2
    //         \
    //          3
    //
    // Min = 1
    // Max = 3
    // Avg = 2.0
    val root4 = TreeNode(1)
    root4.right = TreeNode(2)
    root4.right?.right = TreeNode(3)

    // Test Case 5
    // null tree
    //
    // Min = null
    // Max = null
    // Avg = null
    val root5: TreeNode? = null

    val inputs = listOf(root1, root2, root3, root4, root5)

    val expectedMin = listOf(1, 5, 2, 1, null)
    val expectedMax = listOf(7, 5, 8, 3, null)
    val expectedAvg = listOf(3.4, 5.0, 5.0, 2.0, null)

    println("-------------------------------------------------------")
    for (i in inputs.indices) {

        val min = findMin(inputs[i])
        val max = findMax(inputs[i])
        val avg = findAverage(inputs[i])

        println("input => root=${inputs[i]?.value}")

        println("min => $min ${if (min == expectedMin[i]) "✅" else "❌"}")
        println("max => $max ${if (max == expectedMax[i]) "✅" else "❌"}")
        println("avg => $avg ${if (avg == expectedAvg[i]) "✅" else "❌"}")

        println("-------------------------------------------------------")
    }
}