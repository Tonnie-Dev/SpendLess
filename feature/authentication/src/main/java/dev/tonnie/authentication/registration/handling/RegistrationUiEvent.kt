package dev.tonnie.authentication.registration.handling

import dev.tonnie.presentation.handling.UiEvent

sealed interface RegistrationUiEvent : UiEvent {
    data object NextClicked : RegistrationUiEvent
    data object SignInClicked : RegistrationUiEvent
}
