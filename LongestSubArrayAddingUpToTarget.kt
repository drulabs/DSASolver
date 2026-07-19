fun main() {
    // Problem: Find the length of the longest subarray that sums up to a target value.
    // Inputs: A list of integers and a target integer.
    
    val inputs = listOf(
        listOf(1, -1, 5, -2, 3), 
        listOf(-2, -1, 2, 1),
        listOf(1, 2, 3, 4, 5)
    )
    val targets = listOf(3, 1, 15)
    val expected = listOf(4, 2, 5) // 4 for [1, -1, 5, -2], 2 for [-1, 2], 5 for [1, 2, 3, 4, 5]

    println("--------------------------------------")
    for (i in inputs.indices) {
        println("Input => ${inputs[i]}, Target => ${targets[i]}")
        val result = solve(inputs[i], targets[i])
        val status = if (result == expected[i]) "✅" else "❌"
        println("Result = $result, Expected = ${expected[i]} - $status")
        println("\n--------------------------------------")
    }
}

private fun solve(nums: List<Int>, target: Int): Int {
    val map = mutableMapOf<Int, Int>()
    map[0] = -1
    var maxLen = 0
    var sum = 0

    for (i in nums.indices) {
        sum += nums[i]

        if(map.containsKey(sum - target)) {
            val left = map[sum - target]!!
            val length = i - left
            maxLen = maxOf(maxLen, length)
        }
        map[sum] = map[sum] ?: i
    }

    return maxLen
}