/*
Problem:
Implement the BSTIterator class that represents an iterator
over the inorder traversal of a binary search tree.

BSTIterator(root) initializes the iterator with the root of the BST.

next() returns the next smallest value in the BST.

hasNext() returns true if there is another value available,
otherwise false.

next() will only be called when hasNext() is true.

The iterator should use:
- Average O(1) time per next() call
- O(h) extra space, where h is the height of the tree
*/

data class TreeNode(
    val value: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null
)

private class BSTIterator(root: TreeNode?) {

    private val stack = ArrayDeque<TreeNode>()
    var current: TreeNode? = root

    init {
        while (current != null) {
            stack.addLast(current!!)
            current = current?.left
        }
    }

    fun next(): Int {
        var node = stack.removeLast()
        current = node.right
        while (current != null) {
            stack.addLast(current!!)
            current = current?.left
        }
        return node.value
    }

    fun hasNext(): Boolean {
        return stack.isNotEmpty()
    }
}

private sealed class Operation {
    data object Next : Operation()
    data object HasNext : Operation()
}

private fun runOperations(
    root: TreeNode?,
    operations: List<Operation>
): List<Any> {
    val iterator = BSTIterator(root)
    val output = mutableListOf<Any>()

    for (operation in operations) {
        when (operation) {
            Operation.Next -> output.add(iterator.next())
            Operation.HasNext -> output.add(iterator.hasNext())
        }
    }

    return output
}

fun main() {

    // Test Case 1
    //
    //        7
    //       / \
    //      3   15
    //         /  \
    //        9   20
    //
    // Expected:
    // [3, 7, true, 9, true, 15, true, 20, false]
    val root1 = TreeNode(7)
    root1.left = TreeNode(3)
    root1.right = TreeNode(15)
    root1.right?.left = TreeNode(9)
    root1.right?.right = TreeNode(20)

    val operations1 = listOf(
        Operation.Next,
        Operation.Next,
        Operation.HasNext,
        Operation.Next,
        Operation.HasNext,
        Operation.Next,
        Operation.HasNext,
        Operation.Next,
        Operation.HasNext
    )

    // Test Case 2
    //
    //      2
    //     / \
    //    1   3
    //
    // Expected:
    // [true, 1, 2, true, 3, false]
    val root2 = TreeNode(2)
    root2.left = TreeNode(1)
    root2.right = TreeNode(3)

    val operations2 = listOf(
        Operation.HasNext,
        Operation.Next,
        Operation.Next,
        Operation.HasNext,
        Operation.Next,
        Operation.HasNext
    )

    // Test Case 3
    //
    //      1
    //
    // Expected:
    // [true, 1, false]
    val root3 = TreeNode(1)

    val operations3 = listOf(
        Operation.HasNext,
        Operation.Next,
        Operation.HasNext
    )

    // Test Case 4
    //
    //          5
    //         /
    //        4
    //       /
    //      3
    //     /
    //    2
    //   /
    //  1
    //
    // Expected:
    // [1, 2, 3, 4, 5, false]
    val root4 = TreeNode(5)
    root4.left = TreeNode(4)
    root4.left?.left = TreeNode(3)
    root4.left?.left?.left = TreeNode(2)
    root4.left?.left?.left?.left = TreeNode(1)

    val operations4 = listOf(
        Operation.Next,
        Operation.Next,
        Operation.Next,
        Operation.Next,
        Operation.Next,
        Operation.HasNext
    )

    // Test Case 5
    //
    //    1
    //     \
    //      2
    //       \
    //        3
    //         \
    //          4
    //           \
    //            5
    //
    // Expected:
    // [true, 1, true, 2, 3, 4, 5, false]
    val root5 = TreeNode(1)
    root5.right = TreeNode(2)
    root5.right?.right = TreeNode(3)
    root5.right?.right?.right = TreeNode(4)
    root5.right?.right?.right?.right = TreeNode(5)

    val operations5 = listOf(
        Operation.HasNext,
        Operation.Next,
        Operation.HasNext,
        Operation.Next,
        Operation.Next,
        Operation.Next,
        Operation.Next,
        Operation.HasNext
    )

    // Test Case 6
    //
    //          8
    //        /   \
    //       3     10
    //      / \      \
    //     1   6      14
    //        / \     /
    //       4   7   13
    //
    // Expected:
    // [1, 3, 4, true, 6, 7, 8, 10, 13, 14, false]
    val root6 = TreeNode(8)
    root6.left = TreeNode(3)
    root6.right = TreeNode(10)
    root6.left?.left = TreeNode(1)
    root6.left?.right = TreeNode(6)
    root6.left?.right?.left = TreeNode(4)
    root6.left?.right?.right = TreeNode(7)
    root6.right?.right = TreeNode(14)
    root6.right?.right?.left = TreeNode(13)

    val operations6 = listOf(
        Operation.Next,
        Operation.Next,
        Operation.Next,
        Operation.HasNext,
        Operation.Next,
        Operation.Next,
        Operation.Next,
        Operation.Next,
        Operation.Next,
        Operation.Next,
        Operation.HasNext
    )

    val inputs = listOf(
        root1,
        root2,
        root3,
        root4,
        root5,
        root6
    )

    val operations = listOf(
        operations1,
        operations2,
        operations3,
        operations4,
        operations5,
        operations6
    )

    val expected = listOf(
        listOf<Any>(3, 7, true, 9, true, 15, true, 20, false),
        listOf<Any>(true, 1, 2, true, 3, false),
        listOf<Any>(true, 1, false),
        listOf<Any>(1, 2, 3, 4, 5, false),
        listOf<Any>(true, 1, true, 2, 3, 4, 5, false),
        listOf<Any>(1, 3, 4, true, 6, 7, 8, 10, 13, 14, false)
    )

    for (i in inputs.indices) {
        println("----------------------------------")
        println("Test Case ${i + 1}")

        val output = runOperations(
            inputs[i],
            operations[i]
        )

        println("output => $output")
        println("expected => ${expected[i]}")
        println(
            "${if (output == expected[i]) "✅" else "❌"}"
        )
    }
}