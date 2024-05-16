package presentation.di

import org.koin.dsl.module
import presentation.MainViewModel
import presentation.chat.ChatViewModel
import presentation.chats.ChatsViewModel

val presentationModule = module {
    single<MainViewModel> { MainViewModel(get(), get()) }
    factory<ChatsViewModel> { ChatsViewModel(get(), get(), get(), get()) }
    factory<ChatViewModel> { ChatViewModel(get(), get(), get(), get()) }
}
