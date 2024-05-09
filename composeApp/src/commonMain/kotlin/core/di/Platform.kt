package core.di

import androidx.compose.runtime.staticCompositionLocalOf

enum class Platform {
    Android, Desktop, iOS
}

val LocalPlatform = staticCompositionLocalOf<Platform> { error("no default platform") }
