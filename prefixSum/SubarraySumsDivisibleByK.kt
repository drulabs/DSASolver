/*
 * SUBARRAY SUMS DIVISIBLE BY K
 *
 * Given an integer array nums and an integer k,
 * return the number of non-empty contiguous subarrays
 * whose sum is divisible by k.
 *
 * Constraints:
 * - 1 <= nums.size <= 30_000
 * - -10_000 <= nums[i] <= 10_000
 * - 2 <= k <= 10_000
 */

fun solve(nums: IntArray, k: Int): Int {
    val map = mutableMapOf<Int, Int>()
    map[0] = 1
    var sum = 0
    var count = 0
    
    for (i in nums.indices) {
        sum += nums[i]
        val remainder = (((sum % k) + k) % k)

        if (map.containsKey(remainder)) {
            count += map[remainder] ?: 0
        }
        map[remainder] = (map[remainder] ?: 0) + 1
    }

    return count
}

fun main() {
    val tests = listOf(
        Triple(
            intArrayOf(4, 5, 0, -2, -3, 1),
            5,
            7
        ),
        Triple(
            intArrayOf(5),
            9,
            0
        ),
        Triple(
            intArrayOf(5),
            5,
            1
        ),
        Triple(
            intArrayOf(0, 0, 0),
            5,
            6
        ),
        Triple(
            intArrayOf(2, 4, 6),
            2,
            6
        ),
        Triple(
            intArrayOf(1, 2, 3),
            3,
            3
        ),
        Triple(
            intArrayOf(-1, 2, 9),
            2,
            2
        ),
        Triple(
            intArrayOf(7, -7, 7, -7),
            7,
            10
        ),
        Triple(
            intArrayOf(3, 1, 2, 7, 4),
            6,
            1
        ),
        Triple(
            intArrayOf(-5, -10, 15),
            5,
            6
        )
    )

    tests.forEachIndexed { index, (nums, k, expected) ->
        val actual = solve(nums, k)

        if (actual == expected) {
            println("Test ${index + 1}: PASS ✅. expected $expected, output = $actual")
        } else {
            println(
                "Test ${index + 1}: FAIL ❌. expected $expected, output = $actual"
            )
        }
    }
}