private fun <T> ArrayDeque<T>.push(element: T) = addLast(element)

private fun <T> ArrayDeque<T>.push(elements: Collection<T>) = addAll(elements)
private fun <T> ArrayDeque<T>.pop() = removeLast()
private fun <T> ArrayDeque<T>.peek() = lastOrNull()

fun main() {
    val componentRegex = """\s?(\[?(\w|\d)]?| {3})""".toRegex()
    val instructionRegex = """move (\d+) from (\d+) to (\d+)""".toRegex()

    fun parseStacks(input: List<String>, untilRow: Int): MutableMap<Int, ArrayDeque<String>> {
        val rows = input
            .take(untilRow)
            .map { line ->
                componentRegex
                    .findAll(line)
                    .map { it.groupValues[2] }
                    .toList()
            }
        val numberOfStacks = rows.last().last().toInt()
        val onlyRows = rows.dropLast(1)
        return (1..numberOfStacks).associateWith { stackNo ->
            ArrayDeque(onlyRows.reversed().mapNotNull { row ->
                row.getOrNull(stackNo - 1)?.takeIf { it.isNotEmpty() }
            })
        }.toMutableMap()
    }

    fun part1(input: List<String>): String {
        val stacksLimit = input.indexOf("")
        val stacks = parseStacks(input, stacksLimit)

        input
            .filter { it.startsWith("move") }
            .forEach { instruction ->
                val instructionsParsed = instructionRegex.find(instruction)
                if (instructionsParsed != null) {
                    val (noOfElems, from, to) = instructionsParsed.destructured
                    repeat(noOfElems.toInt()) {
                        val nextElem = stacks[from.toInt()]!!.pop()
                        stacks[to.toInt()]!!.push(nextElem)
                    }
                }
            }

        return stacks.mapNotNull {(_, value) ->
            value.peek()
        }.joinToString(separator = "")
    }

    fun part2(input: List<String>): String {
        val stacksLimit = input.indexOf("")
        val stacks = parseStacks(input, stacksLimit)

        input
            .filter { it.startsWith("move") }
            .forEach { instruction ->
                val instructionsParsed = instructionRegex.find(instruction)
                if (instructionsParsed != null) {
                    val (noOfElems, from, to) = instructionsParsed.destructured
                    val elToRemove = stacks[from.toInt()]!!.takeLast(noOfElems.toInt())
                    repeat(noOfElems.toInt()) { stacks[from.toInt()]!!.pop() }

                    stacks[to.toInt()]!!.push(elToRemove)
                }
            }

        return stacks.mapNotNull {(_, value) ->
            value.peek()
        }.joinToString(separator = "")
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    println(part2(testInput))
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
