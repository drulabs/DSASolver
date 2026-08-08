/*
 * SHORTEST SUBARRAY WITH SUM K
 *
 * Given an integer array nums and an integer k,
 * return the length of the shortest non-empty contiguous
 * subarray whose elements sum exactly to k.
 *
 * Return 0 if no such subarray exists.
 *
 * Constraints:
 * - 1 <= nums.size <= 20_000
 * - -1000 <= nums[i] <= 1000
 * - -10^7 <= k <= 10^7
 */

fun solve(nums: IntArray, k: Int): Int {
        val prefix = mutableMapOf<Int, Int>()
    prefix[0] = -1
    var sum = 0
    var shortest = Int.MAX_VALUE

    for (i in nums.indices) {
        sum += nums[i]
        if (prefix.containsKey(sum - k)) {
            val current = i - (prefix[sum - k] ?: 0)
            shortest = minOf(shortest, current)
        }
        prefix[sum] = i
    }

    return if (shortest == Int.MAX_VALUE) 0 else shortest
}

fun main() {
    val tests = listOf(
        Triple(
            intArrayOf(1, 2, 3, 1, 1),
            3,
            1
        ),
        Triple(
            intArrayOf(1, -1, 5, -2, 3),
            3,
            1
        ),
        Triple(
            intArrayOf(-2, -1, 2, 1),
            1,
            1
        ),
        Triple(
            intArrayOf(3, 4, 7, 2, -3, 1, 4, 2),
            7,
            1
        ),
        Triple(
            intArrayOf(1, 2, 3),
            10,
            0
        ),
        Triple(
            intArrayOf(5),
            5,
            1
        ),
        Triple(
            intArrayOf(0, 0, 0, 0),
            0,
            1
        ),
        Triple(
            intArrayOf(2, -2, 2, -2, 2),
            0,
            2
        ),
        Triple(
            intArrayOf(10, 5, 2, 7, 1, 9),
            15,
            2
        ),
        Triple(
            intArrayOf(4, -1, -1, 2, 3),
            3,
            1
        )
    )

    tests.forEachIndexed { index, (nums, k, expected) ->
        val actual = solve(nums, k)

        if (actual == expected) {
            println("Test ${index + 1}: PASS ✅")
        } else {
            println(
                "Test ${index + 1}: FAIL ❌. expected $expected, output = $actual"
            )
        }
    }
}