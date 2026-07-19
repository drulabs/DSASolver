import java.util.*

fun main() {
    // count the number of sub arrays that add up to a target

    val inputs = listOf(
        intArrayOf(1, -1, 5, -2, 3, 2, -2) to 5,
        intArrayOf(1, 1, 1) to 2,
        intArrayOf(1, 2, 3) to 3,
        intArrayOf(3, 4, 7, 2, -3, 1, 4, 2) to 7,
        intArrayOf(-1, -1, 1) to 0,
        intArrayOf(0, 0, 0) to 0
    )

    val outputs = listOf(5, 2, 2, 4, 1, 6)

    println("-------------------------------------")
    for (i in inputs.indices) {
        val (input, target) = inputs[i]
        val expected = outputs[i]
        println("Input => ${input.joinToString(", ")}, target = $target")
        println("Expected => $expected")
        val result = solve(input, target)
        println("your solution => $result......${ if (result == expected) "✅" else "❌" }")
        println("\n-------------------------------------")
    }
}

private fun solve(nums: IntArray, target: Int): Int {
    val map = mutableMapOf<Int, Int>()
    map[0] = 1
    var count = 0
    var sum = 0

    for (i in nums.indices) {
        sum += nums[i]

        if (map.containsKey(sum-target)) {
            count += map[sum-target]!!
        }
         
        map[sum] = (map[sum] ?: 0) + 1
    }

    return count
}