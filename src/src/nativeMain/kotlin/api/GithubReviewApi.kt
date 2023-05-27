package api

import GraphQLRequest
import GraphQLResponse
import ReviewAPI
import ReviewInput
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.curl.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

@Serializable
data class Foo(
    val issueCount: Int
)

@Serializable
data class ReviewResult(
    val search: Foo
)


class GithubReviewApi: ReviewAPI {
    private val requestQuery = """query (${'$'}search: String!) {
        search(query: ${'$'}search, type: ISSUE, first: 1) {
            issueCount
        }
    }"""

    override suspend fun get(reviewInput: ReviewInput): Map<String, Int> {
        return reviewInput.accountNames.associateWith {
            GithubClient.request<ReviewResult>(
                GraphQLRequest(
                    query = requestQuery,
                    variables = mapOf(
                        "search" to "is:pr reviewed-by:${it} created:>=${this.getDate(reviewInput.after)}"
                    )
                ), reviewInput.token
            ).data.search.issueCount
        }
    }

    private fun getDate(dateTime: LocalDateTime): String {
        return "${dateTime.year}-${dateTime.monthNumber.toString().padStart(2, '0')}-${dateTime.dayOfMonth.toString().padStart(2, '0')}"
    }
}