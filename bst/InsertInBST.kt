/*
Problem: Insert into a Binary Search Tree

Given the root of a Binary Search Tree (BST) and an integer value,
insert the value into the BST.

Return the root of the updated BST.

Assume the BST contains unique values and the value to be inserted
does not already exist.
*/

class TreeNode(
    val value: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null
)

private fun solve(root: TreeNode?, value: Int): TreeNode? {
    val newNode = TreeNode(value)
    if (root == null) return newNode

    var current: TreeNode? = root

    while (current != null) {
        val nodeValue = current.value
        if (value < nodeValue) {
            if (current.left == null) {
                current.left = newNode
                return root
            } else {
                current = current.left
            }
        } else {
            if (current.right == null) {
                current.right = newNode
                return root
            } else {
                current = current.right
            }
        }
    }

    return root
}

private fun inorder(root: TreeNode?): List<Int> {
    if (root == null) return emptyList()
    return inorder(root.left) + root.value + inorder(root.right)
}

fun main() {

    // Test Case 1
    // Insert: 5
    //
    //        4
    //      /   \
    //     2     7
    //    / \
    //   1   3
    //
    // Expected inorder:
    // [1, 2, 3, 4, 5, 7]
    val root1 = TreeNode(4)
    root1.left = TreeNode(2)
    root1.right = TreeNode(7)
    root1.left?.left = TreeNode(1)
    root1.left?.right = TreeNode(3)

    // Test Case 2
    // Insert: 4
    //
    // null
    //
    // Expected inorder:
    // [4]
    val root2: TreeNode? = null

    // Test Case 3
    // Insert: 8
    //
    //      5
    //
    // Expected inorder:
    // [5, 8]
    val root3 = TreeNode(5)

    // Test Case 4
    // Insert: 2
    //
    //      5
    //
    // Expected inorder:
    // [2, 5]
    val root4 = TreeNode(5)

    // Test Case 5
    // Insert: 6
    //
    //        4
    //      /   \
    //     2     7
    //    / \   /
    //   1   3 5
    //
    // Expected inorder:
    // [1, 2, 3, 4, 5, 6, 7]
    val root5 = TreeNode(4)
    root5.left = TreeNode(2)
    root5.right = TreeNode(7)
    root5.left?.left = TreeNode(1)
    root5.left?.right = TreeNode(3)
    root5.right?.left = TreeNode(5)

    val inputs = listOf(root1, root2, root3, root4, root5)

    val values = listOf(
        5,
        4,
        8,
        2,
        6
    )

    val expected = listOf(
        listOf(1, 2, 3, 4, 5, 7),
        listOf(4),
        listOf(5, 8),
        listOf(2, 5),
        listOf(1, 2, 3, 4, 5, 6, 7)
    )

    println("----------------------------------")
    for (i in inputs.indices) {
        val output = inorder(solve(inputs[i], values[i]))
        val answer = expected[i]

        println("input => insert=${values[i]}")
        println("output => $output - ${if (output == answer) "✅" else "❌"}")
        println("----------------------------------")
    }
}