package dev.tonnie.authentication.pin

import android.provider.ContactsContract.PinnedPositions.pin
import dev.tonnie.authentication.pin.handling.PinActionEvent
import dev.tonnie.authentication.pin.handling.PinStage
import dev.tonnie.authentication.pin.handling.PinUiEvent
import dev.tonnie.authentication.pin.handling.PinUiState
import dev.tonnie.domain.constants.AccountConstants.PIN_LENGTH
import dev.tonnie.presentation.BaseViewModel

typealias PinBaseViewModel = BaseViewModel<PinUiState, PinUiEvent, PinActionEvent>

class PinViewModel(
    private val username: String?
) : PinBaseViewModel(initialState = PinUiState()) {

    private var createdPin: String? = null

    override fun onEvent(event: PinUiEvent) {
        when (event) {
            is PinUiEvent.PressPinDigit -> onPressPinDigit(event.digit)
            is PinUiEvent.PressPinBackspace -> onPressPinBackspace()
            is PinUiEvent.ExitPinScreen -> exitPinScreen()
        }
    }

    private fun onPressPinDigit(digit: String) {
        if (
            digit.length != 1
            || !digit.all { it.isDigit() }
            || currentState.pin.length >= PIN_LENGTH
        ) return

        val updatedPin = currentState.pin + digit

        updateState { state -> state.copy(pin = updatedPin, mismatchError = false) }

        if (updatedPin.length == PIN_LENGTH) {
            onPinCompleted()
        }
    }

    private fun onPressPinBackspace() {
        updateState { state -> state.copy(pin = state.pin.dropLast(1)) }
    }

    private fun enterConfirmPinStage() {
        updateState { state ->
            state.copy(
                    pin = "",
                    pinStage = PinStage.CONFIRM,
                    mismatchError = false
            )
        }
    }

    private fun onPinCompleted() {
        when (currentState.pinStage) {
            PinStage.CREATE -> enterConfirmPinStage()
            PinStage.CONFIRM -> comparePins()
        }
    }

    private fun comparePins() {
        val pinsMatch = currentState.pin == createdPin
        if (pinsMatch) {
            onPinsMatch()
        } else {
            onPinsMismatch()
        }
    }

    private fun onPinsMismatch() {
        updateState { state ->
            state.copy(
                    pin = "",
                    mismatchError = true
            )
        }
    }

    private fun onPinsMatch() {
val usernameToSave = username ?: return
       // val pin = encryptedPin ?: return

        //createAccount(usernameToSave, pin)
    }

    private fun exitPinScreen() {
        sendActionEvent(PinActionEvent.NavigateBack)
    }
}