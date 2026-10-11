package dev.tonnie.authentication.registration.handling

import dev.tonnie.presentation.base.handling.ActionEvent

sealed interface RegistrationActionEvent: ActionEvent {
    data class NavigateToCreatePin(val username: String) : RegistrationActionEvent
    data object NavigateToLogin : RegistrationActionEvent
}