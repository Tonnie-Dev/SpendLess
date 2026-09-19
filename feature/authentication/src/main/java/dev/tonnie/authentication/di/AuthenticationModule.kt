package dev.tonnie.authentication.di

import dev.tonnie.authentication.registration.handling.RegistrationViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authenticationModule = module {

    viewModelOf(::RegistrationViewModel)
}