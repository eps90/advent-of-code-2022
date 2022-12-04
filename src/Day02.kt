import java.security.InvalidParameterException

enum class FigureType(val score: Int) {
    Rock(1),
    Paper(2),
    Scissors(3),
}

enum class MatchResult(val score: Int) {
    Loss(0),
    Draw(3),
    Win(6);

    fun opposite(): MatchResult {
        return when (this) {
            Loss -> Win
            Win -> Loss
            else -> Draw
        }
    }

    companion object {
        fun of(code: String): MatchResult {
            val scoresMapping = mapOf(
                "X" to Loss,
                "Y" to Draw,
                "Z" to Win
            )
            return scoresMapping[code] ?: throw InvalidParameterException()
        }
    }
}

typealias ScoreMap = Map<MatchResult, FigureType>
fun ScoreMap.reversed() =  entries.associateBy({ it.value }) { it.key }
sealed class Figure(val type: FigureType, val scoreMap: ScoreMap) {
    object Rock : Figure(
        FigureType.Rock,
        mapOf(
            MatchResult.Win to FigureType.Scissors,
            MatchResult.Loss to FigureType.Paper,
            MatchResult.Draw to FigureType.Rock
        )
    )
    object Paper : Figure(
        FigureType.Paper, mapOf(
            MatchResult.Win to FigureType.Rock,
            MatchResult.Loss to FigureType.Scissors,
            MatchResult.Draw to FigureType.Paper
        )
    )

    object Scissors : Figure(
        FigureType.Scissors, mapOf(
            MatchResult.Win to FigureType.Paper,
            MatchResult.Loss to FigureType.Rock,
            MatchResult.Draw to FigureType.Scissors
        )
    )

    fun matchResultWith(other: Figure): MatchResult {
        return scoreMap.reversed()[other.type] ?: throw InvalidParameterException()
    }

    companion object {
        fun of(char: String): Figure {
            val figuresMapping = mapOf(
                "A" to Rock,
                "B" to Paper,
                "C" to Scissors,
                "X" to Rock,
                "Y" to Paper,
                "Z" to Scissors
            )
            return figuresMapping[char] ?: throw InvalidParameterException()
        }
    }
}

private fun <T> List<T>.destruct() = Pair(first(), last())

fun main() {
    fun part1(input: List<String>): Int {
        return input.asSequence().map { line ->
            val (opponent, santa) = line.split(" ").map { Figure.of(it) }.destruct()
            val battleResult = santa.matchResultWith(opponent)
            val finalScore = santa.type.score + battleResult.score
            finalScore
        }.sum()
    }

    fun part2(input: List<String>): Int {
        return input.asSequence().map { line ->
            val (opponentCode, matchResultCode) = line.split(" ").destruct()
            val opponent = Figure.of(opponentCode)
            val matchResult = MatchResult.of(matchResultCode)
            val santaFigure = opponent.scoreMap[matchResult.opposite()]
            val result = matchResult.score + (santaFigure?.score ?: 0)
            result
        }.sum()
    }

    val testInput = readInput("Day02_test")
    println(part1(testInput))
    check(part1(testInput) == 15)

    println(part2(testInput))
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
