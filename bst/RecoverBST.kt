/**
 * 99. Recover Binary Search Tree
 *
 * You are given the root of a binary search tree where the values of
 * exactly two nodes have been swapped by mistake.
 *
 * Recover the tree without changing its structure.
 *
 * Constraints:
 * - The number of nodes in the tree is in the range [2, 1000].
 * - -2^31 <= Node.val <= 2^31 - 1
 */

class TreeNode(var value: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}

// My solution
// private fun solve(root: TreeNode?) {
//     if (root == null) return

//     val stack = ArrayDeque<TreeNode>()
//     var current = root
//     var prev: TreeNode? = null

//     val swappedNodes = mutableListOf<Pair<TreeNode, TreeNode>>()

//     while (current != null || stack.isNotEmpty()) {
//         while (current != null) {
//             stack.addLast(current)
//             current = current.left
//         }
//         current = stack.removeLast()
//         if (prev != null && current.value < prev.value) {
//             swappedNodes.add(current to prev)
//         }
//         prev = current
//         current = current.right
//     }

//     if (swappedNodes.size == 2) { // non adjacent swap
//         val (left, lp) = swappedNodes[0]
//         val (right, rp) = swappedNodes[1]
//         val temp = lp.value
//         lp.value = right.value
//         right.value = temp
//     } else if (swappedNodes.size == 1) { // adjacent swap
//         val (node, prev) = swappedNodes[0]
//         val temp = node.value
//         node.value = prev.value
//         prev.value = temp
//     } else {
//         // ignore
//     }
// }

