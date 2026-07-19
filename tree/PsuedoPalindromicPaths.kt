/*
Problem: Pseudo-Palindromic Paths in a Binary Tree

Given the root of a binary tree where every node value is a digit
from 1 to 9, return the number of pseudo-palindromic root-to-leaf paths.

A root-to-leaf path is pseudo-palindromic if the values along the path
can be rearranged to form a palindrome.

A sequence can form a palindrome if at most one value occurs an odd
number of times.

Return the total number of pseudo-palindromic paths.
*/

class TreeNode(
    val value: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null
) {
    val isLeaf
        get() = (left == null && right == null)
}

// a better approach is bitwise operation instead of set
private fun solve(root: TreeNode?, countSet: Set<Int> = setOf()): Int {
    if (root == null) 
        return 0
    
    val newSet = if (countSet.contains(root.value))
            countSet - root.value
        else 
            countSet + root.value
    
    return if (root.isLeaf) {        
        if (newSet.size <= 1) 1 else 0
    } else solve(root.left, newSet) + solve(root.right, newSet)
}

fun main() {

    // Test Case 1
    //
    //          2
    //        /   \
    //       3     1
    //      / \     \
    //     3   1     1
    //
    // Paths:
    // 2->3->3  ✅
    // 2->3->1  ❌
    // 2->1->1  ✅
    //
    // Expected: 2
    val root1 = TreeNode(2)
    root1.left = TreeNode(3)
    root1.right = TreeNode(1)
    root1.left?.left = TreeNode(3)
    root1.left?.right = TreeNode(1)
    root1.right?.right = TreeNode(1)

    // Test Case 2
    //
    //          2
    //        /   \
    //       1     1
    //      /
    //     1
    //
    // Path:
    // 2->1->1 ✅
    //
    // Expected: 1
    val root2 = TreeNode(2)
    root2.left = TreeNode(1)
    root2.right = TreeNode(1)
    root2.left?.left = TreeNode(1)

    // Test Case 3
    //
    //      9
    //
    // Expected: 1
    val root3 = TreeNode(9)

    // Test Case 4
    //
    //         2
    //        /
    //       3
    //      /
    //     1
    //
    // Path:
    // 2->3->1 ❌
    //
    // Expected: 0
    val root4 = TreeNode(2)
    root4.left = TreeNode(3)
    root4.left?.left = TreeNode(1)

    // Test Case 5
    //
    // null tree
    //
    // Expected: 0
    val root5: TreeNode? = null

    // Test Case 6
    //
    //                 2
    //              /     \
    //             3       1
    //           /   \    /  \
    //          3     1  1    2
    //         / \     \      / \
    //        2   3     3    2   1
    //
    // Paths:
    // 2-3-3-2 ✅
    // 2-3-3-3 ❌
    // 2-3-1-3 ❌
    // 2-1-1 ✅
    // 2-1-2-2 ❌
    // 2-1-2-1 ✅
    //
    // Expected: 3
    val root6 = TreeNode(2)
    root6.left = TreeNode(3)
    root6.right = TreeNode(1)
    root6.left?.left = TreeNode(3)
    root6.left?.right = TreeNode(1)
    root6.right?.left = TreeNode(1)
    root6.right?.right = TreeNode(2)
    root6.left?.left?.left = TreeNode(2)
    root6.left?.left?.right = TreeNode(3)
    root6.left?.right?.right = TreeNode(3)
    root6.right?.right?.left = TreeNode(2)
    root6.right?.right?.right = TreeNode(1)

    val inputs = listOf(root1, root2, root3, root4, root5, root6)
    val expected = listOf(2, 1, 1, 0, 0, 3)

    println("----------------------------------")
    for (i in inputs.indices) {
        val output = solve(inputs[i])
        println("input => Tree root ${inputs[i]?.value}")
        println("output => $output - ${if (output == expected[i]) "✅" else "❌"}")
        println("----------------------------------")
    }
}