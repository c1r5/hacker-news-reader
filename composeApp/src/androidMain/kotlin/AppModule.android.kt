import dev.eric.hnreader.datasources.HackerNewsRepository
import dev.eric.hnreader.models.HackerNewsService
import dev.eric.hnreader.network.createHttpClient
import dev.eric.hnreader.services.HackerNewsServiceImpl
import dev.eric.hnreader.viewmodels.TechNewsViewModel
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

actual val appModule = module {
    single { createHttpClient(OkHttp.create()) }
    single<HackerNewsService> { HackerNewsServiceImpl(get()) }
    single { HackerNewsRepository(get()) }
    viewModelOf(::TechNewsViewModel)
}