// AI generated solution
private fun solve(root: TreeNode?) {
    if (root == null) return

    val stack = ArrayDeque<TreeNode>()
    var current: TreeNode? = root
    var previous: TreeNode? = null

    /*
     * During inorder traversal, BST values should appear in ascending order.
     *
     * If two values were swapped:
     *
     * Adjacent swap:
     *   1, 3, 2, 4
     *      ^  ^
     *   One ordering violation.
     *
     * Non-adjacent swap:
     *   1, 5, 3, 4, 2, 6
     *      ^        ^
     *   Two ordering violations.
     *
     * At the first violation:
     *   previous is always one of the swapped nodes.
     *
     * At the second violation, if it exists:
     *   current is the other swapped node.
     */
    var firstSwappedNode: TreeNode? = null
    var secondSwappedNode: TreeNode? = null

    while (current != null || stack.isNotEmpty()) {

        // Move to the leftmost unvisited node.
        while (current != null) {
            stack.addLast(current)
            current = current.left
        }

        current = stack.removeLast()

        /*
         * In a valid BST:
         * previous.value < current.value
         *
         * If current is smaller, we found an inversion.
         */
        if (previous != null && previous.value > current.value) {

            /*
             * For the first inversion, previous is the first misplaced node.
             *
             * Example:
             *   1, 5, 3, 4, 2, 6
             *      ^
             *   previous = 5
             */
            if (firstSwappedNode == null) {
                firstSwappedNode = previous
            }

            /*
             * current is always a candidate for the second misplaced node.
             *
             * For adjacent swaps, this is assigned once.
             * For non-adjacent swaps, the second inversion overwrites it
             * with the correct node.
             */
            secondSwappedNode = current
        }

        previous = current
        current = current.right
    }

    // Swap only the misplaced values. The tree structure remains unchanged.
    if (firstSwappedNode != null && secondSwappedNode != null) {
        val temp = firstSwappedNode.value
        firstSwappedNode.value = secondSwappedNode.value
        secondSwappedNode.value = temp
    }
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
        val expectedInorder: List<Int>
    )

    val tests = mutableListOf<TestCase>()

    // ------------------------------------------------------------
    // Test 1
    //
    //      3
    //     / \
    //    1   4
    //       /
    //      2
    //
    // Swapped: 2 <-> 3
    //
    // Expected inorder:
    // [1,2,3,4]
    // ------------------------------------------------------------
    run {
        val root = TreeNode(3)
        root.left = TreeNode(1)
        root.right = TreeNode(4)
        root.right!!.left = TreeNode(2)

        tests += TestCase(
            root,
            listOf(1,2,3,4)
        )
    }

    // ------------------------------------------------------------
    // Test 2
    //
    //      1
    //     /
    //    3
    //     \
    //      2
    //
    // Swapped: 1 <-> 3
    //
    // Expected inorder:
    // [1,2,3]
    // ------------------------------------------------------------
    run {
        val root = TreeNode(1)
        root.left = TreeNode(3)
        root.left!!.right = TreeNode(2)

        tests += TestCase(
            root,
            listOf(1,2,3)
        )
    }

    // ------------------------------------------------------------
    // Test 3
    //
    //           8
    //        /     \
    //       4       12
    //     /  \     /  \
    //    2    6   10   14
    //   / \  / \  / \  / \
    //  1  3 13 7 9 11 5 15
    //        ^           ^
    //      swapped    swapped
    //
    // Swapped: 5 <-> 13
    //
    // Expected inorder:
    // [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15]
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

        root.left!!.right!!.left = TreeNode(13) // swapped
        root.left!!.right!!.right = TreeNode(7)

        root.right!!.left!!.left = TreeNode(9)
        root.right!!.left!!.right = TreeNode(11)

        root.right!!.right!!.left = TreeNode(5) // swapped
        root.right!!.right!!.right = TreeNode(15)

        tests += TestCase(
            root,
            (1..15).toList()
        )
    }

    // ------------------------------------------------------------
    // Test 4
    //
    //               7
    //            /     \
    //           4       12
    //         /  \     /  \
    //        2    6   10   14
    //       / \  / \  / \  / \
    //      1  3 5  8 9 11 13 15
    //              ^
    //            swapped (with 7 - the root)
    //
    // Swapped: 7 <-> 8
    //
    // Expected inorder:
    // [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15]
    // ------------------------------------------------------------
    run {
        val root = TreeNode(7) // swapped

        root.left = TreeNode(4)
        root.right = TreeNode(12)

        root.left!!.left = TreeNode(2)
        root.left!!.right = TreeNode(6)
        root.right!!.left = TreeNode(10)
        root.right!!.right = TreeNode(14)

        root.left!!.left!!.left = TreeNode(1)
        root.left!!.left!!.right = TreeNode(3)

        root.left!!.right!!.left = TreeNode(5)
        root.left!!.right!!.right = TreeNode(8) // swapped

        root.right!!.left!!.left = TreeNode(9)
        root.right!!.left!!.right = TreeNode(11)

        root.right!!.right!!.left = TreeNode(13)
        root.right!!.right!!.right = TreeNode(15)

        tests += TestCase(
            root,
            (1..15).toList()
        )
    }

    // ------------------------------------------------------------
    // Test 5
    //
    // Adjacent swap in inorder (6 <-> 7)
    //
    //              8
    //           /     \
    //          4       12
    //        /  \     /  \
    //       2    7   10   14
    //      / \  / \  / \  / \
    //     1  3 5  6 9 11 13 15
    //
    // Expected inorder:
    // [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15]
    // ------------------------------------------------------------
    run {
        val root = TreeNode(8)

        root.left = TreeNode(4)
        root.right = TreeNode(12)

        root.left!!.left = TreeNode(2)
        root.left!!.right = TreeNode(7)

        root.right!!.left = TreeNode(10)
        root.right!!.right = TreeNode(14)

        root.left!!.left!!.left = TreeNode(1)
        root.left!!.left!!.right = TreeNode(3)

        root.left!!.right!!.left = TreeNode(5)
        root.left!!.right!!.right = TreeNode(6)

        root.right!!.left!!.left = TreeNode(9)
        root.right!!.left!!.right = TreeNode(11)

        root.right!!.right!!.left = TreeNode(13)
        root.right!!.right!!.right = TreeNode(15)

        tests += TestCase(root, (1..15).toList())
    }

    // ------------------------------------------------------------
    // Test 6
    //
    // Root swapped with deepest leaf (8 <-> 15)
    //
    //              15
    //           /      \
    //          4        12
    //        /  \      /  \
    //       2    6    10   14
    //      / \  / \   / \  / \
    //     1  3 5  7  9 11 13 8
    //
    // Expected inorder:
    // [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15]
    // ------------------------------------------------------------
    run {
        val root = TreeNode(15)

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
        root.right!!.right!!.right = TreeNode(8)

        tests += TestCase(root, (1..15).toList())
    }

    // ------------------------------------------------------------
    // Test 7
    //
    // Opposite subtrees (3 <-> 14)
    //
    //               8
    //            /      \
    //           4        12
    //         /  \      /  \
    //        2    6    10   3
    //       / \  / \   / \  / \
    //      1 14 5  7  9 11 13 15
    //
    // Expected inorder:
    // [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15]
    // ------------------------------------------------------------
    run {
        val root = TreeNode(8)

        root.left = TreeNode(4)
        root.right = TreeNode(12)

        root.left!!.left = TreeNode(2)
        root.left!!.right = TreeNode(6)

        root.right!!.left = TreeNode(10)
        root.right!!.right = TreeNode(3)

        root.left!!.left!!.left = TreeNode(1)
        root.left!!.left!!.right = TreeNode(14)

        root.left!!.right!!.left = TreeNode(5)
        root.left!!.right!!.right = TreeNode(7)

        root.right!!.left!!.left = TreeNode(9)
        root.right!!.left!!.right = TreeNode(11)

        root.right!!.right!!.left = TreeNode(13)
        root.right!!.right!!.right = TreeNode(15)

        tests += TestCase(root, (1..15).toList())
    }

    // ------------------------------------------------------------
    // Test 8
    //
    // Parent-child swap (10 <-> 12)
    //
    //               8
    //            /      \
    //           4        10
    //         /  \      /  \
    //        2    6    12   14
    //       / \  / \   / \  / \
    //      1  3 5  7  9 11 13 15
    //
    // Expected inorder:
    // [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15]
    // ------------------------------------------------------------
    run {
        val root = TreeNode(8)

        root.left = TreeNode(4)
        root.right = TreeNode(10)

        root.left!!.left = TreeNode(2)
        root.left!!.right = TreeNode(6)

        root.right!!.left = TreeNode(12)
        root.right!!.right = TreeNode(14)

        root.left!!.left!!.left = TreeNode(1)
        root.left!!.left!!.right = TreeNode(3)

        root.left!!.right!!.left = TreeNode(5)
        root.left!!.right!!.right = TreeNode(7)

        root.right!!.left!!.left = TreeNode(9)
        root.right!!.left!!.right = TreeNode(11)

        root.right!!.right!!.left = TreeNode(13)
        root.right!!.right!!.right = TreeNode(15)

        tests += TestCase(root, (1..15).toList())
    }

    tests.forEachIndexed { index, test ->

        solve(test.root)

        val actual = inorder(test.root)

        println("----------------------------------")
        println("Test ${index + 1}")
        println("Expected : ${test.expectedInorder}")
        println("Actual   : $actual")
        println("ValidBST : ${isValidBST(test.root)}")

        if (actual == test.expectedInorder && isValidBST(test.root)) {
            println("PASS ✅")
        } else {
            println("FAIL ❌")
        }
    }
}