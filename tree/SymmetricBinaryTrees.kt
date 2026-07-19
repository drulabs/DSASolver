/*
Problem: Symmetric Tree

Given the root of a binary tree, check whether it is symmetric
around its center.

A tree is symmetric if the left subtree is a mirror reflection
of the right subtree.

Return true if the tree is symmetric, otherwise false.
*/

class TreeNode(
    val value: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null
)

private fun solve(root: TreeNode?): Boolean {
    if (root == null) return true
    return isSymmetric(root.left, root.right)
}

private fun isSymmetric(left: TreeNode?, right: TreeNode?): Boolean {
    if (left == null && right == null) return true

    if (left == null || right == null || left.value != right.value)
        return false
    
    return isSymmetric(left.left, right.right) && isSymmetric(left.right, right.left)
}

fun main() {
    // Test Case 1
    //         1
    //       /   \
    //      2     2
    //     / \   / \
    //    3   4 4   3
    //
    // Expected: true
    val root1 = TreeNode(1)
    root1.left = TreeNode(2)
    root1.right = TreeNode(2)
    root1.left?.left = TreeNode(3)
    root1.left?.right = TreeNode(4)
    root1.right?.left = TreeNode(4)
    root1.right?.right = TreeNode(3)

    // Test Case 2
    //         1
    //       /   \
    //      2     2
    //       \     \
    //        3     3
    //
    // Expected: false
    val root2 = TreeNode(1)
    root2.left = TreeNode(2)
    root2.right = TreeNode(2)
    root2.left?.right = TreeNode(3)
    root2.right?.right = TreeNode(3)

    // Test Case 3
    //         1
    //       /   \
    //      2     2
    //     /       \
    //    3         3
    //
    // Expected: true
    val root3 = TreeNode(1)
    root3.left = TreeNode(2)
    root3.right = TreeNode(2)
    root3.left?.left = TreeNode(3)
    root3.right?.right = TreeNode(3)

    // Test Case 4
    //      1
    //
    // Expected: true
    val root4 = TreeNode(1)

    // Test Case 5
    // null tree
    //
    // Expected: true
    val root5: TreeNode? = null

    // Test Case 6
    //         1
    //       /   \
    //      2     2
    //     / \   / \
    //    3  5  4  3
    //
    // Expected: false
    val root6 = TreeNode(1)
    root6.left = TreeNode(2)
    root6.right = TreeNode(2)
    root6.left?.left = TreeNode(3)
    root6.left?.right = TreeNode(5)
    root6.right?.left = TreeNode(4)
    root6.right?.right = TreeNode(3)

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