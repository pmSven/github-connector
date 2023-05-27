package input

import InputParser
import ReviewInput
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.output.CliktHelpFormatter
import com.github.ajalt.clikt.parameters.arguments.*
import com.github.ajalt.clikt.parameters.options.*
import kotlinx.datetime.*

class PrReviewCommand : CliktCommand(name="PR Reviews"), InputParser {
    private val after: Instant by option("-a", "--after", help="Count PR Reviews after this date")
        .convert {
            try {
                Instant.parse(it)
            } catch(e: Exception) {
                fail("Please use a date in the format of yyyy-mm-ddThh-mm-ssZ")
            }
        }
        .prompt("The date time to start counting from", default = "2023-04-27T00:00:00Z")

    private val token: String by option("-t", "--token", help="The Github token")
        .prompt("The Github token to use")

    private val accountNames: Set<String> by argument()
        .multiple()
        .unique()

    init {
        versionOption("1.0")
        context {
            helpFormatter = CliktHelpFormatter(showDefaultValues = true, showRequiredTag = true)
        }
    }

    override fun run() {}

    override fun parse(args: Array<String>) : ReviewInput {
        main(args)
        return ReviewInput(
            accountNames = accountNames,
            after = after.toLocalDateTime(TimeZone.currentSystemDefault()),
            token = token
        )
    }
}