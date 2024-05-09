package core.di

actual class PlatformConfiguration {
    actual val appName: String
        get() = "App Name"
    actual val platform: Platform
        get() = Platform.iOS
}
