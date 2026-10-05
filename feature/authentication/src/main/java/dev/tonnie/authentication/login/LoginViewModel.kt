package dev.tonnie.authentication.login

import androidx.compose.runtime.snapshotFlow
import dev.tonnie.authentication.login.handling.LoginActionEvent
import dev.tonnie.authentication.login.handling.LoginUiEvent
import dev.tonnie.authentication.login.handling.LoginUiState
import dev.tonnie.authentication.utils.toErrorMessage
import dev.tonnie.domain.constants.AppDefaults
import dev.tonnie.domain.usecase.account.ValidatePinUseCase
import dev.tonnie.domain.usecase.account.ValidateUsernameUseCase
import dev.tonnie.domain.usecase.account.VerifyPinUseCase
import dev.tonnie.exceptions.DataError
import dev.tonnie.exceptions.Resource
import dev.tonnie.presentation.BaseViewModel
import dev.tonnie.repository.SessionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged

typealias BaseLoginViewModel = BaseViewModel<LoginUiState, LoginUiEvent, LoginActionEvent>

class LoginViewModel(
    private val verifyPinUseCase: VerifyPinUseCase,
    private val validateUsernameUseCase: ValidateUsernameUseCase,
    private val validatePinUseCase: ValidatePinUseCase,
    private val sessionRepository: SessionRepository
) : BaseLoginViewModel(initialState = LoginUiState()) {

    private var dismissBannerJob: Job? = null

    init {
        observeUsernameInput()
        observePinInput()
    }

    override fun onEvent(event: LoginUiEvent) {

        when (event) {
            LoginUiEvent.Login -> onLogin()
            LoginUiEvent.Register -> onRegister()
        }
    }

    private fun observeUsernameInput() {

        launch {
            snapshotFlow { currentState.usernameTextFieldState.text.toString() }
                    .distinctUntilChanged()
                    .collect { username ->

                        clearLoginError()

                        if (username.isEmpty()) {
                            updateState { state ->
                                state.copy(
                                        usernameErrorRes = null,
                                        isUsernameValid = false,
                                )
                            }
                            return@collect
                        }

                        when (val result = validateUsernameUseCase(username)) {

                            is Resource.Success -> {
                                updateState { state ->
                                    state.copy(
                                            usernameErrorRes = null,
                                            isUsernameValid = true
                                    )
                                }
                            }

                            is Resource.Error -> {
                                updateState { state ->
                                    state.copy(
                                            usernameErrorRes =
                                                result.error.toErrorMessage(username),
                                            isUsernameValid = false
                                    )
                                }
                            }
                        }
                    }
        }
    }

    private fun observePinInput() {
        launch {
            snapshotFlow { currentState.pinTextFieldState.text.toString() }
                    .distinctUntilChanged()
                    .collect { pin ->

                        clearLoginError()

                        if (pin.isEmpty()) {
                            updateState { state ->
                                state.copy(
                                        pinErrorRes = null,
                                        isPinValid = false,
                                )
                            }
                            return@collect
                        }

                        when (val result = validatePinUseCase(pin)) {

                            is Resource.Success -> {
                                updateState { state ->
                                    state.copy(
                                            pinErrorRes = null,
                                            isPinValid = true
                                    )
                                }
                            }

                            is Resource.Error -> {

                                updateState { state ->
                                    state.copy(
                                            pinErrorRes =
                                                result.error.toErrorMessage(pin),
                                            isPinValid = false
                                    )
                                }
                            }
                        }
                    }
        }
    }

    private fun onLogin() {

        val username = currentState.usernameTextFieldState
                .text
                .toString()

        val pin = currentState.pinTextFieldState
                .text
                .toString()

        launchCatching(
                context = Dispatchers.IO,
                onError = { onUnknownError() },
                onStart = {},
                onCompletion = {}
        ) {

            when (val result = verifyPinUseCase(username, pin)) {

                is Resource.Success -> {
                    if (result.data) {
                        sessionRepository.startSession(username)
                        sendActionEvent(LoginActionEvent.NavigateToDashboard)
                    } else {
                        onInvalidCredentialsError()
                    }
                }

                is Resource.Error -> {
                    when (result.error) {
                        DataError.AccountNotFound -> {
                            onAccountNotFoundError()
                        }

                        else -> {
                            onUnknownError()
                        }
                    }
                }
            }
        }
    }

    private fun clearLoginError() {

        val errorState = currentState.loginErrorState
        if (errorState.invalidCredentialsError ||
            errorState.accountNotFoundError ||
            errorState.unknownError
        ) {
            dismissBannerJob?.cancel()
            updateState { state ->
                state.copy(
                        loginErrorState = LoginUiState.LoginErrorState()
                )
            }
        }
    }

    private fun scheduleBannerDismissal() {

        dismissBannerJob?.cancel()

        dismissBannerJob = launch {

            delay(AppDefaults.BANNER_DURATION_LENGTH)

            updateState { state ->
                state.copy(
                        loginErrorState = state.loginErrorState.copy(
                                invalidCredentialsError = false,
                                accountNotFoundError = false,
                                unknownError = false,
                        )
                )
            }
        }
    }

    private fun onInvalidCredentialsError() {

        updateState { state ->
            state.copy(
                    loginErrorState = LoginUiState.LoginErrorState(
                            invalidCredentialsError = true
                    )
            )
        }
        scheduleBannerDismissal()
    }

    private fun onAccountNotFoundError() {

        updateState { state ->
            state.copy(
                    loginErrorState = LoginUiState.LoginErrorState(
                            accountNotFoundError = true
                    )
            )
        }
        scheduleBannerDismissal()
    }

    private fun onUnknownError() {

        updateState { state ->
            state.copy(
                    loginErrorState = LoginUiState.LoginErrorState(
                            unknownError = true
                    )
            )
        }
        scheduleBannerDismissal()
    }

    private fun onRegister() {
        sendActionEvent(LoginActionEvent.NavigateToRegistration)
    }
}