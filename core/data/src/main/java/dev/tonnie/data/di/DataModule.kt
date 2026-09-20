package dev.tonnie.data.di

import dev.tonnie.data.repository.AccountRepositoryImpl
import dev.tonnie.repository.AccountRepository
import org.koin.dsl.module

val dataModule = module {

    single<AccountRepository> {
        AccountRepositoryImpl(
                accountDao = get()
        )
    }
}