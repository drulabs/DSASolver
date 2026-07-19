/*
Problem: Range Sum of BST

Given the root of a Binary Search Tree and two integers low and high,
return the sum of values of all nodes with a value in the inclusive range
[low, high].
*/

class TreeNode(
    val value: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null
)

private fun solve(root: TreeNode?, low: Int, high: Int): Int {
    if (root == null) return 0

    val sumFromLeft = if (root.value < low) 0 else solve(root.left, low, high)
    val sumFromRight = if (root.value > high) 0 else solve(root.right, low, high)

    return sumFromLeft + sumFromRight + (if (root.value in low..high) root.value else 0)
}

fun main() {

    // Test Case 1
    //
    //          10
    //        /    \
    //       5      15
    //      / \       \
    //     3   7       18
    //
    // low = 7, high = 15
    // Expected = 32 (7 + 10 + 15)
    val root1 = TreeNode(10)
    root1.left = TreeNode(5)
    root1.right = TreeNode(15)
    root1.left?.left = TreeNode(3)
    root1.left?.right = TreeNode(7)
    root1.right?.right = TreeNode(18)

    // Test Case 2
    //
    //          10
    //        /    \
    //       5      15
    //      / \    /  \
    //     3   7  13  18
    //    /         \
    //   1           14
    //
    // low = 6, high = 10
    // Expected = 17 (7 + 10)
    val root2 = TreeNode(10)
    root2.left = TreeNode(5)
    root2.right = TreeNode(15)
    root2.left?.left = TreeNode(3)
    root2.left?.right = TreeNode(7)
    root2.left?.left?.left = TreeNode(1)
    root2.right?.left = TreeNode(13)
    root2.right?.right = TreeNode(18)
    root2.right?.left?.right = TreeNode(14)

    // Test Case 3
    //
    //      5
    //
    // low = 6, high = 10
    // Expected = 0
    val root3 = TreeNode(5)

    // Test Case 4
    //
    // null
    //
    // Expected = 0
    val root4: TreeNode? = null

    // Test Case 5
    //
    //      8
    //     / \
    //    4  12
    //   / \ / \
    //  2 6 10 14
    //
    // low = 1, high = 20
    // Expected = 56
    val root5 = TreeNode(8)
    root5.left = TreeNode(4)
    root5.right = TreeNode(12)
    root5.left?.left = TreeNode(2)
    root5.left?.right = TreeNode(6)
    root5.right?.left = TreeNode(10)
    root5.right?.right = TreeNode(14)

    val inputs = listOf(root1, root2, root3, root4, root5)

    val ranges = listOf(
        7 to 15,
        6 to 10,
        6 to 10,
        1 to 10,
        1 to 20
    )

    val expected = listOf(
        32,
        17,
        0,
        0,
        56
    )

    println("----------------------------------")
    for (i in inputs.indices) {
        val (low, high) = ranges[i]
        val output = solve(inputs[i], low, high)
        val answer = expected[i]

        println("range = [$low, $high]")
        println("output => $output ${if (output == answer) "✅" else "❌"}")
        println("----------------------------------")
    }
}