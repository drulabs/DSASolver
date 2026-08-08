/*
 * LONGEST SUBSTRING WITH EVEN COUNTS OF ALL VOWELS
 *
 * Given a string s, return the length of the longest substring
 * where every vowel appears an even number of times.
 *
 * Vowels are:
 * - a
 * - e
 * - i
 * - o
 * - u
 *
 * Consonants do not affect the condition.
 *
 * Constraints:
 * - 1 <= s.length <= 500_000
 * - s contains only lowercase English letters
 */

fun solve(s: String): Int {
    val mask = mapOf('a' to 1, 'e' to 2, 'i' to 4, 'o' to 8, 'u' to 16)
    val map = mutableMapOf<Int, Int>()
    map[0] = -1
    var longest = 0
    var runningXor = 0

    for (i in s.indices) {
        val char = s[i]
        runningXor = runningXor xor (mask[char] ?: 0)

        if (map.containsKey(runningXor)) {
            val len = i - map[runningXor]!!
            longest = maxOf(longest, len)
        }

        map[runningXor] = map[runningXor] ?: i
    }

    return longest
}

fun main() {
    val tests = listOf(
        "eleetminicoworoep" to 13,
        "leetcodeisgreat" to 5,
        "bcbcbc" to 6,
        "aeiou" to 0,
        "aaaa" to 4,
        "aaa" to 2,
        "aabb" to 4,
        "abcde" to 3,
        "aeae" to 4,
        "uoieauoiea" to 10,
        "xaxexixoxu" to 1,
        "zzzaeizzzuoou" to 7
    )

    tests.forEachIndexed { index, (s, expected) ->
        val actual = solve(s)

        if (actual == expected) {
            println(
                "Test ${index + 1}: PASS ✅. expected $expected, output = $actual"
            )
        } else {
            println(
                "Test ${index + 1}: FAIL ❌. expected $expected, output = $actual"
            )
        }
    }
}