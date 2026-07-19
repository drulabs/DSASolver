/*
Problem: Invert Binary Tree

Given the root of a binary tree, invert the tree and return its root.

Inverting a binary tree means swapping the left and right child of every node.

Example:

Original:
        4
      /   \
     2     7
    / \   / \
   1   3 6   9

Inverted:
        4
      /   \
     7     2
    / \   / \
   9   6 3   1
*/

class TreeNode(
    val value: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null
)

private fun solve(root: TreeNode?): TreeNode? {
    if (root == null) return null
    val left = solve(root.left)
    val right = solve(root.right)
    root.left = right
    root.right = left
    return root
}

private fun isSameTree(a: TreeNode?, b: TreeNode?): Boolean {
    if (a == null && b == null) return true
    if (a == null || b == null) return false

    return a.value == b.value &&
            isSameTree(a.left, b.left) &&
            isSameTree(a.right, b.right)
}

fun main() {
    // Test Case 1
    // Original:
    //        4
    //      /   \
    //     2     7
    //    / \   / \
    //   1   3 6   9
    val root1 = TreeNode(4)
    root1.left = TreeNode(2)
    root1.right = TreeNode(7)
    root1.left?.left = TreeNode(1)
    root1.left?.right = TreeNode(3)
    root1.right?.left = TreeNode(6)
    root1.right?.right = TreeNode(9)

    val expected1 = TreeNode(4)
    expected1.left = TreeNode(7)
    expected1.right = TreeNode(2)
    expected1.left?.left = TreeNode(9)
    expected1.left?.right = TreeNode(6)
    expected1.right?.left = TreeNode(3)
    expected1.right?.right = TreeNode(1)

    // Test Case 2
    //      1
    //     /
    //    2
    val root2 = TreeNode(1)
    root2.left = TreeNode(2)

    val expected2 = TreeNode(1)
    expected2.right = TreeNode(2)

    // Test Case 3
    //      1
    val root3 = TreeNode(1)

    val expected3 = TreeNode(1)

    // Test Case 4
    // null tree
    val root4: TreeNode? = null
    val expected4: TreeNode? = null

    // Test Case 5
    //        5
    //       /
    //      3
    //       \
    //        4
    val root5 = TreeNode(5)
    root5.left = TreeNode(3)
    root5.left?.right = TreeNode(4)

    val expected5 = TreeNode(5)
    expected5.right = TreeNode(3)
    expected5.right?.left = TreeNode(4)

    val inputs = listOf(root1, root2, root3, root4, root5)
    val expected = listOf(expected1, expected2, expected3, expected4, expected5)

    println("----------------------------------")
    for (i in inputs.indices) {
        val output = solve(inputs[i])
        println("Test Case ${i + 1}: ${if (isSameTree(output, expected[i])) "✅" else "❌"}")
        println("----------------------------------")
    }
}
