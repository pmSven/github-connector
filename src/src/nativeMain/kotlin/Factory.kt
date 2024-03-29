import input.PrReviewCommand
import api.GithubReviewApi
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    singleOf(::PrReviewCommand) bind InputParser::class
    singleOf(::GithubReviewApi) bind ReviewAPI::class
}