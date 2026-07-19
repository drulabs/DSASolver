/*
Problem: Sum Root-to-Leaf Numbers

You are given the root of a binary tree containing digits from 0 to 9.

Each root-to-leaf path represents a number formed by concatenating
the digits along the path.

Return the sum of all numbers represented by root-to-leaf paths.

A leaf is a node with no left and no right child.

Example:

    1
   / \
  2   3

The root-to-leaf paths represent:

1 -> 2 = 12
1 -> 3 = 13

Answer: 12 + 13 = 25
*/

class TreeNode(
    val value: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null
) {
    val isLeaf: Boolean
        get() = left == null && right == null
}

private fun solve(root: TreeNode?, sum: Int = 0): Int {
    if (root == null) return 0

    val newSum = sum * 10 + root.value
    if (root.isLeaf) return newSum

    return solve(root.left, newSum) + solve(root.right, newSum)
}

fun main() {
    // Test Case 1
    //      1
    //     / \
    //    2   3
    //
    // Numbers: 12, 13
    // Expected: 25
    val root1 = TreeNode(1)
    root1.left = TreeNode(2)
    root1.right = TreeNode(3)

    // Test Case 2
    //          4
    //        /   \
    //       9     0
    //      / \
    //     5   1
    //
    // Numbers:
    // 4 -> 9 -> 5 = 495
    // 4 -> 9 -> 1 = 491
    // 4 -> 0     = 40
    //
    // Expected: 495 + 491 + 40 = 1026
    val root2 = TreeNode(4)
    root2.left = TreeNode(9)
    root2.right = TreeNode(0)
    root2.left?.left = TreeNode(5)
    root2.left?.right = TreeNode(1)

    // Test Case 3
    //      7
    //
    // Number: 7
    // Expected: 7
    val root3 = TreeNode(7)

    // Test Case 4
    //          1
    //         /
    //        0
    //       /
    //      5
    //
    // Number: 105
    // Expected: 105
    val root4 = TreeNode(1)
    root4.left = TreeNode(0)
    root4.left?.left = TreeNode(5)

    // Test Case 5
    //          0
    //        /   \
    //       1     2
    //      /       \
    //     3         4
    //
    // Numbers:
    // 0 -> 1 -> 3 = 13
    // 0 -> 2 -> 4 = 24
    //
    // Expected: 37
    val root5 = TreeNode(0)
    root5.left = TreeNode(1)
    root5.right = TreeNode(2)
    root5.left?.left = TreeNode(3)
    root5.right?.right = TreeNode(4)

    // Test Case 6
    // null tree
    //
    // Expected: 0
    val root6: TreeNode? = null

    val inputs = listOf(root1, root2, root3, root4, root5, root6)
    val expected = listOf(25, 1026, 7, 105, 37, 0)

    println("----------------------------------")
    for (i in inputs.indices) {
        val output = solve(inputs[i])
        println("input => Tree root ${inputs[i]?.value}")
        println("output => $output - ${if (output == expected[i]) "✅" else "❌"}")
        println("----------------------------------")
    }
}