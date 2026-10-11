package dev.tonnie.authentication.login.handling

import dev.tonnie.presentation.base.handling.UiEvent

sealed interface LoginUiEvent : UiEvent {
    data object Login : LoginUiEvent
    data object Register : LoginUiEvent
}