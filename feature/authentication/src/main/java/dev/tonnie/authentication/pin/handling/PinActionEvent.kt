package dev.tonnie.authentication.pin.handling

import dev.tonnie.presentation.handling.ActionEvent

sealed interface PinActionEvent: ActionEvent {
    object NavigateBack : PinActionEvent
    data object NavigateToLogin : PinActionEvent
    object NavigateToDashboard : PinActionEvent
}