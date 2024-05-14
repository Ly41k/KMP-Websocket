package domain.di

import domain.interactor.ChatInteractorImpl
import domain.interactor.ChatsInteractor
import org.koin.dsl.module

val domainModule = module {
    single<ChatsInteractor> { ChatInteractorImpl(repository = get()) }
}
