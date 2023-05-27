import kotlinx.serialization.Serializable

@Serializable
data class GraphQLRequest(
    val query: String,
    val variables: Map<String, String> = mapOf()
)

@Serializable
data class GraphQLResponse<T>(
    val data: T,
    val errors: List<String> = listOf()
)

interface ReviewAPI {
    suspend fun get(reviewInput: ReviewInput): Map<String, Int>
}