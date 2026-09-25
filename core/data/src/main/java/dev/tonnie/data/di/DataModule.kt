package dev.tonnie.data.di

import dev.tonnie.data.hashing.Pbkdf2PinHasher
import dev.tonnie.data.repository.AccountRepositoryImpl
import dev.tonnie.domain.hashing.PinHasher
import dev.tonnie.repository.AccountRepository
import org.koin.dsl.module

val dataModule = module {

    single<AccountRepository> {
        AccountRepositoryImpl(
                accountDao = get()
        )
    }
    single < PinHasher>{ Pbkdf2PinHasher()}
}