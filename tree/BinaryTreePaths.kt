/*
Problem: Search in a Binary Search Tree

Given the root of a Binary Search Tree (BST) and an integer value,
return the subtree rooted at the node whose value equals the given value.

If the node does not exist, return null.
*/

class TreeNode(
    val value: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null
)

private fun solve(root: TreeNode?, value: Int): TreeNode? {
    if (root == null) return null
    if (root.value == value) return root

    return if (value < root.value) {
        solve(root.left, value)
    } else {
        solve(root.right, value)
    }
}

// this is better spacewise and is preferred solution
private fun solveIterative(root: TreeNode?, value: Int): TreeNode? {
    var current = root
    while (current != null) {
        when {
            value < current.value -> current = current.left
            value > current.value -> current = current.right
            else -> return current
        }
    }

    return null
}

fun main() {

    // Test Case 1
    //        4
    //      /   \
    //     2     7
    //    / \
    //   1   3
    //
    // Search: 2
    // Expected: 2
    val root1 = TreeNode(4)
    root1.left = TreeNode(2)
    root1.right = TreeNode(7)
    root1.left?.left = TreeNode(1)
    root1.left?.right = TreeNode(3)

    // Test Case 2
    //        4
    //      /   \
    //     2     7
    //    / \
    //   1   3
    //
    // Search: 5
    // Expected: null
    val root2 = TreeNode(4)
    root2.left = TreeNode(2)
    root2.right = TreeNode(7)
    root2.left?.left = TreeNode(1)
    root2.left?.right = TreeNode(3)

    // Test Case 3
    //      1
    //
    // Search: 1
    // Expected: 1
    val root3 = TreeNode(1)

    // Test Case 4
    //      1
    //
    // Search: 2
    // Expected: null
    val root4 = TreeNode(1)

    // Test Case 5
    // null tree
    //
    // Search: 1
    // Expected: null
    val root5: TreeNode? = null

    val inputs = listOf(root1, root2, root3, root4, root5)

    val values = listOf(
        2,
        5,
        1,
        2,
        1
    )

    val expected = listOf(
        2,
        null,
        1,
        null,
        null
    )

    println("----------------------------------")
    for (i in inputs.indices) {
        val output = solve(inputs[i], values[i])?.value
        val answer = expected[i]

        println("input => root=${inputs[i]?.value}, value=${values[i]}")
        println("output => $output - ${if (output == answer) "✅" else "❌"}")
        println("----------------------------------")
    }
}