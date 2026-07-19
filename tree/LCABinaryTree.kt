/*
Problem: Lowest Common Ancestor of a Binary Tree

Given the root of a binary tree and two nodes p and q,
return their Lowest Common Ancestor (LCA).

The Lowest Common Ancestor is defined as the lowest node in the tree
that has both p and q as descendants (where a node can be a descendant of itself).

It is guaranteed that both p and q exist in the tree.
*/

class TreeNode(
    val value: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null
)

private fun solve(
    root: TreeNode?, 
    p: TreeNode, 
    q: TreeNode
): TreeNode? {
    if (root == null) return null
    if (root == p || root == q) return root

    val left = solve(root.left, p, q)
    val right = solve(root.right, p, q)

    return if (left != null && right != null)
        root
    else 
        left ?: right
}


fun main() {
    // Test Case 1
    //          3
    //        /   \
    //       5     1
    //      / \   / \
    //     6   2 0   8
    //        / \
    //       7   4
    //
    // p = 5, q = 1
    // Expected LCA = 3
    val root1 = TreeNode(3)
    root1.left = TreeNode(5)
    root1.right = TreeNode(1)
    val node6 = TreeNode(6)
    root1.left?.left = node6
    root1.left?.right = TreeNode(2)
    root1.left?.right?.left = TreeNode(7)
    val node4 = TreeNode(4)
    root1.left?.right?.right = node4
    root1.right?.left = TreeNode(0)
    root1.right?.right = TreeNode(8)

    // Test Case 2
    // Same tree
    // p = 5, q = 4
    // Expected LCA = 5

    // Test Case 3
    //       1
    //      /
    //     2
    //
    // p = 1, q = 2
    // Expected LCA = 1
    val root3 = TreeNode(1)
    root3.left = TreeNode(2)

    // Test Case 4
    //       1
    //
    // p = 1, q = 1
    // Expected LCA = 1
    val root4 = TreeNode(1)

    val inputs = listOf(
        Triple(root1, root1.left!!, root1.right!!),
        Triple(root1, node6, node4),
        Triple(root1, root1.left!!, root1.left!!.right!!.right!!),
        Triple(root3, root3, root3.left!!),
        Triple(root4, root4, root4)
    )

    val expected = listOf(3, 5, 5, 1, 1)

    println("----------------------------------")
    for (i in inputs.indices) {
        val (root, p, q) = inputs[i]
        val output = solve(root, p, q)
        println("input => root=${root?.value}, p=${p.value}, q=${q.value}")
        println("output => ${output?.value} - ${if (output?.value == expected[i]) "✅" else "❌"}")
        println("----------------------------------")
    }
}