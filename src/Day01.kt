fun main() {
    fun caloriesPerElf(input: List<String>): List<Int> {
        return input
            .fold(mutableListOf()) { acc, s ->
                if (s.isEmpty()) {
                    acc.add(0)
                    acc
                } else {
                    val lastElem = acc.lastOrNull() ?: 0
                    val newAcc = acc.dropLast(1).toMutableList()
                    newAcc.add(lastElem + s.toInt())
                    newAcc
                }
            }
    }

    fun part1(input: List<String>): Int {
        return caloriesPerElf(input).max()
    }

    fun part2(input: List<String>): Int {
        return caloriesPerElf(input).sortedDescending().take(3).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
