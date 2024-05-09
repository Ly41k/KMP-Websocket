package core.di

import android.content.Context

actual class PlatformConfiguration constructor(val activityContext: Context) {
    actual val appName: String
        get() = "App Name"
    actual val platform: Platform
        get() = Platform.Android
}
