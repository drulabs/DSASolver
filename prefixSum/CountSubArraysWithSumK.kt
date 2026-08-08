/*
 * COUNT SUBARRAYS WITH SUM K
 *
 * Given an integer array nums and an integer k, return the total number
 * of continuous subarrays whose sum equals k.
 *
 * A subarray is a contiguous, non-empty sequence of elements within an array.
 *
 * Constraints:
 * - 1 <= nums.size <= 20_000
 * - -1000 <= nums[i] <= 1000
 * - -10^7 <= k <= 10^7
 */

fun solve(nums: IntArray, k: Int): Int {
    val prefix = mutableMapOf<Int, Int>()
    prefix[0] = 1

    var sum = 0
    var count = 0

    for (i in nums.indices) {
        sum += nums[i]
        if (prefix.containsKey(sum - k)) {
            count += (prefix[sum - k] ?: 0)
        }
        prefix[sum] = (prefix[sum] ?: 0) + 1
    }

    return count
}

fun main() {
    val tests = listOf(
        Triple(
            intArrayOf(1, 1, 1),
            2,
            2
        ),
        Triple(
            intArrayOf(1, 2, 3),
            3,
            2
        ),
        Triple(
            intArrayOf(1, -1, 0),
            0,
            3
        ),
        Triple(
            intArrayOf(3, 4, 7, 2, -3, 1, 4, 2),
            7,
            4
        ),
        Triple(
            intArrayOf(-1, -1, 1),
            0,
            1
        ),
        Triple(
            intArrayOf(5),
            5,
            1
        ),
        Triple(
            intArrayOf(5),
            10,
            0
        ),
        Triple(
            intArrayOf(0, 0, 0),
            0,
            6
        ),
        Triple(
            intArrayOf(3, 5, 2, 10, 10, 7, 3),
            10,
            4
        )
    )

    tests.forEachIndexed { index, (nums, k, expected) ->
        val actual = solve(nums, k)

        println(
            "Test ${index + 1}: " +
                if (actual == expected) {
                    "PASS"
                } else {
                    "FAIL | expected=$expected, actual=$actual"
                }
        )
    }
}