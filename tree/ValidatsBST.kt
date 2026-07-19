class TreeNode(var value: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}

fun main() {
    // Test Case 1: Valid BST
    //    2
    //   / \
    //  1   3
    val root1 = TreeNode(2)
    root1.left = TreeNode(1)
    root1.right = TreeNode(3)

    // Test Case 2: Invalid BST
    //    5
    //   / \
    //  1   4
    //     / \
    //    3   6
    val root2 = TreeNode(5)
    root2.left = TreeNode(1)
    root2.right = TreeNode(4)
    root2.right?.left = TreeNode(3)
    root2.right?.right = TreeNode(6)

    // Test Case 3: Valid BST (10 nodes)
    //          10
    //        /    \
    //       5      15
    //      / \    /  \
    //     3   7  12   20
    //    /   /        /
    //   1   6        18
    val root3 = TreeNode(10)
    root3.left = TreeNode(5)
    root3.right = TreeNode(15)
    root3.left?.left = TreeNode(3)
    root3.left?.right = TreeNode(7)
    root3.left?.left?.left = TreeNode(1)
    root3.left?.right?.left = TreeNode(6)
    root3.right?.left = TreeNode(12)
    root3.right?.right = TreeNode(20)
    root3.right?.right?.left = TreeNode(18)

    // Test Case 4: Invalid BST (10 nodes)
    //          10
    //        /    \
    //       5      15
    //      / \    /  \
    //     3   7  12   20
    //    /   / \
    //   1   6   12  <-- 12 is invalid here (should be < 10 (the root))
    val root4 = TreeNode(10)
    root4.left = TreeNode(5)
    root4.right = TreeNode(15)
    root4.left?.left = TreeNode(3)
    root4.left?.right = TreeNode(7)
    root4.left?.left?.left = TreeNode(1)
    root4.left?.right?.left = TreeNode(6)
    root4.left?.right?.right = TreeNode(12)
    root4.right?.left = TreeNode(12)
    root4.right?.right = TreeNode(20)

    val inputs = listOf(root1, root2, root3, root4)
    val expected = listOf(true, false, true, false)

    println("----------------------------------")
    for (i in inputs.indices) {
        val output = solve(inputs[i])
        println("input => Tree root ${inputs[i].value}")
        println("output => $output - ${if (output == expected[i]) "✅" else "❌"}")
        println("----------------------------------")
    }
}

private fun solve(root: TreeNode?): Boolean {
    return isValidBST(root)
}

private fun isValidBST(
    node: TreeNode?, 
    left: Int = Int.MIN_VALUE, 
    right: Int = Int.MAX_VALUE, 
): Boolean {
    if (node == null) return true
    val num = node.value
    return num <= right && num >= left &&
            isValidBST(node.left, left, num) &&
            isValidBST(node.right, num, right)
}