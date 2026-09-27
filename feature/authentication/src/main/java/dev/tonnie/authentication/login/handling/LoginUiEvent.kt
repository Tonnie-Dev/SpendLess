package dev.tonnie.authentication.login.handling

import dev.tonnie.presentation.handling.UiEvent

sealed interface LoginUiEvent : UiEvent {
    data object Login : LoginUiEvent
    data object Register : LoginUiEvent
}