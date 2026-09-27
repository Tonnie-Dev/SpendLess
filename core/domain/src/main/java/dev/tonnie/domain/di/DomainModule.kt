package dev.tonnie.domain.di

import dev.tonnie.domain.usecase.account.CreateAccountUseCase
import dev.tonnie.domain.usecase.account.IsUsernameAvailableUseCase
import dev.tonnie.domain.usecase.account.ValidatePinUseCase
import dev.tonnie.domain.usecase.account.ValidateUsernameUseCase
import dev.tonnie.domain.usecase.account.VerifyPinUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::ValidateUsernameUseCase)
    factoryOf(::IsUsernameAvailableUseCase)
    factoryOf(::CreateAccountUseCase)
    factoryOf(::VerifyPinUseCase)
    factoryOf(::ValidatePinUseCase)
}