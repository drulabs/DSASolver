class TreeNode(
    val value: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null
)

private fun solve(root: TreeNode?): Int {
    if (root == null) return 0
    return 1 + maxOf(solve(root.left), solve(root.right))
}

fun main() {
    // Test Case 1
    //      3
    //     / \
    //    9   20
    //       /  \
    //      15   7
    val root1 = TreeNode(3)
    root1.left = TreeNode(9)
    root1.right = TreeNode(20)
    root1.right?.left = TreeNode(15)
    root1.right?.right = TreeNode(7)

    // Test Case 2
    //      1
    //       \
    //        2
    //         \
    //          3
    val root2 = TreeNode(1)
    root2.right = TreeNode(2)
    root2.right?.right = TreeNode(3)

    // Test Case 3
    // null tree
    val root3: TreeNode? = null

    // Test Case 4
    //      1
    val root4 = TreeNode(1)

    val inputs = listOf(root1, root2, root3, root4)
    val expected = listOf(3, 3, 0, 1)

    println("----------------------------------")
    for (i in inputs.indices) {
        val output = solve(inputs[i])
        println("input => Tree root ${inputs[i]?.value}")
        println("output => $output - ${if (output == expected[i]) "✅" else "❌"}")
        println("----------------------------------")
    }
}