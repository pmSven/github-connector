import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

object App : KoinComponent {
    private val inputParser : InputParser by inject()
    private val reviewApi : ReviewAPI by inject()

    fun run(args: Array<String>) {
        runBlocking {
            val input = inputParser.parse(args)
            val apiResult = reviewApi.get(input)
            println(input)
            println(apiResult)
        }
    }
}

fun main(args: Array<String>) {
    startKoin {
        modules(appModule)
    }
    App.run(args)
}