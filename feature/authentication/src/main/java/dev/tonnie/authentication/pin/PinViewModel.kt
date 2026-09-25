package dev.tonnie.authentication.pin

import dev.tonnie.authentication.pin.handling.PinActionEvent
import dev.tonnie.authentication.pin.handling.PinStage
import dev.tonnie.authentication.pin.handling.PinUiEvent
import dev.tonnie.authentication.pin.handling.PinUiState
import dev.tonnie.domain.constants.AccountConstants.PIN_LENGTH
import dev.tonnie.domain.usecase.account.CreateAccountUseCase
import dev.tonnie.exceptions.Resource
import dev.tonnie.presentation.BaseViewModel

typealias PinBaseViewModel = BaseViewModel<PinUiState, PinUiEvent, PinActionEvent>

class PinViewModel(
    private val username: String?,
    private val createAccountUseCase: CreateAccountUseCase
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

        updateState { state -> state.copy(pin = updatedPin, pinMismatchError = false) }

        if (updatedPin.length == PIN_LENGTH) {
            onPinCompleted()
        }
    }

    private fun onPressPinBackspace() {
        updateState { state -> state.copy(pin = state.pin.dropLast(1)) }
    }

    private fun enterConfirmPinStage() {
        createdPin = currentState.pin
        updateState { state ->
            state.copy(
                    pin = "",
                    pinStage = PinStage.CONFIRM,
                    pinMismatchError = false
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
                    pinMismatchError = true
            )
        }
    }

    private fun onPinsMatch() {
        createAccount()
    }

    private fun createAccount() {
        val username = this.username ?: return
        val pin = currentState.pin

        updateState { state ->
            state.copy(
                    isCreatingAccount = true,
                    accountCreationError = false
            )
        }

        launch {
            when (createAccountUseCase(username, pin)) {
                is Resource.Success -> {
                    sendActionEvent(PinActionEvent.NavigateToDashboard)
                }

                is Resource.Error -> {
                    updateState { state ->
                        state.copy(
                               isCreatingAccount = false,
                                accountCreationError = true)
                    }
                }
            }
        }
    }

    private fun exitPinScreen() {

            if (username != null) {
                sendActionEvent(PinActionEvent.NavigateBack)
            } else {
                sendActionEvent(PinActionEvent.NavigateToLogin)
            }
        }

}