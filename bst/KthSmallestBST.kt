/*
Problem:
Given the root of a binary search tree, and an integer k,
return the kth smallest value (1-indexed) of all the values
of the nodes in the tree.

1 <= k <= number of nodes

Example 1:

        3
       / \
      1   4
       \
        2

k = 1
Output = 1

Example 2:

            5
           / \
          3   6
         / \
        2   4
       /
      1

k = 3
Output = 3
*/

data class TreeNode(
    val value: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null
)

// Iterative
// private fun solve(root: TreeNode?, k: Int): Int {
//     if (root == null) return -1

//     val stack = ArrayDeque<TreeNode>()
//     var current: TreeNode? = root
//     var counter = k

//     while (current != null || stack.isNotEmpty()) {
//         while (current != null) {
//             stack.addLast(current)
//             current = current.left
//         }

//         val node = stack.removeLast()
//         if (--counter == 0) return node.value
//         current = node.right
//     }

//     return -1
// }

// Recursive
private fun solve(root: TreeNode?, k: Int): Int {
    val counter = intArrayOf(k)
    return findKth(root, counter) ?: -1
}

private fun findKth(node: TreeNode?, counter: IntArray): Int? {
    if (node == null) return null

    val left = findKth(node.left, counter)
    if (left != null) return left

    counter[0]--
    if (counter[0] == 0) return node.value

    return findKth(node.right, counter)
}

fun main() {

    // Test Case 1
    //
    //      3
    //     / \
    //    1   4
    //     \
    //      2
    //
    // k = 1
    // Expected = 1
    val root1 = TreeNode(3)
    root1.left = TreeNode(1)
    root1.right = TreeNode(4)
    root1.left?.right = TreeNode(2)

    // Test Case 2
    //
    //          5
    //         / \
    //        3   6
    //       / \
    //      2   4
    //     /
    //    1
    //
    // k = 3
    // Expected = 3
    val root2 = TreeNode(5)
    root2.left = TreeNode(3)
    root2.right = TreeNode(6)
    root2.left?.left = TreeNode(2)
    root2.left?.right = TreeNode(4)
    root2.left?.left?.left = TreeNode(1)

    // Test Case 3
    //
    //      2
    //     / \
    //    1   3
    //
    // k = 2
    // Expected = 2
    val root3 = TreeNode(2)
    root3.left = TreeNode(1)
    root3.right = TreeNode(3)

    // Test Case 4
    //
    //      1
    //
    // k = 1
    // Expected = 1
    val root4 = TreeNode(1)

    // Test Case 5
    //
    //          8
    //        /   \
    //       3     10
    //      / \      \
    //     1   6      14
    //        / \     /
    //       4   7   13
    //
    // k = 6
    // Expected = 8
    val root5 = TreeNode(8)
    root5.left = TreeNode(3)
    root5.right = TreeNode(10)
    root5.left?.left = TreeNode(1)
    root5.left?.right = TreeNode(6)
    root5.left?.right?.left = TreeNode(4)
    root5.left?.right?.right = TreeNode(7)
    root5.right?.right = TreeNode(14)
    root5.right?.right?.left = TreeNode(13)

    val inputs = listOf(root1, root2, root3, root4, root5)
    val ks = listOf(1, 3, 2, 1, 6)

    val expected = listOf(
        1,
        3,
        2,
        1,
        8
    )

    for (i in inputs.indices) {
        println("----------------------------------")
        println("k = ${ks[i]}")
        val output = solve(inputs[i], ks[i])
        println("output => $output")
        println("expected => ${expected[i]}")
        println(
            "output => $output ${
                if (output == expected[i]) "✅" else "❌"
            }"
        )
    }
}