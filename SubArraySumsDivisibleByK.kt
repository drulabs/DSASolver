fun main() {
    // Problem: Find the total number of non-empty subarrays that have 
    // a sum divisible by k.
    
    val inputs = listOf(
        listOf(4, 5, 0, -2, -3, 1), 
        listOf(5),
        listOf(2, -2, 2, -2)
    )
    val ks = listOf(5, 9, 2)
    val expected = listOf(7, 0, 10) 

    println("--------------------------------------")
    for (i in inputs.indices) {
        println("Input => ${inputs[i]}, k => ${ks[i]}")
        val result = solve(inputs[i], ks[i])
        val status = if (result == expected[i]) "✅" else "❌"
        println("Result = $result, Expected = ${expected[i]} - $status")
        println("\n--------------------------------------")
    }
}

private fun solve(nums: List<Int>, k: Int): Int {
    val map = mutableMapOf<Int, Int>()
    map[0] = 1
    var count = 0
    var sum = 0

    for (i in nums.indices) {
        sum += nums[i]
        val remainder = ((sum % k) + k) % k
        if (map.containsKey(remainder)) {
            count += map[remainder]!!
        }
        map[remainder] = (map[remainder] ?: 0) + 1
    }

    return count
}