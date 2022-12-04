import kotlin.math.ceil

fun String.splitInHalf(): List<String> = chunked(ceil(length.toDouble() / 2).toInt())

private val priorities = (('a'..'z') + ('A'..'Z')).withIndex().associateBy({ it.value }) { it.index + 1 }
fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val (one, two) = line.splitInHalf().map { it.toSortedSet() }
            one.intersect(two).first().let { priorities[it] ?: 0 }
        }
    }

    fun part2(input: List<String>): Int {
        return input.chunked(3).sumOf { group ->
            group.map { it.toSet() }.reduce { acc, chars ->
                acc.intersect(chars)
            }.first().let { priorities[it] ?: 0 }
        }
    }

    val testInput = readInput("Day03_test")
    println(part1(testInput))
    check(part1(testInput) == 157)
    println(part2(testInput))
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
