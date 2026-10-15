package dev.tonnie.authentication.pin

import dev.tonnie.authentication.login.handling.LoginUiState
import dev.tonnie.authentication.pin.handling.PinActionEvent
import dev.tonnie.authentication.pin.handling.PinPurpose
import dev.tonnie.authentication.pin.handling.PinStage
import dev.tonnie.authentication.pin.handling.PinUiEvent
import dev.tonnie.authentication.pin.handling.PinUiState
import dev.tonnie.domain.constants.AccountConstants.PIN_LENGTH
import dev.tonnie.domain.constants.AppDefaults
import dev.tonnie.domain.usecase.account.CreateAccountUseCase
import dev.tonnie.domain.usecase.account.VerifyPinUseCase
import dev.tonnie.exceptions.Resource
import dev.tonnie.presentation.base.BaseViewModel
import dev.tonnie.repository.SessionRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

typealias PinBaseViewModel = BaseViewModel<PinUiState, PinUiEvent, PinActionEvent>

class PinViewModel(
    val pinPurpose: PinPurpose,
    private val createAccountUseCase: CreateAccountUseCase,
    private val verifyPinUseCase: VerifyPinUseCase,
    private val sessionRepository: SessionRepository,
) : PinBaseViewModel(initialState = PinUiState()) {

    init {

        if (pinPurpose is PinPurpose.UnlockAccount) {

            observeCurrentUser()
        }
    }

    private var createdPin: String? = null
    private var isVerifyingPin = false

    private var dismissBannerJob: Job? = null
    private var lockoutJob: Job? = null

    override fun onEvent(event: PinUiEvent) {
        when (event) {
            is PinUiEvent.PressPinDigit -> onPressPinDigit(event.digit)
            is PinUiEvent.PressPinBackspace -> onPressPinBackspace()
            is PinUiEvent.ExitPinScreen -> exitPinScreen()
        }
    }

    private fun observeCurrentUser() {
        launch {
            sessionRepository.activeUsername.collect { currentUser ->
                if (currentUser == null) {
                    sendActionEvent(PinActionEvent.NavigateToLogin)
                } else {
                    updateState { state -> state.copy(currentUser = currentUser) }
                }
            }
        }
    }

    private fun onPressPinDigit(digit: String) {

        if (currentState.lockState.isPinLocked) return

        if (
            pinPurpose is PinPurpose.UnlockAccount &&
            currentState.currentUser == null
        ) return

        if (
            digit.length != 1
            || !digit.all { it.isDigit() }
            || currentState.pin.length >= PIN_LENGTH
        ) return

        clearLoginError()

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
        if (currentState.lockState.isPinLocked) return
        updateState { state -> state.copy(pin = state.pin.dropLast(1)) }
    }


    private fun clearLoginError() {

        val errorState = currentState.pinErrorState
        if (errorState.pinMismatchError ||
            errorState.invalidPinError ||
            errorState.pinVerificationError ||
            errorState.accountCreationError
        ) {
            dismissBannerJob?.cancel()
            updateState { state ->
                state.copy(
                        pinErrorState = PinUiState.PinErrorState()
                )
            }
        }
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
        when (pinPurpose) {
            is PinPurpose.CreateAccount -> {
                when (currentState.pinStage) {
                    PinStage.CREATE -> enterConfirmPinStage()
                    PinStage.CONFIRM -> comparePins()
                }
            }

            is PinPurpose.UnlockAccount -> {
                verifyPin()
            }
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

    private fun onPinsMatch() {
        when (pinPurpose) {
            is PinPurpose.CreateAccount -> createAccount(pinPurpose.username)
            is PinPurpose.UnlockAccount -> verifyPin()
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

    private fun onInvalidPinAttempt() {

        val failedAttempts = currentState.lockState.failedPinAttempts + 1
        updateState { state ->
            state.copy(
                    pin = "",
                    lockState = state.lockState.copy(failedPinAttempts = failedAttempts),
                    pinErrorState = state.pinErrorState.copy(invalidPinError = true)
            )
        }
        scheduleBannerDismissal()


        if (failedAttempts >= AppDefaults.MAX_PIN_ATTEMPTS) {
            startPinLockout()
        }
    }

    private fun startPinLockout() {
        lockoutJob?.cancel()
        lockoutJob = launch {
            updateState { state ->

                state.copy(
                        pin = "",
                        lockState = state.lockState.copy(
                                isPinLocked = true,
                                lockoutRemainingSeconds = AppDefaults.PIN_LOCKOUT_DURATION_SECONDS
                        )
                )
            }

            while (currentState.lockState.lockoutRemainingSeconds > 0) {
                delay(1000.milliseconds)
                updateState { state ->
                    state.copy(
                            lockState = state.lockState.copy(
                                    lockoutRemainingSeconds = (state.lockState.lockoutRemainingSeconds - 1)
                                            .coerceAtLeast(0)
                            )
                    )
                }
            }

            updateState { state ->
                state.copy(
                        lockState = state.lockState.copy(
                                isPinLocked = false,
                                failedPinAttempts = 0,
                                lockoutRemainingSeconds = 0
                        )
                )
            }
        }
    }

    private fun verifyPin() {

        if (isVerifyingPin || currentState.lockState.isPinLocked) return
        val currentUsername = currentState.currentUser ?: return
        val pin = currentState.pin



        launch {
            try {
                isVerifyingPin = true
                when (val result =
                    verifyPinUseCase(username = currentUsername, pin = pin)) {
                    is Resource.Success -> {
                        if (result.data) {
                            sessionRepository.refreshSession()
                            sendActionEvent(PinActionEvent.NavigateToDashboard)
                        } else {
                            onInvalidPinAttempt()
                        }
                    }

                    is Resource.Error -> onPinVerificationError()
                }
            }catch (e: CancellationException) {
               throw e
            }
            catch (e: Exception) {
                onPinVerificationError()
            } finally {
                isVerifyingPin = false
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

/*
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
*/

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
            delay(AppDefaults.BANNER_DURATION_LENGTH)

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