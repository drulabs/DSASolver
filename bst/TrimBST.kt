/*
Problem: Trim a Binary Search Tree

Given the root of a Binary Search Tree and two integers low and high,
trim the tree so that all its elements lies in the inclusive range
[low, high].

Trimming the tree should not change the relative structure of the
remaining nodes.

Return the root of the trimmed Binary Search Tree.
*/

class TreeNode(
    val value: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null
)

private fun solve(root: TreeNode?, low: Int, high: Int): TreeNode? {
    if (root == null) return null

    if (root.value < low) {
        return solve(root.right, low, high)
    }

    if (root.value > high) {
        return solve(root.left, low, high)
    }

    root.left = solve(root.left, low, high)
    root.right = solve(root.right, low, high)

    return root
}

private fun levelOrder(root: TreeNode?): List<Int?> {
    if (root == null) return emptyList()

    val result = mutableListOf<Int?>()
    val queue = ArrayDeque<TreeNode?>()
    queue.add(root)

    while (queue.isNotEmpty()) {
        val node = queue.removeFirst()

        if (node == null) {
            result.add(null)
        } else {
            result.add(node.value)
            queue.add(node.left)
            queue.add(node.right)
        }
    }

    while (result.isNotEmpty() && result.last() == null) {
        result.removeAt(result.lastIndex)
    }

    return result
}

fun main() {

    // Test Case 1
    //
    //          1
    //         / \
    //        0   2
    //
    // low = 1, high = 2
    // Expected = [1, null, 2]
    val root1 = TreeNode(1)
    root1.left = TreeNode(0)
    root1.right = TreeNode(2)

    // Test Case 2
    //
    //             3
    //           /   \
    //          0     4
    //           \
    //            2
    //           /
    //          1
    //
    // low = 1, high = 3
    // Expected = [3,2,null,1]
    val root2 = TreeNode(3)
    root2.left = TreeNode(0)
    root2.right = TreeNode(4)
    root2.left?.right = TreeNode(2)
    root2.left?.right?.left = TreeNode(1)

    // Test Case 3
    //
    //      5
    //
    // low = 6, high = 10
    // Expected = []
    val root3 = TreeNode(5)

    // Test Case 4
    //
    // null
    //
    // Expected = []
    val root4: TreeNode? = null

    // Test Case 5
    //
    //          8
    //        /   \
    //       4     12
    //      / \    / \
    //     2   6 10 14
    //
    // low = 5, high = 12
    // Expected = [8,6,12,10]
    val root5 = TreeNode(8)
    root5.left = TreeNode(4)
    root5.right = TreeNode(12)
    root5.left?.left = TreeNode(2)
    root5.left?.right = TreeNode(6)
    root5.right?.left = TreeNode(10)
    root5.right?.right = TreeNode(14)

    // Test Case 6 (invalid BST ⚠️)
    //
    //          5
    //         /
    //        3
    //         \
    //          4
    //           \
    //            8
    //
    // low = 6, high = 10
    // Expected = [8]
    val root6 = TreeNode(5)
    root6.left = TreeNode(3)
    root6.left?.right = TreeNode(4)
    root6.left?.right?.right = TreeNode(8)

    // Test Case 7
    //
    //      2
    //       \
    //        3
    //         \
    //          4
    //           \
    //            8
    //
    // low = 8, high = 10
    // Expected = [8]
    val root7 = TreeNode(2)
    root7.right = TreeNode(3)
    root7.right?.right = TreeNode(4)
    root7.right?.right?.right = TreeNode(8)

    // Test Case 8
    //
    //          8
    //         /
    //        4
    //         \
    //          6
    //         /
    //        5
    //
    // low = 5, high = 8
    // Expected = [8,6,null,5]
    val root8 = TreeNode(8)
    root8.left = TreeNode(4)
    root8.left?.right = TreeNode(6)
    root8.left?.right?.left = TreeNode(5)

    // Test Case 9
    //
    //          10
    //         /
    //        5
    //       /
    //      2
    //       \
    //        3
    //
    // low = 3, high = 10
    // Expected = [10,5,null,3]
    val root9 = TreeNode(10)
    root9.left = TreeNode(5)
    root9.left?.left = TreeNode(2)
    root9.left?.left?.right = TreeNode(3)

    // Test Case 10
    //
    //          5
    //         / \
    //        2   8
    //       /   /
    //      1   7
    //
    // low = 6, high = 8
    // Expected = [8,7]
    val root10 = TreeNode(5)
    root10.left = TreeNode(2)
    root10.right = TreeNode(8)
    root10.left?.left = TreeNode(1)
    root10.right?.left = TreeNode(7)

    val inputs = listOf(
        root1, root2, root3, root4, root5,
        root7, root8, root9, root10
    )

    val ranges = listOf(
        1 to 2,
        1 to 3,
        6 to 10,
        1 to 10,
        5 to 12,
        8 to 10,
        5 to 8,
        3 to 10,
        6 to 8
    )

    val expected = listOf(
        listOf(1, null, 2),
        listOf(3, 2, null, 1),
        emptyList(),
        emptyList(),
        listOf(8, 6, 12, null, null, 10),
        listOf(8),
        listOf(8, 6, null, 5),
        listOf(10, 5, null, 3),
        listOf(8, 7)
    )

    println("----------------------------------")
    for (i in inputs.indices) {
        val (low, high) = ranges[i]
        val output = levelOrder(solve(inputs[i], low, high))
        val answer = expected[i]

        println("range = [$low, $high]")
        println("output => $output ${if (output == answer) "✅" else "❌"}")
        println("----------------------------------")
    }
}