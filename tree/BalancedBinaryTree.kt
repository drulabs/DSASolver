/*
Problem: Balanced Binary Tree

Given the root of a binary tree, determine if it is height-balanced.

A height-balanced binary tree is a binary tree in which the depths of the
left and right subtrees of every node differ by no more than 1.

Return true if the tree is balanced, otherwise false.
*/
import kotlin.math.abs

class TreeNode(
    val value: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null
)

private fun solve(root: TreeNode?): Boolean {
    return getHeight(root) != -1
}

private fun getHeight(node: TreeNode?): Int {
    if (node == null) return 0

    val leftHeight = getHeight(node.left)
    if (leftHeight == -1) return -1
    val rightHeight = getHeight(node.right)
    if (rightHeight == -1) return -1

    if (leftHeight == -1 || rightHeight == -1) {
        return -1
    }

    if (abs(leftHeight - rightHeight) > 1) return -1

    return 1 + maxOf(leftHeight, rightHeight)
}

fun main() {
    // Test Case 1
    //        3
    //      /   \
    //     9     20
    //          /  \
    //         15   7
    //
    // Expected: true
    val root1 = TreeNode(3)
    root1.left = TreeNode(9)
    root1.right = TreeNode(20)
    root1.right?.left = TreeNode(15)
    root1.right?.right = TreeNode(7)

    // Test Case 2
    //          1
    //         /
    //        2
    //       /
    //      3
    //     /
    //    4
    //
    // Expected: false
    val root2 = TreeNode(1)
    root2.left = TreeNode(2)
    root2.left?.left = TreeNode(3)
    root2.left?.left?.left = TreeNode(4)

    // Test Case 3
    //        1
    //
    // Expected: true
    val root3 = TreeNode(1)

    // Test Case 4
    // null tree
    //
    // Expected: true
    val root4: TreeNode? = null

    // Test Case 5
    //           1
    //         /   \
    //        2     3
    //       / \     \
    //      4   5     6
    //         /
    //        7
    //
    // Expected: true
    val root5 = TreeNode(1)
    root5.left = TreeNode(2)
    root5.right = TreeNode(3)
    root5.left?.left = TreeNode(4)
    root5.left?.right = TreeNode(5)
    root5.left?.right?.left = TreeNode(7)
    root5.right?.right = TreeNode(6)

    // Test Case 6
    //           1
    //         /   \
    //        2     3
    //       /
    //      4
    //     /
    //    5
    //
    // Expected: false
    val root6 = TreeNode(1)
    root6.left = TreeNode(2)
    root6.right = TreeNode(3)
    root6.left?.left = TreeNode(4)
    root6.left?.left?.left = TreeNode(5)

    val inputs = listOf(root1, root2, root3, root4, root5, root6)
    val expected = listOf(true, false, true, true, true, false)

    println("----------------------------------")
    for (i in inputs.indices) {
        val output = solve(inputs[i])
        println("input => Tree root ${inputs[i]?.value}")
        println("output => $output - ${if (output == expected[i]) "✅" else "❌"}")
        println("----------------------------------")
    }
}