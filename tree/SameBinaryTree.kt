/*
Problem: Same Tree

Given the roots of two binary trees p and q, write a function to
check if they are the same.

Two binary trees are considered the same if they are structurally
identical and the nodes have the same values.

Return true if the trees are the same, otherwise false.
*/

class TreeNode(
    val value: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null
)

private fun solve(p: TreeNode?, q: TreeNode?): Boolean {
    if (p == null && q == null) return true
    if (p == null || q == null) return false
    if (p.value != q.value) return false

    return solve(p.left, q.left) && solve(p.right, q.right)
}

fun main() {
    // Test Case 1
    //      1           1
    //     / \         / \
    //    2   3       2   3
    //
    // Expected: true
    val p1 = TreeNode(1)
    p1.left = TreeNode(2)
    p1.right = TreeNode(3)

    val q1 = TreeNode(1)
    q1.left = TreeNode(2)
    q1.right = TreeNode(3)

    // Test Case 2
    //      1           1
    //     /             \
    //    2               2
    //
    // Expected: false
    val p2 = TreeNode(1)
    p2.left = TreeNode(2)

    val q2 = TreeNode(1)
    q2.right = TreeNode(2)

    // Test Case 3
    //      1           1
    //     / \         / \
    //    2   1       1   2
    //
    // Expected: false
    val p3 = TreeNode(1)
    p3.left = TreeNode(2)
    p3.right = TreeNode(1)

    val q3 = TreeNode(1)
    q3.left = TreeNode(1)
    q3.right = TreeNode(2)

    // Test Case 4
    //      5           5
    //     /           /
    //    4           4
    //   /
    //  3           3
    //
    // Expected: true
    val p4 = TreeNode(5)
    p4.left = TreeNode(4)
    p4.left?.left = TreeNode(3)

    val q4 = TreeNode(5)
    q4.left = TreeNode(4)
    q4.left?.left = TreeNode(3)

    // Test Case 5
    // null       null
    //
    // Expected: true
    val p5: TreeNode? = null
    val q5: TreeNode? = null

    // Test Case 6
    //      1       null
    //
    // Expected: false
    val p6 = TreeNode(1)
    val q6: TreeNode? = null

    val inputs = listOf(
        p1 to q1,
        p2 to q2,
        p3 to q3,
        p4 to q4,
        p5 to q5,
        p6 to q6
    )

    val expected = listOf(
        true,
        false,
        false,
        true,
        true,
        false
    )

    println("----------------------------------")
    for ((index, input) in inputs.withIndex()) {
        val (p, q) = input
        val output = solve(p, q)

        println("Test Case ${index + 1}")
        println("output => $output - ${if (output == expected[index]) "✅" else "❌"}")
        println("----------------------------------")
    }
}