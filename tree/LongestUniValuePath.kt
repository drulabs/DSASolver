/*
Problem: Longest Univalue Path

Given the root of a binary tree, return the length of the longest path
where every node in the path has the same value.

The path:
- May start and end at any node.
- Must follow parent-child connections.
- Does not have to pass through the root.
- Is measured in EDGES, not nodes.
*/

class TreeNode(
    val value: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null
)

private fun solve(root: TreeNode?): Int {
    return helper(root).second
}

private fun helper(node: TreeNode?): Pair<Int, Int> {
    if (node == null) return Pair(0, 0)

    val left = helper(node.left)
    val right = helper(node.right)

    val leftGain =
        if (node.left?.value == node.value) left.first + 1 else 0

    val rightGain =
        if (node.right?.value == node.value) right.first + 1 else 0

    val gain = maxOf(leftGain, rightGain)

    val max = maxOf(
        left.second,
        right.second,
        leftGain + rightGain
    )

    return (gain to max)
}

fun main() {
    // Test Case 1
    //         5
    //        / \
    //       4   5
    //      / \   \
    //     1   1   5
    //
    // Longest path: 5 -> 5 -> 5
    // Expected: 2
    val root1 = TreeNode(5)
    root1.left = TreeNode(4)
    root1.right = TreeNode(5)
    root1.left?.left = TreeNode(1)
    root1.left?.right = TreeNode(1)
    root1.right?.right = TreeNode(5)

    // Test Case 2
    //         1
    //        / \
    //       4   5
    //      / \   \
    //     4   4   5
    //
    // Longest path: 4 -> 4 -> 4
    // Expected: 2
    val root2 = TreeNode(1)
    root2.left = TreeNode(4)
    root2.right = TreeNode(5)
    root2.left?.left = TreeNode(4)
    root2.left?.right = TreeNode(4)
    root2.right?.right = TreeNode(5)

    // Test Case 3
    //      1
    //
    // Expected: 0
    val root3 = TreeNode(1)

    // Test Case 4
    //        1
    //       / \
    //      1   1
    //     / \
    //    1   1
    //   /
    //  2
    //
    // Longest path:
    // left-left -> left -> root -> right
    // Expected: 3
    val root4 = TreeNode(1)
    root4.left = TreeNode(1)
    root4.right = TreeNode(1)
    root4.left?.left = TreeNode(1)
    root4.left?.right = TreeNode(1)
    root4.left?.left?.left = TreeNode(2)

    // Test Case 5
    //         7
    //        / \
    //       7   7
    //      /     \
    //     7       7
    //    /         \
    //   7           7
    //
    // Longest path:
    // leftmost 7 -> ... -> root -> ... -> rightmost 7
    // Expected: 6
    val root5 = TreeNode(7)
    root5.left = TreeNode(7)
    root5.right = TreeNode(7)
    root5.left?.left = TreeNode(7)
    root5.left?.left?.left = TreeNode(7)
    root5.right?.right = TreeNode(7)
    root5.right?.right?.right = TreeNode(7)

    // Test Case 6
    // null tree
    //
    // Expected: 0
    val root6: TreeNode? = null

    val inputs = listOf(root1, root2, root3, root4, root5, root6)
    val expected = listOf(2, 2, 0, 3, 6, 0)

    println("----------------------------------")
    for (i in inputs.indices) {
        val output = solve(inputs[i])
        println("input => Tree root ${inputs[i]?.value}")
        println("output => $output - ${if (output == expected[i]) "✅" else "❌"}")
        println("----------------------------------")
    }
}