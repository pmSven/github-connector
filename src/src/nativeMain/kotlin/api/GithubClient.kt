package api

import GraphQLRequest
import GraphQLResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.curl.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.*
import kotlinx.serialization.json.Json

object GithubClient {
    suspend inline fun <reified Response> request(request: GraphQLRequest, token: String): GraphQLResponse<Response> {
        val client = HttpClient(Curl)
        val response: HttpResponse = client.post("https://api.github.com/graphql") {
            headers {
                append("Authorization",  "bearer $token")
            }
            setBody(Json.encodeToString(request))
        }
        val result = Json.decodeFromString<GraphQLResponse<Response>>(response.body())
        client.close()
        return result
    }
}