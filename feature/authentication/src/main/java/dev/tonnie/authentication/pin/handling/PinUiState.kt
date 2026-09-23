package dev.tonnie.authentication.pin.handling

import dev.tonnie.presentation.handling.UiState

data class PinUiState(
    val pin: String = "",
    val pinStage: PinStage = PinStage.CREATE,
    val mismatchError: Boolean = false
) : UiState

enum class PinStage { CREATE, CONFIRM }
