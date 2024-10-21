import data.HackerNewsClient
import data.HackerNewsRepository
import dev.eric.hnreader.network.createHttpClient
import dev.eric.hnreader.viewmodels.HackerNewsViewModel
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module

actual val appModule: Module = module {
    single { HackerNewsClient(createHttpClient(OkHttp.create())) }
    single { HackerNewsRepository(get()) }
    factory { HackerNewsViewModel(get()) }
}