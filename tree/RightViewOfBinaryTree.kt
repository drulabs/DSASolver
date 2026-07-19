/*
Problem: Binary Tree Right Side View

Given the root of a binary tree, imagine yourself standing on the
right side of it.

Return the values of the nodes you can see ordered from top to bottom.

Example:

        1
      /   \
     2     3
      \ 
       5

Right side view:
[1, 3, 5]
*/

class TreeNode(
    val value: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null
)

private fun solve(root: TreeNode?): List<Int> {
    if (root == null) return listOf()
    val result = mutableListOf<Int>()
    val q = ArrayDeque<TreeNode>()
    q.addLast(root)

    while (q.isNotEmpty()) {
        val size = q.size

        repeat(size) { i ->
            val node = q.removeFirst()

            if (i == size -1) result.add(node.value)

            node.left?.let { q.addLast(it) }
            node.right?.let { q.addLast(it) }
        }
    }

    return result
}

fun main() {
    // Test Case 1
    //         1
    //       /   \
    //      2     3
    //       \     \
    //        5     4
    //
    // Expected: [1, 3, 4]
    val root1 = TreeNode(1)
    root1.left = TreeNode(2)
    root1.right = TreeNode(3)
    root1.left?.right = TreeNode(5)
    root1.right?.right = TreeNode(4)

    // Test Case 2
    //         1
    //       /
    //      2
    //     /
    //    3
    //
    // Expected: [1, 2, 3]
    val root2 = TreeNode(1)
    root2.left = TreeNode(2)
    root2.left?.left = TreeNode(3)

    // Test Case 3
    //         1
    //          \
    //           2
    //            \
    //             3
    //
    // Expected: [1, 2, 3]
    val root3 = TreeNode(1)
    root3.right = TreeNode(2)
    root3.right?.right = TreeNode(3)

    // Test Case 4
    //         1
    //       /   \
    //      2     3
    //     /     /
    //    4     5
    //
    // Expected: [1, 3, 5]
    val root4 = TreeNode(1)
    root4.left = TreeNode(2)
    root4.right = TreeNode(3)
    root4.left?.left = TreeNode(4)
    root4.right?.left = TreeNode(5)

    // Test Case 5
    //      7
    //
    // Expected: [7]
    val root5 = TreeNode(7)

    // Test Case 6
    // null tree
    //
    // Expected: []
    val root6: TreeNode? = null

    // Test Case 7
    //          1
    //        /   \
    //       2     3
    //      /
    //     4
    //    /
    //   5
    //
    // Expected: [1, 3, 4, 5]
    val root7 = TreeNode(1)
    root7.left = TreeNode(2)
    root7.right = TreeNode(3)
    root7.left?.left = TreeNode(4)
    root7.left?.left?.left = TreeNode(5)

    val inputs = listOf(
        root1,
        root2,
        root3,
        root4,
        root5,
        root6,
        root7
    )

    val expected = listOf(
        listOf(1, 3, 4),
        listOf(1, 2, 3),
        listOf(1, 2, 3),
        listOf(1, 3, 5),
        listOf(7),
        emptyList(),
        listOf(1, 3, 4, 5)
    )

    println("----------------------------------")
    for (i in inputs.indices) {
        val output = solve(inputs[i])
        println("input => Tree root ${inputs[i]?.value}")
        println("output => $output - ${if (output == expected[i]) "✅" else "❌"}")
        println("----------------------------------")
    }
}