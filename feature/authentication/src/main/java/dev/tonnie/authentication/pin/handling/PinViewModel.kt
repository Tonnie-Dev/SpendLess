package dev.tonnie.authentication.pin.handling

import dev.tonnie.presentation.BaseViewModel

typealias PinBaseViewModel = BaseViewModel<PinUiState, PinUiEvent, PinActionEvent>
class PinViewModel(username: String?): PinBaseViewModel(initialState = PinUiState()) {

    override fun onEvent(event: PinUiEvent) {
        when(event) {
            is PinUiEvent.PressPinDigit -> onPressPinDigit(event.digit)
            is PinUiEvent.PressPinBackspace -> onPressPinBackspace()
            is PinUiEvent.ExitPinScreen -> exitPinScreen()
        }
    }


    private fun onPressPinDigit(digit: String) {

        if (currentState.pin.length < PIN_LENGTH) {
            updateState { state -> state.copy(pin = state.pin + digit) }
        }
    }


    private fun onPressPinBackspace() {
        updateState { state -> state.copy(pin = state.pin.dropLast(1)) }
    }
    private fun exitPinScreen() {
       sendActionEvent(PinActionEvent.NavigateBack)
    }

    private companion object {

        private const val PIN_LENGTH = 5
    }
}