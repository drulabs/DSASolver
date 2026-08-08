/**
 * 450. Delete Node in a BST
 *
 * Given the root of a Binary Search Tree and an integer key,
 * delete the node with the given key from the BST.
 *
 * Return the (possibly new) root of the BST.
 *
 * Constraints:
 * - The number of nodes in the tree is in the range [0, 10^4].
 * - -10^5 <= Node.val <= 10^5
 * - All node values are unique.
 * - root is a valid BST.
 * - -10^5 <= key <= 10^5
 */

class TreeNode(var value: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}

// private fun solve(root: TreeNode?, key: Int): TreeNode? {
//     if (root == null) return null

//     val dummy = TreeNode(-1)
//     dummy.right = root
//     val (nodeToDelete, parent, isLeft) = findNode(root, key, dummy, false)
    
//     if (nodeToDelete == null) return root

//     val left = nodeToDelete.left
//     val right = nodeToDelete.right

//     println("delete = ${nodeToDelete.value}, parent = ${parent.value}, isLeft = $isLeft, left = ${left?.value ?: -1}, right = ${right?.value ?: -1}")

//     var prev: TreeNode? = null
//     var replaceWith: TreeNode? = nodeToDelete
//     if (isLeft) {
//         replaceWith = nodeToDelete.right
//         while(replaceWith?.left != null) {
//             prev = replaceWith
//             replaceWith = replaceWith.left
//         }
//         prev?.left = replaceWith?.right
//         replaceWith?.right = null
//         parent.left = replaceWith
//     } else {
//         replaceWith = nodeToDelete.left
//         while(replaceWith?.right != null) {
//             prev = replaceWith
//             replaceWith = replaceWith.right
//         }
//         prev?.right = replaceWith?.left
//         replaceWith?.left = null
//         parent.right = replaceWith
//     }
//     if (left != replaceWith)
//         replaceWith?.left = left
    
//     if (right != replaceWith)
//         replaceWith?.right = right

//     return dummy.right
// }

// private fun findNode(root: TreeNode?, key: Int, parent: TreeNode, isLeft: Boolean): Triple<TreeNode?, TreeNode, Boolean> {
//     if (root == null || root.value == key) return Triple(root, parent, isLeft)

//     return if (root.value > key)
//         findNode(root.left, key, root, true)
//     else
//         findNode(root.right, key, root, false)
// }

private fun solve(root: TreeNode?, key: Int): TreeNode? {
    if (root == null) return null

    val dummy = TreeNode(-1)
    dummy.right = root

    when {
        key < root.value -> {
            root.left = solve(root.left, key)
        }

        key > root.value -> {
            root.right = solve(root.right, key)
        }

        else -> {
            if (root.left == null) return root.right
            if (root.right == null) return root.left

            val successor = findMin(root.right!!)
            val left = root.left
            dummy.right = successor
            successor.right = solve(root.right, successor.value)
            successor.left = left
        }
    }

    return dummy.right
}

