package dev.tonnie.authentication.pin

import dev.tonnie.authentication.pin.handling.PinActionEvent
import dev.tonnie.authentication.pin.handling.PinPurpose
import dev.tonnie.authentication.pin.handling.PinStage
import dev.tonnie.authentication.pin.handling.PinUiEvent
import dev.tonnie.authentication.pin.handling.PinUiState
import dev.tonnie.domain.constants.AccountConstants.PIN_LENGTH
import dev.tonnie.domain.usecase.account.CreateAccountUseCase
import dev.tonnie.domain.usecase.account.VerifyPinUseCase
import dev.tonnie.exceptions.Resource
import dev.tonnie.presentation.BaseViewModel
import dev.tonnie.repository.SessionRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

typealias PinBaseViewModel = BaseViewModel<PinUiState, PinUiEvent, PinActionEvent>

class PinViewModel(
    val pinPurpose: PinPurpose,
    private val createAccountUseCase: CreateAccountUseCase,
    private val verifyPinUseCase: VerifyPinUseCase,
    private val sessionRepository: SessionRepository
) : PinBaseViewModel(initialState = PinUiState()) {

    private var createdPin: String? = null
    private var dismissBannerJob: Job? = null

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

        updateState { state ->
            state.copy(
                    pin = updatedPin,
                    pinErrorState = state.pinErrorState.copy(
                            pinMismatchError = false
                    )
            )
        }

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
                    pinErrorState = state.pinErrorState.copy(pinMismatchError = false)
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
                    pinErrorState = state.pinErrorState.copy(pinMismatchError = true)
            )
        }

        scheduleBannerDismissal()
    }

    private fun onPinsMatch() {
        when (pinPurpose) {
            is PinPurpose.CreateAccount -> createAccount(pinPurpose.username)
            is PinPurpose.UnlockAccount -> verifyPin()
        }
    }

    private fun verifyPin() {

        launch {
            when (val result = verifyPinUseCase(currentState.pin)) {
                is Resource.Success -> {
                    if (result.data) {
                        sessionRepository.refreshSession()
                        sendActionEvent(PinActionEvent.NavigateToDashboard)
                    } else {
                        onInvalidPin()
                    }
                }

                is Resource.Error -> onPinVerificationError()
            }
        }
    }

    private fun createAccount(username: String) {
        val pin = currentState.pin

        updateState { state ->
            state.copy(
                    isCreatingAccount = true
            )
        }

        launch {
            when (createAccountUseCase(username, pin)) {
                is Resource.Success -> {
                    sessionRepository.startSession(username)
                    sendActionEvent(PinActionEvent.NavigateToDashboard)
                }

                is Resource.Error -> {
                    updateState { state ->
                        state.copy(
                                isCreatingAccount = false,
                                pinErrorState = state.pinErrorState.copy(
                                        accountCreationError = true
                                )
                        )
                    }

                    scheduleBannerDismissal()
                }
            }
        }
    }

    private fun onInvalidPin() {
        updateState { state ->
            state.copy(
                    pin = "",
                    pinErrorState = state.pinErrorState.copy(
                            invalidPinError = true
                    )
            )
        }
        scheduleBannerDismissal()
    }

    private fun onPinVerificationError() {
        updateState { state ->
            state.copy(
                    pin = "",
                    pinErrorState = state.pinErrorState.copy(
                            pinVerificationError = true
                    )
            )
        }

        scheduleBannerDismissal()
    }

    private fun scheduleBannerDismissal() {
        dismissBannerJob?.cancel()
        dismissBannerJob = launch {
            delay(2_000.milliseconds)

            updateState { state ->
                state.copy(
                        pinErrorState = state.pinErrorState.copy(
                                pinMismatchError = false,
                                invalidPinError = false,
                                pinVerificationError = false,
                                accountCreationError = false
                        )
                )
            }
        }
    }

    private fun exitPinScreen() {
        when (pinPurpose) {
            is PinPurpose.CreateAccount -> sendActionEvent(PinActionEvent.NavigateBack)
            is PinPurpose.UnlockAccount -> sendActionEvent(PinActionEvent.NavigateToLogin)
        }
    }
}