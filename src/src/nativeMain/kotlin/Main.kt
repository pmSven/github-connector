import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

object App : KoinComponent {
    private val inputParser : InputParser by inject()

    fun run(args: Array<String>) {
        val reviewData = inputParser.parse(args)
        println(reviewData)
    }
}

fun main(args: Array<String>) {
    startKoin {
        modules(appModule)
    }
    App.run(args)
}