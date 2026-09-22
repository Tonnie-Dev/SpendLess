package dev.tonnie.authentication.pin.handling

import dev.tonnie.presentation.handling.UiEvent

sealed interface PinUiEvent: UiEvent{

    data class PressPinDigit(val digit: String): PinUiEvent
    data object PressPinBackspace: PinUiEvent
    data object ExitPinScreen: PinUiEvent

}
