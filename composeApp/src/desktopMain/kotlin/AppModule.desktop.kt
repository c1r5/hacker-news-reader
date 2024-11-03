import dev.eric.hnreader.models.HackerNewsService
import dev.eric.hnreader.network.createHttpClient
import dev.eric.hnreader.services.HackerNewsServiceImpl
import dev.eric.hnreader.viewmodels.HackerNewsViewModel
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module

actual val appModule: Module = module {
    single { createHttpClient(OkHttp.create()) }
    single<HackerNewsService> { HackerNewsServiceImpl(get()) }
    factory { HackerNewsViewModel(get()) }
}