/*
 * Problem: Split BST
 *
 * Given the root of a Binary Search Tree and an integer target, split the tree
 * into two Binary Search Trees:
 *
 * 1. The first tree must contain every node whose value is less than or equal
 *    to target.
 *
 * 2. The second tree must contain every node whose value is greater than target.
 *
 * The original parent-child relationships should be preserved whenever possible.
 * Existing TreeNode objects must be reused; do not create replacement nodes.
 *
 * Return the roots of the two resulting trees as:
 *
 * Pair(smallerOrEqualTree, greaterTree)
 *
 * Constraints:
 * - The number of nodes is between 0 and 1,000.
 * - Every node value is unique.
 * - The input tree is a valid Binary Search Tree.
 * - Node values and target fit within the Int range.
 */

class TreeNode(
    var value: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null
)

private fun solve(
    root: TreeNode?,
    target: Int
): Pair<TreeNode?, TreeNode?> {
    if (root == null) return null to null

    // irrespective of the target value, the main root node is 
    // always one of the solution nodes
    if (root.value <= target) {
        val (left, right) = solve(root.right, target)
        root.right = left
        return root to right
    } else {
        val (left, right) = solve(root.left, target)
        root.left = right
        return left to root
    }
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
    target: Int,
    expectedSmallerTree: TreeNode?,
    expectedGreaterTree: TreeNode?
) {
    val (actualSmallerTree, actualGreaterTree) = solve(root, target)

    val actualSmaller = serialize(actualSmallerTree)
    val actualGreater = serialize(actualGreaterTree)

    val expectedSmaller = serialize(expectedSmallerTree)
    val expectedGreater = serialize(expectedGreaterTree)

    val passed =
        actualSmaller == expectedSmaller &&
        actualGreater == expectedGreater

    println("$testName: ${if (passed) "{✅}{✅}" else "[❌][❌]"}")

    if (!passed) {
        println("  Expected smaller: $expectedSmaller")
        println("  Actual smaller:   $actualSmaller")
        println("  Expected greater: $expectedGreater")
        println("  Actual greater:   $actualGreater")
    }
}

