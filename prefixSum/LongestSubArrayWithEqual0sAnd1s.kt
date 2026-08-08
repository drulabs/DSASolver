/*
 * LONGEST CONTIGUOUS SUBARRAY WITH EQUAL 0s AND 1s
 *
 * Given a binary array nums containing only 0s and 1s,
 * return the length of the longest contiguous subarray
 * containing an equal number of 0s and 1s.
 *
 * Constraints:
 * - 1 <= nums.size <= 50_000
 * - nums[i] is either 0 or 1
 */

fun solve(nums: IntArray): Int {
    val prefix = mutableMapOf<Int, Int>()
    prefix[0] = -1
    var longest = 0
    var sum = 0

    for (i in nums.indices) {
        sum += if (nums[i] == 1) 1 else -1
        if (prefix.containsKey(sum)) {
            val len = i - (prefix[sum] ?: 0)
            longest = maxOf(longest, len)
        }
        prefix[sum] = (prefix[sum] ?: i)
    }

    return longest
}

fun main() {
    val tests = listOf(
        intArrayOf(0, 1) to 2,

        intArrayOf(0, 1, 0) to 2,

        intArrayOf(0, 1, 1, 0) to 4,

        intArrayOf(0, 0, 1, 0, 0, 0, 1, 1) to 6,

        intArrayOf(1, 1, 1, 0, 0, 0) to 6,

        intArrayOf(0, 0, 0, 1, 1) to 4,

        intArrayOf(1, 1, 1, 1) to 0,

        intArrayOf(0, 0, 0, 0) to 0,

        intArrayOf(1, 0, 1, 0, 1, 0, 1, 0) to 8,

        intArrayOf(1) to 0,

        intArrayOf(0) to 0,

        intArrayOf(1, 1, 0, 1, 0, 0, 1, 0) to 8
    )

    tests.forEachIndexed { index, (nums, expected) ->
        val actual = solve(nums)

        if (actual == expected) {
            println("Test ${index + 1}: PASS ✅")
        } else {
            println(
                "Test ${index + 1}: FAIL ❌. expected $expected, output = $actual"
            )
        }
    }
}