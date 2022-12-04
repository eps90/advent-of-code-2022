fun <T> List<T>.destructed() = Pair(first(), last())
fun main() {
    fun prepareSections(input: List<String>): List<Pair<IntRange, IntRange>> {
        return input.map { line ->
            line.split(",")
                .map { s -> s.split("-").let { IntRange(it[0].toInt(), it[1].toInt()) } }
                .destructed()
        }
    }

    fun part1(input: List<String>): Int {
        return prepareSections(input).count { (firstGroup, secondGroup) ->
            firstGroup.all { it in secondGroup } || secondGroup.all { it in firstGroup }
        }
    }

    fun part2(input: List<String>): Int {
        return prepareSections(input).count { (firstGroup, secondGroup) ->
            firstGroup.any { it in secondGroup }
        }
    }

    val testInput = readInput("Day04_test")
    println(part1(testInput))
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