fun main() {

    /*
     * Test 1
     *
     * Original:
     *
     *             4
     *           /   \
     *          2     6
     *         / \   / \
     *        1   3 5   7
     *
     * target = 2
     *
     * Expected smaller-or-equal tree:
     *
     *          2
     *         /
     *        1
     *
     * Expected greater tree:
     *
     *             4
     *           /   \
     *          3     6
     *               / \
     *              5   7
     */
    val root1 = TreeNode(4).apply {
        left = TreeNode(2).apply {
            left = TreeNode(1)
            right = TreeNode(3)
        }
        right = TreeNode(6).apply {
            left = TreeNode(5)
            right = TreeNode(7)
        }
    }

    val expectedSmaller1 = TreeNode(2).apply {
        left = TreeNode(1)
    }

    val expectedGreater1 = TreeNode(4).apply {
        left = TreeNode(3)
        right = TreeNode(6).apply {
            left = TreeNode(5)
            right = TreeNode(7)
        }
    }

    runTest(
        testName = "Test 1: Split through left subtree",
        root = root1,
        target = 2,
        expectedSmallerTree = expectedSmaller1,
        expectedGreaterTree = expectedGreater1
    )

    /*
     * Test 2
     *
     * Original:
     *
     *             4
     *           /   \
     *          2     6
     *         / \   / \
     *        1   3 5   7
     *
     * target = 5
     *
     * Expected smaller-or-equal tree:
     *
     *             4
     *           /   \
     *          2     5
     *         / \
     *        1   3
     *
     * Expected greater tree:
     *
     *          6
     *           \
     *            7
     */
    val root2 = TreeNode(4).apply {
        left = TreeNode(2).apply {
            left = TreeNode(1)
            right = TreeNode(3)
        }
        right = TreeNode(6).apply {
            left = TreeNode(5)
            right = TreeNode(7)
        }
    }

    val expectedSmaller2 = TreeNode(4).apply {
        left = TreeNode(2).apply {
            left = TreeNode(1)
            right = TreeNode(3)
        }
        right = TreeNode(5)
    }

    val expectedGreater2 = TreeNode(6).apply {
        right = TreeNode(7)
    }

    runTest(
        testName = "Test 2: Split through right subtree",
        root = root2,
        target = 5,
        expectedSmallerTree = expectedSmaller2,
        expectedGreaterTree = expectedGreater2
    )

    /*
     * Test 3
     *
     * Original:
     *
     *          3
     *         / \
     *        1   5
     *         \ /
     *          2 4
     *
     * target = 3
     *
     * Expected smaller-or-equal tree:
     *
     *          3
     *         /
     *        1
     *         \
     *          2
     *
     * Expected greater tree:
     *
     *          5
     *         /
     *        4
     */
    val root3 = TreeNode(3).apply {
        left = TreeNode(1).apply {
            right = TreeNode(2)
        }
        right = TreeNode(5).apply {
            left = TreeNode(4)
        }
    }

    val expectedSmaller3 = TreeNode(3).apply {
        left = TreeNode(1).apply {
            right = TreeNode(2)
        }
    }

    val expectedGreater3 = TreeNode(5).apply {
        left = TreeNode(4)
    }

    runTest(
        testName = "Test 3: Target equals root",
        root = root3,
        target = 3,
        expectedSmallerTree = expectedSmaller3,
        expectedGreaterTree = expectedGreater3
    )

    /*
     * Test 4
     *
     * Original:
     *
     *          2
     *         / \
     *        1   3
     *
     * target = 10
     *
     * Expected smaller-or-equal tree:
     *
     *          2
     *         / \
     *        1   3
     *
     * Expected greater tree:
     *
     *          null
     */
    val root4 = TreeNode(2).apply {
        left = TreeNode(1)
        right = TreeNode(3)
    }

    val expectedSmaller4 = TreeNode(2).apply {
        left = TreeNode(1)
        right = TreeNode(3)
    }

    runTest(
        testName = "Test 4: Every node is smaller",
        root = root4,
        target = 10,
        expectedSmallerTree = expectedSmaller4,
        expectedGreaterTree = null
    )

    /*
     * Test 5
     *
     * Original:
     *
     *          2
     *         / \
     *        1   3
     *
     * target = 0
     *
     * Expected smaller-or-equal tree:
     *
     *          null
     *
     * Expected greater tree:
     *
     *          2
     *         / \
     *        1   3
     */
    val root5 = TreeNode(2).apply {
        left = TreeNode(1)
        right = TreeNode(3)
    }

    val expectedGreater5 = TreeNode(2).apply {
        left = TreeNode(1)
        right = TreeNode(3)
    }

    runTest(
        testName = "Test 5: Every node is greater",
        root = root5,
        target = 0,
        expectedSmallerTree = null,
        expectedGreaterTree = expectedGreater5
    )

    /*
     * Test 6
     *
     * Original:
     *
     *          null
     *
     * target = 5
     *
     * Expected:
     *
     * smaller tree = null
     * greater tree = null
     */
    runTest(
        testName = "Test 6: Empty tree",
        root = null,
        target = 5,
        expectedSmallerTree = null,
        expectedGreaterTree = null
    )

    /*
     * Test 7
     *
     * Original:
     *
     *          8
     *
     * target = 8
     *
     * Expected smaller-or-equal tree:
     *
     *          8
     *
     * Expected greater tree:
     *
     *          null
     */
    runTest(
        testName = "Test 7: Single node equals target",
        root = TreeNode(8),
        target = 8,
        expectedSmallerTree = TreeNode(8),
        expectedGreaterTree = null
    )

    /*
    * Test 8
    *
    * Original:
    *
    *                         10
    *                   /            \
    *                  5              15
    *               /     \         /    \
    *              3       8       12     18
    *             / \     / \     / \    / \
    *            1   4   6   9   11 13  17 20
    *
    * target = 8
    *
    * Expected smaller-or-equal tree:
    *
    *                  5
    *               /     \
    *              3       8
    *             / \     /
    *            1   4   6
    *
    * Expected greater tree:
    *
    *                         10
    *                        /  \
    *                       9    15
    *                          /    \
    *                         12     18
    *                        / \    / \
    *                       11 13  17 20
    *                     
    *
    */
    val root8 = TreeNode(10).apply {
        left = TreeNode(5).apply {
            left = TreeNode(3).apply {
                left = TreeNode(1)
                right = TreeNode(4)
            }
            right = TreeNode(8).apply {
                left = TreeNode(6)
                right = TreeNode(9)
            }
        }
        right = TreeNode(15).apply {
            left = TreeNode(12).apply {
                left = TreeNode(11)
                right = TreeNode(13)
            }
            right = TreeNode(18).apply {
                left = TreeNode(17)
                right = TreeNode(20)
            }
        }
    }

    val expectedSmaller8 = TreeNode(5).apply {
        left = TreeNode(3).apply {
            left = TreeNode(1)
            right = TreeNode(4)
        }
        right = TreeNode(8).apply {
            left = TreeNode(6)
        }
    }

    val expectedGreater8 = TreeNode(10).apply {
        left = TreeNode(9)
        right = TreeNode(15).apply {
            left = TreeNode(12).apply {
                left = TreeNode(11)
                right = TreeNode(13)
            }
            right = TreeNode(18).apply {
                left = TreeNode(17)
                right = TreeNode(20)
            }
        }
    }

    runTest(
        testName = "Test 8: Large balanced tree, split inside left subtree",
        root = root8,
        target = 8,
        expectedSmallerTree = expectedSmaller8,
        expectedGreaterTree = expectedGreater8
    )

    /*
    * Test 9
    *
    * Original:
    *
    *                         20
    *                   /            \
    *                 10              30
    *               /    \          /    \
    *              5      15       25     35
    *             / \    /  \     / \    / \
    *            2   7  12  18   22 27  32 40
    *               /       / \      \      /
    *              6       17 19     28    38
    *
    * target = 27
    *
    * Expected smaller-or-equal tree:
    *
    *                         20
    *                   /            \
    *                 10              25
    *               /    \          /   \
    *              5      15       22    27
    *             / \    /  \              \
    *            2   7  12  18             null
    *               /       / \
    *              6       17 19
    *
    * Expected greater tree (28 becomes the left child of 30):
    *
    *                  30
    *                 /  \
    *                28   35
    *                    /  \
    *                   32   40
    *                       /
    *                      38
    *                  
    *
    */
    val root9 = TreeNode(20).apply {
        left = TreeNode(10).apply {
            left = TreeNode(5).apply {
                left = TreeNode(2)
                right = TreeNode(7).apply {
                    left = TreeNode(6)
                }
            }
            right = TreeNode(15).apply {
                left = TreeNode(12)
                right = TreeNode(18).apply {
                    left = TreeNode(17)
                    right = TreeNode(19)
                }
            }
        }
        right = TreeNode(30).apply {
            left = TreeNode(25).apply {
                left = TreeNode(22)
                right = TreeNode(27).apply {
                    right = TreeNode(28)
                }
            }
            right = TreeNode(35).apply {
                left = TreeNode(32)
                right = TreeNode(40).apply {
                    left = TreeNode(38)
                }
            }
        }
    }

    val expectedSmaller9 = TreeNode(20).apply {
        left = TreeNode(10).apply {
            left = TreeNode(5).apply {
                left = TreeNode(2)
                right = TreeNode(7).apply {
                    left = TreeNode(6)
                }
            }
            right = TreeNode(15).apply {
                left = TreeNode(12)
                right = TreeNode(18).apply {
                    left = TreeNode(17)
                    right = TreeNode(19)
                }
            }
        }
        right = TreeNode(25).apply {
            left = TreeNode(22)
            right = TreeNode(27)
        }
    }

    val expectedGreater9 = TreeNode(30).apply {
        left = TreeNode(28)
        right = TreeNode(35).apply {
            left = TreeNode(32)
            right = TreeNode(40).apply {
                left = TreeNode(38)
            }
        }
    }

    runTest(
        testName = "Test 9: Large tree, split deep inside right subtree",
        root = root9,
        target = 27,
        expectedSmallerTree = expectedSmaller9,
        expectedGreaterTree = expectedGreater9
    )

    /*
    * Test 10
    *
    * Original:
    *
    *                         50
    *                   /            \
    *                 25              75
    *               /    \          /    \
    *             10      40       60      90
    *            / \     /  \     /  \    / \
    *           5  15   30  45   55  65  80 95
    *               \       /       \     /
    *               20     42       70   78
    *
    * target = 50
    *
    * Expected smaller-or-equal tree:
    *
    *                         50
    *                        /
    *                      25
    *                    /    \
    *                  10      40
    *                 / \     /  \
    *                5  15   30  45
    *                    \       /
    *                    20     42
    *
    * Expected greater tree:
    *
    *                       75
    *                     /    \
    *                   60      90
    *                  /  \    / \
    *                 55  65  80 95
    *                       \  /
    *                       70 78
    */
    val root10 = TreeNode(50).apply {
        left = TreeNode(25).apply {
            left = TreeNode(10).apply {
                left = TreeNode(5)
                right = TreeNode(15).apply {
                    right = TreeNode(20)
                }
            }
            right = TreeNode(40).apply {
                left = TreeNode(30)
                right = TreeNode(45).apply {
                    left = TreeNode(42)
                }
            }
        }
        right = TreeNode(75).apply {
            left = TreeNode(60).apply {
                left = TreeNode(55)
                right = TreeNode(65).apply {
                    right = TreeNode(70)
                }
            }
            right = TreeNode(90).apply {
                left = TreeNode(80).apply {
                    left = TreeNode(78)
                }
                right = TreeNode(95)
            }
        }
    }

    val expectedSmaller10 = TreeNode(50).apply {
        left = TreeNode(25).apply {
            left = TreeNode(10).apply {
                left = TreeNode(5)
                right = TreeNode(15).apply {
                    right = TreeNode(20)
                }
            }
            right = TreeNode(40).apply {
                left = TreeNode(30)
                right = TreeNode(45).apply {
                    left = TreeNode(42)
                }
            }
        }
    }

    val expectedGreater10 = TreeNode(75).apply {
        left = TreeNode(60).apply {
            left = TreeNode(55)
            right = TreeNode(65).apply {
                right = TreeNode(70)
            }
        }
        right = TreeNode(90).apply {
            left = TreeNode(80).apply {
                left = TreeNode(78)
            }
            right = TreeNode(95)
        }
    }

    runTest(
        testName = "Test 10: Large tree, target equals root",
        root = root10,
        target = 50,
        expectedSmallerTree = expectedSmaller10,
        expectedGreaterTree = expectedGreater10
    )

    /*
    * Test 11
    *
    * Original:
    *
    *          5
    *           \
    *            10
    *              \
    *               15
    *                 \
    *                  20
    *                    \
    *                     25
    *                       \
    *                        30
    *                          \
    *                           35
    *
    * target = 22
    *
    * Expected smaller-or-equal tree:
    *
    *          5
    *           \
    *            10
    *              \
    *               15
    *                 \
    *                  20
    *
    * Expected greater tree:
    *
    *          25
    *            \
    *             30
    *               \
    *                35
    */
    val root11 = TreeNode(5).apply {
        right = TreeNode(10).apply {
            right = TreeNode(15).apply {
                right = TreeNode(20).apply {
                    right = TreeNode(25).apply {
                        right = TreeNode(30).apply {
                            right = TreeNode(35)
                        }
                    }
                }
            }
        }
    }

    val expectedSmaller11 = TreeNode(5).apply {
        right = TreeNode(10).apply {
            right = TreeNode(15).apply {
                right = TreeNode(20)
            }
        }
    }

    val expectedGreater11 = TreeNode(25).apply {
        right = TreeNode(30).apply {
            right = TreeNode(35)
        }
    }

    runTest(
        testName = "Test 11: Large right-skewed tree",
        root = root11,
        target = 22,
        expectedSmallerTree = expectedSmaller11,
        expectedGreaterTree = expectedGreater11
    )

    /*
    * Test 12
    *
    * Original:
    *
    *                         40
    *                       /
    *                     35
    *                   /
    *                 30
    *               /
    *             25
    *           /
    *         20
    *       /
    *     15
    *    /
    *   10
    *
    * target = 24
    *
    * Expected smaller-or-equal tree:
    *
    *          20
    *         /
    *        15
    *       /
    *      10
    *
    * Expected greater tree:
    *
    *                         40
    *                       /
    *                     35
    *                   /
    *                 30
    *               /
    *             25
    */
    val root12 = TreeNode(40).apply {
        left = TreeNode(35).apply {
            left = TreeNode(30).apply {
                left = TreeNode(25).apply {
                    left = TreeNode(20).apply {
                        left = TreeNode(15).apply {
                            left = TreeNode(10)
                        }
                    }
                }
            }
        }
    }

    val expectedSmaller12 = TreeNode(20).apply {
        left = TreeNode(15).apply {
            left = TreeNode(10)
        }
    }

    val expectedGreater12 = TreeNode(40).apply {
        left = TreeNode(35).apply {
            left = TreeNode(30).apply {
                left = TreeNode(25)
            }
        }
    }

    runTest(
        testName = "Test 12: Large left-skewed tree",
        root = root12,
        target = 24,
        expectedSmallerTree = expectedSmaller12,
        expectedGreaterTree = expectedGreater12
    )

    /*
    * Test 13
    *
    * Original:
    *
    *                         16
    *                   /            \
    *                  8              24
    *               /     \        /     \
    *              4      12      20      28
    *             / \    /  \    /  \    /  \
    *            2   6  10  14  18  22  26  30
    *           / \ / \ / \ / \ / \ / \ / \ / \
    *          1 3 5 7 9 11 13 15 17 19 21 23 25 27 29 31
    *
    * target = 21
    *
    * Expected smaller-or-equal tree:
    *
    *                         16
    *                   /            \
    *                  8              20
    *               /     \         /  \
    *              4      12       18   21
    *             / \    /  \     / \
    *            2   6  10  14   17 19
    *           / \ / \ / \ / \
    *          1 3 5 7 9 11 13 15
    *
    * Expected greater tree:
    *
    *                         24
    *                       /    \
    *                     22      28
    *                       \    /  \
    *                       23  26  30
    *                          / \  / \
    *                         25 27 29 31
    */
    val root13 = TreeNode(16).apply {
        left = TreeNode(8).apply {
            left = TreeNode(4).apply {
                left = TreeNode(2).apply {
                    left = TreeNode(1)
                    right = TreeNode(3)
                }
                right = TreeNode(6).apply {
                    left = TreeNode(5)
                    right = TreeNode(7)
                }
            }
            right = TreeNode(12).apply {
                left = TreeNode(10).apply {
                    left = TreeNode(9)
                    right = TreeNode(11)
                }
                right = TreeNode(14).apply {
                    left = TreeNode(13)
                    right = TreeNode(15)
                }
            }
        }

        right = TreeNode(24).apply {
            left = TreeNode(20).apply {
                left = TreeNode(18).apply {
                    left = TreeNode(17)
                    right = TreeNode(19)
                }
                right = TreeNode(22).apply {
                    left = TreeNode(21)
                    right = TreeNode(23)
                }
            }
            right = TreeNode(28).apply {
                left = TreeNode(26).apply {
                    left = TreeNode(25)
                    right = TreeNode(27)
                }
                right = TreeNode(30).apply {
                    left = TreeNode(29)
                    right = TreeNode(31)
                }
            }
        }
    }

    val expectedSmaller13 = TreeNode(16).apply {
        left = TreeNode(8).apply {
            left = TreeNode(4).apply {
                left = TreeNode(2).apply {
                    left = TreeNode(1)
                    right = TreeNode(3)
                }
                right = TreeNode(6).apply {
                    left = TreeNode(5)
                    right = TreeNode(7)
                }
            }
            right = TreeNode(12).apply {
                left = TreeNode(10).apply {
                    left = TreeNode(9)
                    right = TreeNode(11)
                }
                right = TreeNode(14).apply {
                    left = TreeNode(13)
                    right = TreeNode(15)
                }
            }
        }

        right = TreeNode(20).apply {
            left = TreeNode(18).apply {
                left = TreeNode(17)
                right = TreeNode(19)
            }
            right = TreeNode(21)
        }
    }

    val expectedGreater13 = TreeNode(24).apply {
        left = TreeNode(22).apply {
            right = TreeNode(23)
        }
        right = TreeNode(28).apply {
            left = TreeNode(26).apply {
                left = TreeNode(25)
                right = TreeNode(27)
            }
            right = TreeNode(30).apply {
                left = TreeNode(29)
                right = TreeNode(31)
            }
        }
    }

    runTest(
        testName = "Test 13: Complete 31-node tree, deep internal split",
        root = root13,
        target = 21,
        expectedSmallerTree = expectedSmaller13,
        expectedGreaterTree = expectedGreater13
    )
}