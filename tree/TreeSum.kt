/*
Problem: Find the Minimum, Maximum and Sum of a Binary Tree

Given the root of a binary tree, return:

- The minimum value in the tree.
- The maximum value in the tree.
- The sum of all node values.

You may assume the tree is non-empty.
*/

class TreeNode(
    val value: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null
)

data class Result(
    val min: Int,
    val max: Int,
    val sum: Int
)

private fun solve(root: TreeNode?): Result {
    if (root == null) return Result(Int.MAX_VALUE, Int.MIN_VALUE, 0)

    val left = solve(root.left)
    val right = solve(root.right)

    return Result(
        minOf(left.min, right.min, root.value), 
        maxOf(left.max, right.max, root.value), 
        root.value + left.sum + right.sum
    )
}

fun main() {
    // Test Case 1
    //        5
    //      /   \
    //     3     8
    //    / \   / \
    //   1   4 7   9
    //
    // min = 1, max = 9, sum = 37
    val root1 = TreeNode(5)
    root1.left = TreeNode(3)
    root1.right = TreeNode(8)
    root1.left?.left = TreeNode(1)
    root1.left?.right = TreeNode(4)
    root1.right?.left = TreeNode(7)
    root1.right?.right = TreeNode(9)

    // Test Case 2
    //      -10
    //      /  \
    //    -20   5
    //          /
    //         2
    //
    // min = -20, max = 5, sum = -23
    val root2 = TreeNode(-10)
    root2.left = TreeNode(-20)
    root2.right = TreeNode(5)
    root2.right?.left = TreeNode(2)

    // Test Case 3
    //      42
    //
    // min = 42, max = 42, sum = 42
    val root3 = TreeNode(42)

    // Test Case 4
    //        0
    //      /   \
    //    -5     5
    //    /       \
    //  -10       10
    //
    // min = -10, max = 10, sum = 0
    val root4 = TreeNode(0)
    root4.left = TreeNode(-5)
    root4.right = TreeNode(5)
    root4.left?.left = TreeNode(-10)
    root4.right?.right = TreeNode(10)

    // Test Case 5
    //         8
    //        /
    //       3
    //      /
    //     1
    //
    // min = 1, max = 8, sum = 12
    val root5 = TreeNode(8)
    root5.left = TreeNode(3)
    root5.left?.left = TreeNode(1)

    val inputs = listOf(root1, root2, root3, root4, root5)

    val expected = listOf(
        Result(1, 9, 37),
        Result(-20, 5, -23),
        Result(42, 42, 42),
        Result(-10, 10, 0),
        Result(1, 8, 12)
    )

    println("----------------------------------")
    for (i in inputs.indices) {
        val output = solve(inputs[i])
        println("input => Tree root ${inputs[i]?.value}")
        println("output => $output - ${if (output == expected[i]) "✅" else "❌"}")
        println("----------------------------------")
    }
}