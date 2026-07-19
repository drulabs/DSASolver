/*
Problem: Search in a Binary Search Tree

You are given the root of a Binary Search Tree (BST) and an integer value.

Return the subtree rooted at the node whose value equals the given value.
If no such node exists, return null.

Example:

Input:
        4
      /   \
     2     7
    / \
   1   3

value = 2

Output:

      2
     / \
    1   3

-----------------------------------------

Input:
        4
      /   \
     2     7
    / \
   1   3

value = 5

Output:
null
*/

class TreeNode(
    val value: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null
)


private fun solve(root: TreeNode?, value: Int): TreeNode? {
    
}

/* ---------- Helpers ---------- */

private fun TreeNode?.preorder(): List<Int> {
    if (this == null) return emptyList()
    return listOf(value) + left.preorder() + right.preorder()
}

private fun sampleTree(): TreeNode =
    TreeNode(
        4,
        TreeNode(
            2,
            TreeNode(1),
            TreeNode(3)
        ),
        TreeNode(7)
    )

fun main() {
    val root = sampleTree()

    println(solve(root, 2)?.preorder()) // [2, 1, 3]
    println(solve(root, 7)?.preorder()) // [7]
    println(solve(root, 4)?.preorder()) // [4, 2, 1, 3, 7]
    println(solve(root, 1)?.preorder()) // [1]
    println(solve(root, 5)?.preorder()) // null
    println(solve(null, 1)?.preorder()) // null
}