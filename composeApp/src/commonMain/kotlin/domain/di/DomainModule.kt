package domain.di

import domain.interactor.ChatInteractorImpl
import domain.interactor.ChatsInteractor
import domain.interactor.MessageInteractor
import domain.interactor.MessageInteractorImpl
import org.koin.dsl.module

val domainModule = module {
    single<ChatsInteractor> { ChatInteractorImpl(repository = get()) }
    single<MessageInteractor> { MessageInteractorImpl(get(), get()) }
}
