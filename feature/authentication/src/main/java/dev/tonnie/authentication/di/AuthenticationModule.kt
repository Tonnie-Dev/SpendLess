package dev.tonnie.authentication.di

import dev.tonnie.authentication.pin.handling.PinViewModel
import dev.tonnie.authentication.registration.RegistrationViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authenticationModule = module {

    viewModelOf(::RegistrationViewModel)
    viewModelOf(::PinViewModel)
}