private fun findMin(root: TreeNode): TreeNode {
    var current = root

    while (current.left != null) {
        current = current.left!!
    }

    return current
}

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
        val key: Int,
        val expected: List<Int>
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
    // delete = 3
    //
    // expected inorder = [2,4,5,6,7]
    // ------------------------------------------------------------
    run {
        val root = TreeNode(5)
        root.left = TreeNode(3)
        root.right = TreeNode(6)
        root.left!!.left = TreeNode(2)
        root.left!!.right = TreeNode(4)
        root.right!!.right = TreeNode(7)

        tests += TestCase(root, 3, listOf(2,4,5,6,7))
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
    // delete = 5
    //
    // expected inorder = [2,3,4,6,7]
    // ------------------------------------------------------------
    run {
        val root = TreeNode(5)
        root.left = TreeNode(3)
        root.right = TreeNode(6)
        root.left!!.left = TreeNode(2)
        root.left!!.right = TreeNode(4)
        root.right!!.right = TreeNode(7)

        tests += TestCase(root, 5, listOf(2,3,4,6,7))
    }

    // ------------------------------------------------------------
    // Test 3
    //
    //          5
    //        /   \
    //       3     6
    //      / \     \
    //     2   4     7
    //
    // delete = 7
    //
    // expected inorder = [2,3,4,5,6]
    // ------------------------------------------------------------
    run {
        val root = TreeNode(5)
        root.left = TreeNode(3)
        root.right = TreeNode(6)
        root.left!!.left = TreeNode(2)
        root.left!!.right = TreeNode(4)
        root.right!!.right = TreeNode(7)

        tests += TestCase(root, 7, listOf(2,3,4,5,6))
    }

    // ------------------------------------------------------------
    // Test 4
    //
    //      2
    //     /
    //    1
    //
    // delete = 2
    //
    // expected inorder = [1]
    // ------------------------------------------------------------
    run {
        val root = TreeNode(2)
        root.left = TreeNode(1)

        tests += TestCase(root, 2, listOf(1))
    }

    // ------------------------------------------------------------
    // Test 5
    //
    //      1
    //
    // delete = 1
    //
    // expected inorder = []
    // ------------------------------------------------------------
    run {
        val root = TreeNode(1)

        tests += TestCase(root, 1, emptyList())
    }

    // ------------------------------------------------------------
    // Test 6
    //
    //      4
    //     / \
    //    2   6
    //   / \ / \
    //  1  3 5  7
    //
    // delete = 42
    //
    // expected inorder = [1,2,3,4,5,6,7]
    // ------------------------------------------------------------
    run {
        val root = TreeNode(4)
        root.left = TreeNode(2)
        root.right = TreeNode(6)

        root.left!!.left = TreeNode(1)
        root.left!!.right = TreeNode(3)

        root.right!!.left = TreeNode(5)
        root.right!!.right = TreeNode(7)

        tests += TestCase(root, 42, listOf(1,2,3,4,5,6,7))
    }

    // ------------------------------------------------------------
    // Test 7
    //
    //              8
    //           /     \
    //          4       12
    //        /  \     /  \
    //       2    6   10   14
    //      / \  / \  / \  / \
    //     1  3 5  7 9 11 13 15
    //
    // delete = 4
    //
    // expected inorder =
    // [1,2,3,5,6,7,8,9,10,11,12,13,14,15]
    // ------------------------------------------------------------
    run {
        val root = TreeNode(8)

        root.left = TreeNode(4)
        root.right = TreeNode(12)

        root.left!!.left = TreeNode(2)
        root.left!!.right = TreeNode(6)
        root.right!!.left = TreeNode(10)
        root.right!!.right = TreeNode(14)

        root.left!!.left!!.left = TreeNode(1)
        root.left!!.left!!.right = TreeNode(3)
        root.left!!.right!!.left = TreeNode(5)
        root.left!!.right!!.right = TreeNode(7)

        root.right!!.left!!.left = TreeNode(9)
        root.right!!.left!!.right = TreeNode(11)
        root.right!!.right!!.left = TreeNode(13)
        root.right!!.right!!.right = TreeNode(15)

        tests += TestCase(
            root,
            4,
            listOf(1,2,3,5,6,7,8,9,10,11,12,13,14,15)
        )
    }

    // ------------------------------------------------------------
    // Test 8
    //
    //              8
    //           /     \
    //          4       12
    //        /  \     /  \
    //       2    6   10   14
    //      / \  / \  / \  / \
    //     1  3 5  7 9 11 13 15
    //
    // delete = 10
    //
    // expected inorder =
    // [1,2,3,4,5,6,7,8,9,11,12,13,14,15]
    // ------------------------------------------------------------
    run {
        val root = TreeNode(8)

        root.left = TreeNode(4)
        root.right = TreeNode(12)

        root.left!!.left = TreeNode(2)
        root.left!!.right = TreeNode(6)
        root.right!!.left = TreeNode(10)
        root.right!!.right = TreeNode(14)

        root.left!!.left!!.left = TreeNode(1)
        root.left!!.left!!.right = TreeNode(3)
        root.left!!.right!!.left = TreeNode(5)
        root.left!!.right!!.right = TreeNode(7)

        root.right!!.left!!.left = TreeNode(9)
        root.right!!.left!!.right = TreeNode(11)
        root.right!!.right!!.left = TreeNode(13)
        root.right!!.right!!.right = TreeNode(15)

        tests += TestCase(
            root,
            10,
            listOf(1,2,3,4,5,6,7,8,9,11,12,13,14,15)
        )
    }

    // ------------------------------------------------------------
    // Test 9
    //
    //              8
    //           /     \
    //          4       12
    //        /  \     /  \
    //       2    6   10   14
    //      / \  / \  / \  / \
    //     1  3 5  7 9 11 13 15
    //
    // delete = 8
    //
    // expected inorder =
    // [1,2,3,4,5,6,7,9,10,11,12,13,14,15]
    // ------------------------------------------------------------
    run {
        val root = TreeNode(8)

        root.left = TreeNode(4)
        root.right = TreeNode(12)

        root.left!!.left = TreeNode(2)
        root.left!!.right = TreeNode(6)
        root.right!!.left = TreeNode(10)
        root.right!!.right = TreeNode(14)

        root.left!!.left!!.left = TreeNode(1)
        root.left!!.left!!.right = TreeNode(3)
        root.left!!.right!!.left = TreeNode(5)
        root.left!!.right!!.right = TreeNode(7)

        root.right!!.left!!.left = TreeNode(9)
        root.right!!.left!!.right = TreeNode(11)
        root.right!!.right!!.left = TreeNode(13)
        root.right!!.right!!.right = TreeNode(15)

        tests += TestCase(
            root,
            8,
            listOf(1,2,3,4,5,6,7,9,10,11,12,13,14,15)
        )
    }

    // ------------------------------------------------------------
    // Test 10
    //
    //          5
    //        /   \
    //       3     6
    //      / \     \
    //     2   4     7
    //
    // delete = 6
    //
    // expected inorder = [2,3,4,5,7]
    // ------------------------------------------------------------
    run {
        val root = TreeNode(5)
        root.left = TreeNode(3)
        root.right = TreeNode(6)
        root.left!!.left = TreeNode(2)
        root.left!!.right = TreeNode(4)
        root.right!!.right = TreeNode(7)

        tests += TestCase(root, 6, listOf(2,3,4,5,7))
    }

    tests.forEachIndexed { index, test ->

        val result = solve(test.root, test.key)
        val actual = inorder(result)

        println("----------------------------------")
        println("Test ${index + 1}")
        println("Delete Key   : ${test.key}")
        println("Expected     : ${test.expected}")
        println("Actual       : $actual")
        println("Valid BST    : ${isValidBST(result)}")

        if (actual == test.expected && isValidBST(result)) {
            println("PASS ✅")
        } else {
            println("FAIL ❌")
        }
    }
}