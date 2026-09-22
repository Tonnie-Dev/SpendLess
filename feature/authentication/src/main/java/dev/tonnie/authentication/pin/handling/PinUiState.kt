package dev.tonnie.authentication.pin.handling

import dev.tonnie.presentation.handling.UiState

data class PinUiState(val pin: String = ""): UiState
