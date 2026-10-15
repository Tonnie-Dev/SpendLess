package dev.tonnie.authentication.pin.handling

import dev.tonnie.presentation.base.handling.UiState

data class PinUiState(
    val currentUser: String ?= null,
    val pin: String = "",
    val pinStage: PinStage = PinStage.CREATE,
    val isCreatingAccount: Boolean = false,
    val pinErrorState: PinErrorState = PinErrorState(),
    val lockState: LockState = LockState()

) : UiState {

    data class PinErrorState(
        val pinMismatchError: Boolean = false,
        val invalidPinError: Boolean = false,
        val pinVerificationError: Boolean = false,
        val accountCreationError: Boolean = false
    )

    data class LockState(
        val failedPinAttempts: Int = 0,
        val isPinLocked: Boolean = false,
        val lockoutRemainingSeconds: Int = 0,
    )
}

enum class PinStage { CREATE, CONFIRM }
