/**
 * 653. Two Sum IV - Input is a BST
 *
 * Given the root of a Binary Search Tree and an integer k,
 * return true if there exist two distinct nodes in the BST
 * whose values sum to k. Otherwise, return false.
 *
 * Constraints:
 * - The number of nodes in the tree is in the range [1, 10^4].
 * - -10^4 <= Node.val <= 10^4
 * - root is guaranteed to be a valid BST.
 * - -10^5 <= k <= 10^5
 */

class TreeNode(var value: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}

private fun solve(root: TreeNode?, k: Int): Boolean {
    if (root == null) return false

    val ls: ArrayDeque<TreeNode> = ArrayDeque()
    val rs: ArrayDeque<TreeNode> = ArrayDeque()

    var curr: TreeNode? = root
    while (curr != null) {
        ls.addLast(curr)
        curr = curr.left
    }

    curr = root
    while (curr != null) {
        rs.addLast(curr)
        curr = curr.right
    }

    
    while (ls.isNotEmpty() && rs.isNotEmpty()) {
        val leftNode = ls.last()
        val rightNode = rs.last()

        if (leftNode == rightNode) break

        val sum = leftNode.value + rightNode.value
        when {
            sum == k -> return true
            sum < k -> {
                var node = ls.removeLast().right
                while (node != null) {
                    ls.addLast(node)
                    node = node.left
                }
            }
            else -> {
                var node = rs.removeLast().left
                while (node != null) {
                    rs.addLast(node)
                    node = node.right
                }
            }
        }
    }

    return false
}

// private helper(left: ArrayDeque<TreeNode>, right: ArrayDeque<TreeNode>, k: Int): Boolean {
//     if (left.isEmpty() && right.isEMpty()) return false

//     val leftVal = left.removeLastOrNull() ?: right.removeFirstOrNull()
//     val rightVal = right.removeLastOrNull() ?: left.removeFirstOrNull()

//     if (leftVal == null || rightVal == null) return false
    
//     val sum = leftVal.value + rightVal.value

//     when {
//         sum > k -> left.addLast(leftVal)
//         sum < k -> right.addLast(rightVal)
//         else -> return true
//     }
//     return helper(left, right, k)
// }

// --------------------------------------------------------------------
// Helpers
// --------------------------------------------------------------------

private fun inorder(root: TreeNode?): List<Int> {
    val result = mutableListOf<Int>()

    fun traverse(node: TreeNode?) {
        if (node == null) return

        traverse(node.left)
        result += node.value
        traverse(node.right)
    }

    traverse(root)
    return result
}

private fun isValidBST(root: TreeNode?): Boolean {
    fun validate(node: TreeNode?, low: Long, high: Long): Boolean {
        if (node == null) return true

        if (node.value <= low || node.value >= high) return false

        return validate(node.left, low, node.value.toLong()) &&
                validate(node.right, node.value.toLong(), high)
    }

    return validate(root, Long.MIN_VALUE, Long.MAX_VALUE)
}

// --------------------------------------------------------------------
// Tests
// --------------------------------------------------------------------

fun main() {

    data class TestCase(
        val root: TreeNode?,
        val target: Int,
        val expected: Boolean
    )

    val tests = mutableListOf<TestCase>()

    // ------------------------------------------------------------
    // Test 1
    //
    //          5
    //        /   \
    //       3     6
    //      / \     \
    //     2   4     7
    //
    // target = 9
    // expected = true
    // ------------------------------------------------------------
    run {
        val root = TreeNode(5)
        root.left = TreeNode(3)
        root.right = TreeNode(6)
        root.left!!.left = TreeNode(2)
        root.left!!.right = TreeNode(4)
        root.right!!.right = TreeNode(7)

        tests += TestCase(root, 9, true)
    }

    // ------------------------------------------------------------
    // Test 2
    //
    //          5
    //        /   \
    //       3     6
    //      / \     \
    //     2   4     7
    //
    // target = 28
    // expected = false
    // ------------------------------------------------------------
    run {
        val root = TreeNode(5)
        root.left = TreeNode(3)
        root.right = TreeNode(6)
        root.left!!.left = TreeNode(2)
        root.left!!.right = TreeNode(4)
        root.right!!.right = TreeNode(7)

        tests += TestCase(root, 28, false)
    }

    // ------------------------------------------------------------
    // Test 3
    //
    //        2
    //       /
    //      1
    //
    // target = 3
    // expected = true
    // ------------------------------------------------------------
    run {
        val root = TreeNode(2)
        root.left = TreeNode(1)

        tests += TestCase(root, 3, true)
    }

    // ------------------------------------------------------------
    // Test 4
    //
    //      10
    //     /  \
    //    5    15
    //   / \   / \
    //  3   7 12 18
    //
    // target = 30
    // expected = true
    // ------------------------------------------------------------
    run {
        val root = TreeNode(10)
        root.left = TreeNode(5)
        root.right = TreeNode(15)
        root.left!!.left = TreeNode(3)
        root.left!!.right = TreeNode(7)
        root.right!!.left = TreeNode(12)
        root.right!!.right = TreeNode(18)

        tests += TestCase(root, 30, true)
    }

    // ------------------------------------------------------------
    // Test 5
    //
    //      42
    //
    // target = 84
    // expected = false
    // ------------------------------------------------------------
    run {
        val root = TreeNode(42)

        tests += TestCase(root, 84, false)
    }

    tests.forEachIndexed { index, test ->

        val actual = solve(test.root, test.target)

        println("----------------------------------")
        println("Test ${index + 1}")
        println("Target        : ${test.target}")
        println("Expected      : ${test.expected}")
        println("Actual        : $actual")
        println("Valid BST     : ${isValidBST(test.root)}")

        if (actual == test.expected) {
            println("PASS ✅")
        } else {
            println("FAIL ❌")
        }
    }
}