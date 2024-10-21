import org.koin.core.context.startKoin

actual class KoinInit {
    actual fun init() {
        startKoin {
            modules(appModule)
        }
    }
}