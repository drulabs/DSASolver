/*
    Convert Sorted Array to Binary Search Tree

    Given an integer array nums where the elements are sorted in ascending order,
    convert it into a height-balanced Binary Search Tree.

    A height-balanced binary tree is a binary tree in which the depth of the two
    subtrees of every node never differs by more than 1.

    A Binary Search Tree satisfies the following conditions:

    - Every value in the left subtree is smaller than the current node.
    - Every value in the right subtree is greater than the current node.
    - Both left and right subtrees must also be Binary Search Trees.

    The returned tree does not need to have one specific structure.
    Any valid height-balanced BST containing all values from nums is accepted.


    Example 1:

    Input:

        nums = [-10, -3, 0, 5, 9]

    One possible output:

              0
             / \
           -3   9
           /   /
        -10   5

    Another valid output:

              0
             / \
          -10   5
             \   \
             -3   9


    Example 2:

    Input:

        nums = [1, 3]

    Possible output:

          1
           \
            3

    Another valid output:

            3
           /
          1


    Constraints:

    - 1 <= nums.size <= 10_000
    - -10_000 <= nums[i] <= 10_000
    - nums is sorted in strictly ascending order.
*/


data class TreeNode(
    val value: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null
)


private fun solve(nums: List<Int>, low: Int = 0, high: Int = nums.lastIndex): TreeNode? {
    if (low > high) return null

    val mid = (low + high)/2
    val root = TreeNode(nums[mid])

    root.left = solve(nums, low, mid - 1)
    root.right = solve(nums, mid + 1, high)

    return root    
}


private fun inorder(root: TreeNode?): List<Int> {
    val result = mutableListOf<Int>()

    fun traverse(node: TreeNode?) {
        if (node == null) return

        traverse(node.left)
        result.add(node.value)
        traverse(node.right)
    }

    traverse(root)
    return result
}


private fun isBalanced(root: TreeNode?): Boolean {

    fun height(node: TreeNode?): Int {
        if (node == null) return 0

        val leftHeight = height(node.left)
        if (leftHeight == -1) return -1

        val rightHeight = height(node.right)
        if (rightHeight == -1) return -1

        if (kotlin.math.abs(leftHeight - rightHeight) > 1) {
            return -1
        }

        return 1 + maxOf(leftHeight, rightHeight)
    }

    return height(root) != -1
}


private fun isValidBST(root: TreeNode?): Boolean {

    fun validate(
        node: TreeNode?,
        lowerBound: Long,
        upperBound: Long
    ): Boolean {

        if (node == null) return true

        if (node.value <= lowerBound || node.value >= upperBound) {
            return false
        }

        return validate(
            node = node.left,
            lowerBound = lowerBound,
            upperBound = node.value.toLong()
        ) && validate(
            node = node.right,
            lowerBound = node.value.toLong(),
            upperBound = upperBound
        )
    }

    return validate(
        node = root,
        lowerBound = Long.MIN_VALUE,
        upperBound = Long.MAX_VALUE
    )
}


private data class TestCase(
    val nums: List<Int>,
    val description: String
)


fun main() {

    val testCases = listOf(

        TestCase(
            nums = listOf(-10, -3, 0, 5, 9),
            description = "Odd number of elements"
        ),

        TestCase(
            nums = listOf(1, 3),
            description = "Two elements"
        ),

        TestCase(
            nums = listOf(1),
            description = "Single element"
        ),

        TestCase(
            nums = listOf(1, 2, 3),
            description = "Three elements"
        ),

        TestCase(
            nums = listOf(1, 2, 3, 4),
            description = "Even number of elements"
        ),

        TestCase(
            nums = listOf(-5, -4, -3, -2, -1),
            description = "All negative values"
        ),

        TestCase(
            nums = listOf(2, 4, 6, 8, 10, 12, 14),
            description = "Perfectly sized input"
        ),

        TestCase(
            nums = listOf(
                -15, -12, -8, -3, 0,
                4, 7, 11, 18, 25
            ),
            description = "Larger input"
        )
    )


    for ((index, testCase) in testCases.withIndex()) {

        val result = solve(testCase.nums)

        val actualInorder = inorder(result)
        val containsAllValues = actualInorder == testCase.nums
        val validBST = isValidBST(result)
        val balanced = isBalanced(result)

        val passed =
            containsAllValues &&
            validBST &&
            balanced

        println(
            """
            |Test ${index + 1}: ${testCase.description}
            |Input:                ${testCase.nums}
            |Inorder traversal:    $actualInorder
            |Contains all values:  $containsAllValues
            |Valid BST:            $validBST
            |Height-balanced:      $balanced
            |Result:               ${if (passed) "PASS ✅" else "FAIL ❌"}
            |--------------------------------------------------
            """.trimMargin()
        )
    }
}