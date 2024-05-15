package di

import core.di.PlatformConfiguration
import core.di.coreModule
import data.di.dataModule
import domain.di.domainModule
import org.koin.dsl.module
import presentation.di.presentationModule

fun appModule(configuration: PlatformConfiguration) = module {
    single<PlatformConfiguration> { configuration }
    includes(dataModule, coreModule, domainModule, presentationModule)
}
