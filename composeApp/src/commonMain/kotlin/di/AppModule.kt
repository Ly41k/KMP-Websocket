package di

import core.di.PlatformConfiguration
import core.di.coreModule
import data.di.dataModule
import domain.di.domainModule
import org.koin.dsl.module
import presentation.chats.ChatsViewModel

fun appModule(configuration: PlatformConfiguration) = module {
    single<PlatformConfiguration> { configuration }
    includes(coreModule, dataModule, domainModule)
    factory<ChatsViewModel> { ChatsViewModel(get(), get()) }
}
