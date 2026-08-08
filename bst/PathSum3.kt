/*
Problem: Path Sum III

Given the root of a binary tree and an integer targetSum, return the
number of downward paths whose node values sum to targetSum.

A path may start and end at any node, but it must move from parent
to child.

Constraints:
- The number of nodes is in the range [0, 1_000].
- Node values may be negative, zero, or positive.
- targetSum may be negative, zero, or positive.
- Intermediate sums may exceed the Int range.
*/

data class TreeNode(
    val value: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null
)

private fun solve(root: TreeNode?, targetSum: Long): Int {
    if (root == null) return 0
    val prefix = mutableMapOf<Long, Int>()
    prefix[0L] = 1
    return helper(root, targetSum, prefix)
}

private fun helper(root: TreeNode?, targetSum: Long, prefix: MutableMap<Long, Int>, sum: Int = 0): Int {
    if (root == null) return 0

    val newSum = sum + root.value
    var count = 0
    if (prefix.containsKey(newSum - targetSum)) {
        count += prefix[newSum - targetSum] ?: 0
    }

    prefix[newSum.toLong()] = (prefix[newSum.toLong()] ?: 0) + 1

    return count + helper(root.left, targetSum, prefix, newSum) + helper(root.right, targetSum, prefix, newSum)
    
}

/*
 * Serializes a tree using preorder traversal with null markers.
 *
 * Including null markers ensures that both node values and tree structure
 * are compared.
 */
private fun serialize(root: TreeNode?): String {
    val values = mutableListOf<String>()

    fun traverse(node: TreeNode?) {
        if (node == null) {
            values.add("#")
            return
        }

        values.add(node.value.toString())
        traverse(node.left)
        traverse(node.right)
    }

    traverse(root)
    return values.joinToString(",")
}

private fun runTest(
    testName: String,
    root: TreeNode?,
    targetSum: Long,
    expected: Int
) {
    val actual = solve(root, targetSum)

    if (actual == expected) {
        println("PASS: $testName")
    } else {
        println(
            "FAIL: $testName | " +
                "expected=$expected, actual=$actual"
        )
    }
}

fun main() {
    /*
                     10
                    /  \
                   5   -3
                  / \    \
                 3   2    11
                / \   \
               3  -2   1

        Target: 8

        Valid paths:
        5 -> 3
        5 -> 2 -> 1
        -3 -> 11
    */
    val root1 = TreeNode(
        10,
        left = TreeNode(
            5,
            left = TreeNode(
                3,
                left = TreeNode(3),
                right = TreeNode(-2)
            ),
            right = TreeNode(
                2,
                right = TreeNode(1)
            )
        ),
        right = TreeNode(
            -3,
            right = TreeNode(11)
        )
    )

    runTest(
        testName = "Multiple paths starting below root",
        root = root1,
        targetSum = 8L,
        expected = 3
    )

    /*
                  1
                 / \
               -1  -1
               /   / \
              1   1   1

        Target: 0

        Valid paths:
        1 -> -1, using the left child
        1 -> -1, using the right child
        -1 -> 1, using the left subtree
        -1 -> 1, using the right-left child
        -1 -> 1, using the right-right child
    */
    val root2 = TreeNode(
        1,
        left = TreeNode(
            -1,
            left = TreeNode(1)
        ),
        right = TreeNode(
            -1,
            left = TreeNode(1),
            right = TreeNode(1)
        )
    )

    runTest(
        testName = "Duplicate values represent distinct paths",
        root = root2,
        targetSum = 0L,
        expected = 5
    )

    /*
             0
            / \
           0   0

        Target: 0

        Valid paths:
        Each individual node: 3 paths
        Root -> left: 1 path
        Root -> right: 1 path
    */
    val root3 = TreeNode(
        0,
        left = TreeNode(0),
        right = TreeNode(0)
    )

    runTest(
        testName = "Zero-valued paths",
        root = root3,
        targetSum = 0L,
        expected = 5
    )

    /*
             1
              \
               2
                \
                 3
                  \
                   4

        Target: 6

        Valid paths:
        1 -> 2 -> 3
        2 -> 4 is invalid because nodes must be contiguous.
    */
    val root4 = TreeNode(
        1,
        right = TreeNode(
            2,
            right = TreeNode(
                3,
                right = TreeNode(4)
            )
        )
    )

    runTest(
        testName = "Skewed tree",
        root = root4,
        targetSum = 6L,
        expected = 1
    )

    /*
             -2
               \
               -3

        Target: -5
    */
    val root5 = TreeNode(
        -2,
        right = TreeNode(-3)
    )

    runTest(
        testName = "Negative target sum",
        root = root5,
        targetSum = -5L,
        expected = 1
    )

    runTest(
        testName = "Empty tree",
        root = null,
        targetSum = 0L,
        expected = 0
    )

    /*
             1_000_000_000
                    \
                  1_000_000_000
                         \
                       1_000_000_000

        Forces prefix sums beyond safe Int arithmetic.
    */
    val root6 = TreeNode(
        1_000_000_000,
        right = TreeNode(
            1_000_000_000,
            right = TreeNode(1_000_000_000)
        )
    )

    runTest(
        testName = "Use Long for cumulative sums",
        root = root6,
        targetSum = 3_000_000_000L,
        expected = 1
    )
}