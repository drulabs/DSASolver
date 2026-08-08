/*
    Increasing Order Search Tree

    Given the root of a Binary Search Tree (BST), rearrange the tree so that:

    - The leftmost node becomes the new root.
    - Every node has no left child.
    - Every node has exactly one right child.
    - The nodes appear in increasing order.

    The relative order of the nodes must remain the same as the inorder
    traversal of the original BST.


    Example 1:

            5
           / \
          3   6
         / \   \
        2   4   8
       /       / \
      1       7   9

    Output:

    1
     \
      2
       \
        3
         \
          4
           \
            5
             \
              6
               \
                7
                 \
                  8
                   \
                    9


    Example 2:

    Input:

          5
         / \
        1   7

    Output:

    1
     \
      5
       \
        7


    Constraints:

    - The number of nodes is in the range [1, 100].
    - 0 <= Node.value <= 1000
*/


data class TreeNode(
    val value: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null
)

// Iterative
// private fun solve(root: TreeNode?): TreeNode? {
//     if (root == null) return null

//     val stack = ArrayDeque<TreeNode>()
//     var current: TreeNode? = root
    
//     var head: TreeNode? = null
//     var prev: TreeNode? = null

//     while (current != null || stack.isNotEmpty()) {

//         // go the left most node - preorder things
//         while (current != null) {
//             stack.addLast(current)
//             current = current.left
//         }

//         // in the first iteration top of the stack new head
//         val node = stack.removeLast()
//         if (head == null) {
//             head = node
//         }

//         // as this is essentially a linked list, set left to null
//         // and right to prev
//         prev?.right = node
//         prev?.left = null
        
//         prev = node
//         current = node.right
//     }
    
//     // last node cleanup
//     prev?.left = null
//     prev?.right = null // this is not needed in a BST
    
//     return head
// }

private fun solve(root: TreeNode?): TreeNode? {

    var head: TreeNode? = null
    var prev: TreeNode? = null

    fun inorder(node: TreeNode?) {
        if (node == null) return

        inorder(node.left)

        if (head == null) {
            head = node
        }

        prev?.right = node
        prev?.left = null

        prev = node

        inorder(node.right)
    }

    inorder(root)

    prev?.left = null
    prev?.right = null

    return head
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


private fun isIncreasingOrderTree(root: TreeNode?): Boolean {
    var current = root
    var previous = Int.MIN_VALUE

    while (current != null) {

        if (current.left != null) return false

        if (current.value < previous) return false

        previous = current.value
        current = current.right
    }

    return true
}


private data class TestCase(
    val root: TreeNode?,
    val description: String
)


fun main() {

    val testCases = listOf(

        TestCase(
            root = TreeNode(
                5,
                left = TreeNode(
                    3,
                    left = TreeNode(
                        2,
                        left = TreeNode(1)
                    ),
                    right = TreeNode(4)
                ),
                right = TreeNode(
                    6,
                    right = TreeNode(
                        8,
                        left = TreeNode(7),
                        right = TreeNode(9)
                    )
                )
            ),
            description = "Example tree"
        ),

        TestCase(
            root = TreeNode(
                5,
                left = TreeNode(1),
                right = TreeNode(7)
            ),
            description = "Three nodes"
        ),

        TestCase(
            root = TreeNode(1),
            description = "Single node"
        ),

        TestCase(
            root = TreeNode(
                4,
                left = TreeNode(
                    3,
                    left = TreeNode(
                        2,
                        left = TreeNode(1)
                    )
                )
            ),
            description = "Left skewed BST"
        ),

        TestCase(
            root = TreeNode(
                1,
                right = TreeNode(
                    2,
                    right = TreeNode(
                        3,
                        right = TreeNode(4)
                    )
                )
            ),
            description = "Already increasing"
        ),

        TestCase(
            root = TreeNode(
                8,
                left = TreeNode(
                    4,
                    left = TreeNode(2),
                    right = TreeNode(6)
                ),
                right = TreeNode(
                    12,
                    left = TreeNode(10),
                    right = TreeNode(14)
                )
            ),
            description = "Perfect BST"
        )
    )

    for ((index, testCase) in testCases.withIndex()) {

        val expected = inorder(testCase.root)

        val result = solve(testCase.root)

        val actual = inorder(result)

        val passed =
            expected == actual &&
            isIncreasingOrderTree(result)

        println(
            """
            |Test ${index + 1}: ${testCase.description}
            |Expected inorder:   $expected
            |Actual inorder:     $actual
            |Valid structure:    ${isIncreasingOrderTree(result)}
            |Result:             ${if (passed) "PASS ✅" else "FAIL ❌"}
            |--------------------------------------------------
            """.trimMargin()
        )
    }
}