/*
Problem: Flatten Binary Tree to Linked List

Given the root of a binary tree, flatten the tree into a linked list
in-place.

The linked list should use the same TreeNode class, where each node's
right pointer points to the next node in the list and every left pointer
is null.

The nodes in the linked list should appear in the same order as a
preorder traversal of the binary tree.

Do not return anything; modify the tree in-place.
*/

class TreeNode(
    val value: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null
)

private fun solve(root: TreeNode?) {
    if (root == null) return

    val nodeStack = ArrayDeque<TreeNode>()
    nodeStack.addFirst(root)

    while (nodeStack.isNotEmpty()) {
        val node = nodeStack.removeFirst()
        node.right?.let { nodeStack.addFirst(it) }
        node.left?.let { nodeStack.addFirst(it) }
        node.right = nodeStack.firstOrNull()
        node.left = null
    }
}

private fun getFlattenedValues(root: TreeNode?): List<Int> {
    val values = mutableListOf<Int>()
    var current = root

    while (current != null) {
        // A valid flattened tree must not have any left child
        if (current.left != null) {
            return emptyList()
        }

        values.add(current.value)
        current = current.right
    }

    return values
}

fun main() {
    // Test Case 1
    //       1
    //      / \
    //     2   5
    //    / \   \
    //   3   4   6
    //
    // Expected: 1 -> 2 -> 3 -> 4 -> 5 -> 6
    val root1 = TreeNode(1)
    root1.left = TreeNode(2)
    root1.right = TreeNode(5)
    root1.left?.left = TreeNode(3)
    root1.left?.right = TreeNode(4)
    root1.right?.right = TreeNode(6)

    // Test Case 2
    // null
    //
    // Expected: empty list
    val root2: TreeNode? = null

    // Test Case 3
    //   1
    //
    // Expected: 1
    val root3 = TreeNode(1)

    // Test Case 4
    //       1
    //      /
    //     2
    //    /
    //   3
    //  /
    // 4
    //
    // Expected: 1 -> 2 -> 3 -> 4
    val root4 = TreeNode(1)
    root4.left = TreeNode(2)
    root4.left?.left = TreeNode(3)
    root4.left?.left?.left = TreeNode(4)

    // Test Case 5
    //   1
    //    \
    //     2
    //      \
    //       3
    //
    // Expected: 1 -> 2 -> 3
    val root5 = TreeNode(1)
    root5.right = TreeNode(2)
    root5.right?.right = TreeNode(3)

    // Test Case 6
    //       1
    //      / \
    //     2   3
    //        / \
    //       4   5
    //
    // Expected: 1 -> 2 -> 3 -> 4 -> 5
    val root6 = TreeNode(1)
    root6.left = TreeNode(2)
    root6.right = TreeNode(3)
    root6.right?.left = TreeNode(4)
    root6.right?.right = TreeNode(5)

    val inputs = listOf(
        root1,
        root2,
        root3,
        root4,
        root5,
        root6
    )

    val expected = listOf(
        listOf(1, 2, 3, 4, 5, 6),
        emptyList(),
        listOf(1),
        listOf(1, 2, 3, 4),
        listOf(1, 2, 3),
        listOf(1, 2, 3, 4, 5)
    )

    println("----------------------------------")
    for ((index, root) in inputs.withIndex()) {
        solve(root)
        val output = getFlattenedValues(root)

        println("Test Case ${index + 1}")
        println("output => $output - ${if (output == expected[index]) "✅" else "❌"}")
        println("----------------------------------")
    }
}