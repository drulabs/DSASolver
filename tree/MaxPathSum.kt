/*
Problem: Binary Tree Maximum Path Sum

Given the root of a binary tree, return the maximum path sum.

A path is any sequence of nodes where each adjacent pair is connected by an edge.
A path does not need to pass through the root.
A path must contain at least one node.
Each node can appear in the path at most once.

The path sum is the sum of all node values in the path.
*/

class TreeNode(
    val value: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null
)

private fun solve(root: TreeNode?): Int {
    return maxPathSum(root).max
}

data class Result(val gain: Int, val max: Int)

private fun maxPathSum(node: TreeNode?): Result {
    if (node == null) return Result(0, Int.MIN_VALUE)

    val left = maxPathSum(node.left)
    val right = maxPathSum(node.right)

    val leftGain = maxOf(0, left.gain)
    val rightGain = maxOf(0, right.gain)

    val gain = node.value + maxOf(leftGain, rightGain)
    val max = maxOf(left.max, right.max, leftGain + rightGain + node.value)

    return Result(gain, max)
}

fun main() {
    // Test Case 1
    //      1
    //     / \
    //    2   3
    //
    // Maximum path: 2 -> 1 -> 3 = 6
    val root1 = TreeNode(1)
    root1.left = TreeNode(2)
    root1.right = TreeNode(3)

    // Test Case 2
    //      -10
    //      /  \
    //     9    20
    //         /  \
    //        15   7
    //
    // Maximum path: 15 -> 20 -> 7 = 42
    val root2 = TreeNode(-10)
    root2.left = TreeNode(9)
    root2.right = TreeNode(20)
    root2.right?.left = TreeNode(15)
    root2.right?.right = TreeNode(7)

    // Test Case 3
    //      -3
    //
    // Maximum path: -3
    val root3 = TreeNode(-3)

    // Test Case 4
    //        2
    //       /
    //     -1
    //
    // Maximum path: 2
    val root4 = TreeNode(2)
    root4.left = TreeNode(-1)

    // Test Case 5
    //          5
    //        /   \
    //       4     8
    //      /     / \
    //     11    13  4
    //    /  \        \
    //   7    2        1
    //
    // Maximum path: 7 -> 11 -> 4 -> 5 -> 8 -> 13 = 48
    val root5 = TreeNode(5)
    root5.left = TreeNode(4)
    root5.right = TreeNode(8)
    root5.left?.left = TreeNode(11)
    root5.left?.left?.left = TreeNode(7)
    root5.left?.left?.right = TreeNode(2)
    root5.right?.left = TreeNode(13)
    root5.right?.right = TreeNode(4)
    root5.right?.right?.right = TreeNode(1)

    // Test Case 6
    //        2
    //       /
    //      3
    //       \
    //       -2
    //
    // Maximum path: 3 -> 2 = 5
    val root6 = TreeNode(2)
    root6.left = TreeNode(3)
    root6.left?.right = TreeNode(-2)

    val inputs = listOf(root1, root2, root3, root4, root5, root6)
    val expected = listOf(6, 42, -3, 2, 48, 5)

    println("----------------------------------")
    for (i in inputs.indices) {
        val output = solve(inputs[i])
        println("input => Tree root ${inputs[i].value}")
        println("output => $output - ${if (output == expected[i]) "✅" else "❌"}")
        println("----------------------------------")
    }
}