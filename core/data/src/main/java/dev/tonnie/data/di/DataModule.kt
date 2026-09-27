package dev.tonnie.data.di

import dev.tonnie.data.hashing.Pbkdf2PinHasher
import dev.tonnie.data.repository.AccountRepositoryImpl
import dev.tonnie.data.repository.SessionRepositoryImpl
import dev.tonnie.data.repository.SystemClock
import dev.tonnie.domain.hashing.PinHasher
import dev.tonnie.repository.AccountRepository
import dev.tonnie.repository.Clock
import dev.tonnie.repository.SessionRepository
import org.koin.dsl.module

val dataModule = module {

    single<AccountRepository> { AccountRepositoryImpl(accountDao = get()) }
    single<PinHasher> { Pbkdf2PinHasher() }
    single<Clock> { SystemClock() }
    single<SessionRepository> { SessionRepositoryImpl(sessionPrefs = get(), clock = get()) }
}