package dev.tonnie.authentication.pin.handling

import dev.tonnie.presentation.handling.UiState

data class PinUiState(
    val pin: String = "",
    val pinStage: PinStage = PinStage.CREATE,
    val isCreatingAccount: Boolean = false,
    val pinMismatchError: Boolean = false,
    val accountCreationError: Boolean = false
) : UiState

enum class PinStage { CREATE, CONFIRM }
