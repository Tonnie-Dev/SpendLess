package dev.tonnie.domain.di

import dev.tonnie.domain.usecase.account.CreateAccountUseCase
import dev.tonnie.domain.usecase.account.IsUsernameAvailableUseCase
import dev.tonnie.domain.usecase.account.ValidateUsernameUseCase
import org.koin.dsl.module

val domainModule = module {

    factory {
        ValidateUsernameUseCase()
    }

    factory {
        IsUsernameAvailableUseCase(
                accountRepository = get()
        )
    }

    factory {
        CreateAccountUseCase(
                accountRepository = get(),
                pinHasher = get()
        )
    }
}