/*
Problem: Path Sum

Given the root of a binary tree and an integer targetSum,
return true if the tree has a root-to-leaf path such that
adding up all the values along the path equals targetSum.

A leaf is a node with no left and no right child.

A valid path:
- Starts at the root.
- Ends at a leaf.
- Moves only from parent to child.
*/

class TreeNode(
    val value: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null
) {
    val isLeaf
        get() = (left == null && right == null)
}

private fun solve(root: TreeNode?, targetSum: Int): Boolean {
    if (root == null) return false
    if (root.isLeaf && targetSum == root.value) return true
    return solve(root.left, targetSum - root.value) || solve(root.right, targetSum - root.value)
}

fun main() {
    // Test Case 1
    //          5
    //        /   \
    //       4     8
    //      /     / \
    //     11    13  4
    //    /  \         \
    //   7    2         1
    //
    // targetSum = 22
    // Expected: true
    val root1 = TreeNode(5)
    root1.left = TreeNode(4)
    root1.right = TreeNode(8)
    root1.left?.left = TreeNode(11)
    root1.left?.left?.left = TreeNode(7)
    root1.left?.left?.right = TreeNode(2)
    root1.right?.left = TreeNode(13)
    root1.right?.right = TreeNode(4)
    root1.right?.right?.right = TreeNode(1)

    // Test Case 2
    //      1
    //     / \
    //    2   3
    //
    // targetSum = 5
    // Expected: false
    val root2 = TreeNode(1)
    root2.left = TreeNode(2)
    root2.right = TreeNode(3)

    // Test Case 3
    //      1
    //     /
    //    2
    //
    // targetSum = 1
    // Expected: false
    val root3 = TreeNode(1)
    root3.left = TreeNode(2)

    // Test Case 4
    //      1
    //
    // targetSum = 1
    // Expected: true
    val root4 = TreeNode(1)

    // Test Case 5
    //        -2
    //          \
    //          -3
    //
    // targetSum = -5
    // Expected: true
    val root5 = TreeNode(-2)
    root5.right = TreeNode(-3)

    // Test Case 6
    // null tree
    //
    // targetSum = 0
    // Expected: false
    val root6: TreeNode? = null

    val inputs = listOf(
        root1 to 22,
        root2 to 5,
        root3 to 1,
        root4 to 1,
        root5 to -5,
        root6 to 0
    )

    val expected = listOf(
        true,
        false,
        false,
        true,
        true,
        false
    )

    println("----------------------------------")
    for (i in inputs.indices) {
        val (root, targetSum) = inputs[i]
        val output = solve(root, targetSum)
        println("input => root=${root?.value}, targetSum=$targetSum")
        println("output => $output - ${if (output == expected[i]) "✅" else "❌"}")
        println("----------------------------------")
    }
}