package dev.tonnie.authentication.registration.handling

import dev.tonnie.presentation.handling.ActionEvent

sealed interface RegistrationActionEvent: ActionEvent {
    data object NavigateToCreatePin : RegistrationActionEvent
    data object NavigateToLogin : RegistrationActionEvent
}