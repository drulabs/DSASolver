/**
    * Problem: Diameter of Binary Tree

    * Return the diameter of the binary tree.

    * The diameter is the number of edges on the longest path between any two nodes.
    * The path does not have to pass through the root.
 */
 class TreeNode(
    val value: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null
) {
    val isLeaf
        get() = (left == null && right == null)
}

private fun solve(root: TreeNode?): Int {
    return getHeightAndDiameter(root).second
}

private fun getHeightAndDiameter(node: TreeNode?): Pair<Int, Int> {
    if (node == null) return (0 to 0)
    val (leftHeight, leftDia) = getHeightAndDiameter(node.left)
    val (rightHeight, rightDia) = getHeightAndDiameter(node.right)
    val diameter = maxOf(leftDia, rightDia, leftHeight + rightHeight)
    return (1 + maxOf(leftHeight, rightHeight) to diameter)
}

fun main() {
    // Test Case 1
    //      1
    //     / \
    //    2   3
    //   / \
    //  4   5
    val root1 = TreeNode(1)
    root1.left = TreeNode(2)
    root1.right = TreeNode(3)
    root1.left?.left = TreeNode(4)
    root1.left?.right = TreeNode(5)

    // Test Case 2
    //      1
    //       \
    //        2
    //         \
    //          3
    //           \
    //            4
    val root2 = TreeNode(1)
    root2.right = TreeNode(2)
    root2.right?.right = TreeNode(3)
    root2.right?.right?.right = TreeNode(4)

    // Test Case 3
    //      1
    val root3 = TreeNode(1)

    // Test Case 4
    // null tree
    val root4: TreeNode? = null

    // Test Case 5
    //          1
    //        /   \
    //       2     3
    //      /     / \
    //     4     5   6
    //    /           \
    //   7             8
    val root5 = TreeNode(1)
    root5.left = TreeNode(2)
    root5.right = TreeNode(3)
    root5.left?.left = TreeNode(4)
    root5.left?.left?.left = TreeNode(7)
    root5.right?.left = TreeNode(5)
    root5.right?.right = TreeNode(6)
    root5.right?.right?.right = TreeNode(8)

    val inputs = listOf(root1, root2, root3, root4, root5)
    val expected = listOf(3, 3, 0, 0, 6)

    println("----------------------------------")
    for (i in inputs.indices) {
        val output = solve(inputs[i])
        println("input => Tree root ${inputs[i]?.value}")
        println("output => $output - ${if (output == expected[i]) "✅" else "❌"}")
        println("----------------------------------")
    }
}