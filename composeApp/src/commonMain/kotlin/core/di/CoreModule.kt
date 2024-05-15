package core.di

import core.exception.DefaultExceptionService
import core.exception.ExceptionService
import core.ktor.ktorModule
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val coreModule = module {
    single<ExceptionService> { DefaultExceptionService() }
    includes(ktorModule)
    single { Json { encodeDefaults = true } }
}
