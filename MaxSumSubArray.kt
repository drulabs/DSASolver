fun main() {
    val inputs = listOf(
        listOf(-2, 1, -3, 4, -1, 2, 1, -5, 4), // Classic case: Max subarray is [4, -1, 2, 1]
        listOf(1),                             // Single element
        listOf(5, 4, -1, 7, 8),                // All positive except one
        listOf(-1, -2, -3, -4),                // All negative (Max is the least negative)
        listOf(0, 0, 0)
    )

    val outputs = listOf(
        6, 1, 23, -1, 0
    )

    println("--------------------------------------")
    for (i in inputs.indices) {
        println("Input => ${inputs[i].joinToString(", ")}")
        val result = solve(inputs[i])
        println("Result = $result, expected = ${outputs[i]}")
        println("\n--------------------------------------")
    }
    
}

private fun solve(nums: List<Int>): Int {

    var maxSum = -1001
    var current = -1001

    for (i in nums.indices) {
        current = maxOf(nums[i], current + nums[i])
        maxSum = maxOf(maxSum, current)
    }

    return maxSum
}
