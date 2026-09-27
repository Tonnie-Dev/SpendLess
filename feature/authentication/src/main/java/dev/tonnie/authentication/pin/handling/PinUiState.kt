package dev.tonnie.authentication.pin.handling

import dev.tonnie.presentation.handling.UiState

data class PinUiState(
    val pin: String = "",
    val pinStage: PinStage = PinStage.CREATE,
    val isCreatingAccount: Boolean = false,
    val pinErrorState: PinErrorState = PinErrorState()
) : UiState {

    data class PinErrorState(
        val pinMismatchError: Boolean = false,
        val invalidPinError: Boolean = false,
        val pinVerificationError: Boolean = false,
        val accountCreationError: Boolean = false
    )
}

enum class PinStage { CREATE, CONFIRM }
