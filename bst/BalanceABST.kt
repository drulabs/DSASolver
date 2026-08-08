/*
    Balance a Binary Search Tree

    Given the root of a Binary Search Tree (BST),
    return a height-balanced BST containing the same node values.

    A height-balanced binary tree is one in which the depths
    of the left and right subtrees of every node never differ
    by more than 1.

    If there is more than one answer, return any of them.


    Example 1:

    Input:

            1
             \
              2
               \
                3
                 \
                  4

    One valid output:

            2
           / \
          1   3
               \
                4


    Example 2:

    Input:

            2
           / \
          1   3

    Output:

            2
           / \
          1   3


    Constraints:

    - The number of nodes is in the range [1, 10_000].
    - 1 <= Node.value <= 100_000
    - The input tree is a valid BST.
*/


data class TreeNode(
    val value: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null
)


private fun solve(root: TreeNode?): TreeNode? {
    if (root == null) return null
    val nodes = inorderNodes(root)
    return helper(nodes, 0, nodes.lastIndex)
}

private fun helper(nodes: List<TreeNode>, low: Int, high: Int): TreeNode? {
    if (low > high) return null

    val mid = (low + high)/2
    val root = nodes[mid]
    root.left = helper(nodes, low, mid - 1)
    root.right = helper(nodes, mid + 1, high)
    return root
}

private fun inorderNodes(root: TreeNode?): List<TreeNode> {
    val result = mutableListOf<TreeNode>()

    fun traverse(node: TreeNode?) {
        if (node == null) return

        traverse(node.left)
        result.add(node)
        traverse(node.right)
    }

    traverse(root)
    return result
}

private fun inorder(root: TreeNode?): List<Int> {
    val result = mutableListOf<Int>()

    fun traverse(node: TreeNode?) {
        if (node == null) return

        traverse(node.left)
        result.add(node.value)
        traverse(node.right)
    }

    traverse(root)
    return result
}


private fun isBalanced(root: TreeNode?): Boolean {

    fun height(node: TreeNode?): Int {
        if (node == null) return 0

        val left = height(node.left)
        if (left == -1) return -1

        val right = height(node.right)
        if (right == -1) return -1

        if (kotlin.math.abs(left - right) > 1) {
            return -1
        }

        return 1 + maxOf(left, right)
    }

    return height(root) != -1
}


private fun isValidBST(root: TreeNode?): Boolean {

    fun validate(
        node: TreeNode?,
        lower: Long,
        upper: Long
    ): Boolean {

        if (node == null) return true

        if (node.value <= lower || node.value >= upper) {
            return false
        }

        return validate(
            node.left,
            lower,
            node.value.toLong()
        ) && validate(
            node.right,
            node.value.toLong(),
            upper
        )
    }

    return validate(
        root,
        Long.MIN_VALUE,
        Long.MAX_VALUE
    )
}


private data class TestCase(
    val root: TreeNode?,
    val description: String
)


fun main() {
    // Test Case 1
    //
    // 1
    //  \
    //   2
    //    \
    //     3
    //      \
    //       4
    //
    val root1 = TreeNode(1)
    root1.right = TreeNode(2)
    root1.right?.right = TreeNode(3)
    root1.right?.right?.right = TreeNode(4)


    // Test Case 2
    //
    //       4
    //      /
    //     3
    //    /
    //   2
    //  /
    // 1
    //
    val root2 = TreeNode(4)
    root2.left = TreeNode(3)
    root2.left?.left = TreeNode(2)
    root2.left?.left?.left = TreeNode(1)


    // Test Case 3
    //
    //      3
    //     / \
    //    2   4
    //
    val root3 = TreeNode(3)
    root3.left = TreeNode(2)
    root3.right = TreeNode(4)


    // Test Case 4
    //
    //          8
    //        /   \
    //       4     12
    //      / \    / \
    //     2   6 10  14
    //
    val root4 = TreeNode(8)
    root4.left = TreeNode(4)
    root4.right = TreeNode(12)
    root4.left?.left = TreeNode(2)
    root4.left?.right = TreeNode(6)
    root4.right?.left = TreeNode(10)
    root4.right?.right = TreeNode(14)


    // Test Case 5
    //
    //      42
    //
    val root5 = TreeNode(42)

    val inputs = listOf(
        root1,
        root2,
        root3,
        root4,
        root5
    )

    for ((index, input) in inputs.withIndex()) {

        val expected = inorder(input)

        val output = solve(input)

        println("----------------------------------")
        println("Test ${index + 1}")

        println("Expected inorder : $expected")
        println("Actual inorder   : ${inorder(output)}")
        println("Balanced         : ${isBalanced(output)}")
        println("Valid BST        : ${isValidBST(output)}")

        println(
            if (
                expected == inorder(output) &&
                isBalanced(output) &&
                isValidBST(output)
            ) "PASS ✅" else "FAIL ❌"
        )
    }
}