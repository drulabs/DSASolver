/*
Problem: Count Good Nodes in Binary Tree

Given the root of a binary tree, return the number of good nodes.

A node X is considered good if, on the path from the root to X,
there is no node with a value greater than X's value.

In other words, X is good if:

X.value >= maximum value seen on the path from the root to X

The root is always a good node.
*/

class TreeNode(
    val value: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null
)

var goodNodes = 0

private fun solve(root: TreeNode?, maxSoFar: Int = Int.MIN_VALUE): Int {
    if (root == null) return 0
    
    val isGood = root.value >= maxSoFar
    val newMax = maxOf(maxSoFar, root.value)

    return (if (isGood) 1 else 0) +
        solve(root.left, newMax) +
        solve(root.right, newMax)
}

fun main() {
    // Test Case 1
    //          3
    //        /   \
    //       1     4
    //      /     / \
    //     3     1   5
    //
    // Good nodes: 3(root), 3(left-left), 4, 5
    // Expected: 4
    val root1 = TreeNode(3)
    root1.left = TreeNode(1)
    root1.right = TreeNode(4)
    root1.left?.left = TreeNode(3)
    root1.right?.left = TreeNode(1)
    root1.right?.right = TreeNode(5)

    // Test Case 2
    //          3
    //         /
    //        3
    //       / \
    //      4   2
    //
    // Good nodes: 3(root), 3, 4
    // Expected: 3
    val root2 = TreeNode(3)
    root2.left = TreeNode(3)
    root2.left?.left = TreeNode(4)
    root2.left?.right = TreeNode(2)

    // Test Case 3
    //          5
    //         /
    //        4
    //       /
    //      3
    //     /
    //    2
    //
    // Only the root is good
    // Expected: 1
    val root3 = TreeNode(5)
    root3.left = TreeNode(4)
    root3.left?.left = TreeNode(3)
    root3.left?.left?.left = TreeNode(2)

    // Test Case 4
    //         -2
    //        /  \
    //      -3   -1
    //      /
    //    -2
    //
    // Good nodes: -2(root), -1, -2(left-left)
    // Expected: 3
    val root4 = TreeNode(-2)
    root4.left = TreeNode(-3)
    root4.right = TreeNode(-1)
    root4.left?.left = TreeNode(-2)

    // Test Case 5
    //       7
    //
    // Expected: 1
    val root5 = TreeNode(7)

    // Test Case 6
    // null tree
    //
    // Expected: 0
    val root6: TreeNode? = null

    val inputs = listOf(root1, root2, root3, root4, root5, root6)
    val expected = listOf(4, 3, 1, 3, 1, 0)

    println("----------------------------------")
    for (i in inputs.indices) {
        val output = solve(inputs[i])
        println("input => Tree root ${inputs[i]?.value}")
        println("output => $output - ${if (output == expected[i]) "✅" else "❌"}")
        println("----------------------------------")
    }
}