import kotlinx.datetime.LocalDateTime

data class ReviewInput(
    val accountNames: Iterable<String>,
    val after: LocalDateTime,
    val token: String
)

interface InputParser {
    fun parse(args: Array<String>): ReviewInput
}