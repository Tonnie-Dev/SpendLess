package dev.tonnie.authentication.login.handling

import dev.tonnie.presentation.base.handling.ActionEvent

sealed interface LoginActionEvent : ActionEvent {
    data object NavigateToDashboard : LoginActionEvent
    data object NavigateToRegistration: LoginActionEvent
}
