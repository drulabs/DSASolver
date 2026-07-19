fun main() {
    // Problem: Find the pivot index where the sum of elements to the left
    // is equal to the sum of elements to the right. 
    // Return -1 if no such index exists.
    
    val inputs = listOf(
        listOf(1, 7, 3, 6, 5, 6), // Pivot is index 3 (left: 1+7+3=11, right: 5+6=11)
        listOf(1, 2, 3),          // No pivot index, return -1
        listOf(2, 1, -1)          // Pivot is index 0 (left: 0, right: 1-1=0)
    )
    val expected = listOf(3, -1, 0)

    println("--------------------------------------")
    for (i in inputs.indices) {
        println("Input => ${inputs[i]}")
        val result = solve(inputs[i])
        val status = if (result == expected[i]) "✅" else "❌"
        println("Result = $result, Expected = ${expected[i]} - $status")
        println("\n--------------------------------------")
    }
}

private fun solve(nums: List<Int>): Int {
    val total = nums.sum()
    var leftSum = 0
    var rightSum = total

    for (i in nums.indices) {
        rightSum -= nums[i]
        if (leftSum == rightSum) {
            return i
        }
        leftSum += nums[i]
    }
    
    return -1
}