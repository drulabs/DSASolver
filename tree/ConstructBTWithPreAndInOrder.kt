/*
Problem: Construct Binary Tree from Preorder and Inorder Traversal

Given two integer arrays preorder and inorder where:

- preorder is the preorder traversal of a binary tree.
- inorder is the inorder traversal of the same tree.

Construct and return the binary tree.

You may assume:
- preorder and inorder contain unique values.
- The tree is valid.
*/

class TreeNode(
    val value: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null
)

// private fun solve(preorder: IntArray, inorder: IntArray): TreeNode? {
//     if (inorder.isEmpty()) return null

//     val rootValue = preorder.first()
//     val root = TreeNode(rootValue)

//     val rootIndex = inorder.indexOf(rootValue)
//     val leftInorder = inorder.sliceArray(0..(rootIndex - 1))
//     val rightInorder = inorder.sliceArray((rootIndex + 1)..preorder.lastIndex)
//     val leftPreorder = preorder.filter { it in leftInorder }.toIntArray()
//     val rightPreorder = preorder.filter { it in rightInorder }.toIntArray()
    
//     root.left = solve(leftPreorder, leftInorder)
//     root.right = solve (rightPreorder, rightInorder)

//     return root
// }
private fun solve(
    preorder: IntArray,
    inorder: IntArray
): TreeNode? {
    if (preorder.isEmpty()) return null

    val inorderIndex = inorder
        .withIndex()
        .associate { it.value to it.index }

    fun build(
        preorderLeft: Int,
        preorderRight: Int,
        inorderLeft: Int,
        inorderRight: Int
    ): TreeNode? {
        if (preorderLeft > preorderRight || inorderLeft > inorderRight) {
            return null
        }

        val rootValue = preorder[preorderLeft]
        val rootIndex = inorderIndex[rootValue] ?: error("Invalid input")

        val leftSize = rootIndex - inorderLeft

        return TreeNode(rootValue).apply {
            left = build(
                preorderLeft + 1,
                preorderLeft + leftSize,
                inorderLeft,
                rootIndex - 1
            )

            right = build(
                preorderLeft + leftSize + 1,
                preorderRight,
                rootIndex + 1,
                inorderRight
            )
        }
    }

    return build(
        preorderLeft = 0,
        preorderRight = preorder.lastIndex,
        inorderLeft = 0,
        inorderRight = inorder.lastIndex
    )
}

private fun preorder(root: TreeNode?): List<Int> {
    if (root == null) return emptyList()
    return listOf(root.value) + preorder(root.left) + preorder(root.right)
}

private fun inorder(root: TreeNode?): List<Int> {
    if (root == null) return emptyList()
    return inorder(root.left) + root.value + inorder(root.right)
}

fun main() {
    // Test Case 1
    //
    //         3
    //       /   \
    //      9     20
    //           /  \
    //          15   7
    //
    // preorder = [3, 9, 20, 15, 7]
    // inorder  = [9, 3, 15, 20, 7]
    val preorder1 = intArrayOf(3, 9, 20, 15, 7)
    val inorder1 = intArrayOf(9, 3, 15, 20, 7)

    // Test Case 2
    //
    //      1
    //
    // preorder = [1]
    // inorder  = [1]
    val preorder2 = intArrayOf(1)
    val inorder2 = intArrayOf(1)

    // Test Case 3
    //
    //      1
    //       \
    //        2
    //         \
    //          3
    //
    // preorder = [1, 2, 3]
    // inorder  = [1, 2, 3]
    val preorder3 = intArrayOf(1, 2, 3)
    val inorder3 = intArrayOf(1, 2, 3)

    // Test Case 4
    //
    //          1
    //         /
    //        2
    //       /
    //      3
    //
    // preorder = [1, 2, 3]
    // inorder  = [3, 2, 1]
    val preorder4 = intArrayOf(1, 2, 3)
    val inorder4 = intArrayOf(3, 2, 1)

    // Test Case 5
    //
    //          8
    //        /   \
    //       4     10
    //      / \      \
    //     2   6      20
    //
    // preorder = [8, 4, 2, 6, 10, 20]
    // inorder  = [2, 4, 6, 8, 10, 20]
    val preorder5 = intArrayOf(8, 4, 2, 6, 10, 20)
    val inorder5 = intArrayOf(2, 4, 6, 8, 10, 20)

    val inputs = listOf(
        preorder1 to inorder1,
        preorder2 to inorder2,
        preorder3 to inorder3,
        preorder4 to inorder4,
        preorder5 to inorder5
    )

    println("----------------------------------")
    for ((pre, ino) in inputs) {
        val root = solve(pre, ino)

        val preorderOutput = preorder(root)
        val inorderOutput = inorder(root)

        val ok = preorderOutput == pre.toList() &&
                 inorderOutput == ino.toList()

        println("preorder = ${pre.toList()}")
        println("inorder  = ${ino.toList()}")
        println("output => ${if (ok) "✅" else "❌"}")
        println("----------------------------------")
    }
}