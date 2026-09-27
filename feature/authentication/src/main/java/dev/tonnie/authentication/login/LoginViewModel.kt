package dev.tonnie.authentication.login

import androidx.compose.runtime.snapshotFlow
import dev.tonnie.authentication.login.handling.LoginActionEvent
import dev.tonnie.authentication.login.handling.LoginUiEvent
import dev.tonnie.authentication.login.handling.LoginUiState
import dev.tonnie.authentication.utils.toErrorMessage
import dev.tonnie.domain.usecase.account.ValidatePinUseCase
import dev.tonnie.domain.usecase.account.ValidateUsernameUseCase
import dev.tonnie.exceptions.Resource
import dev.tonnie.presentation.BaseViewModel
import kotlinx.coroutines.flow.distinctUntilChanged

typealias BaseLoginViewModel = BaseViewModel<LoginUiState, LoginUiEvent, LoginActionEvent>

class LoginViewModel(
    private val validateUsernameUseCase: ValidateUsernameUseCase,
    private val validatePinUseCase: ValidatePinUseCase
) : BaseLoginViewModel(initialState = LoginUiState()) {

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
                                updateState { state -> state.copy(pinErrorRes = null,
                                        isPinValid = true) }
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
    }

    private fun onRegister() {
        sendActionEvent(LoginActionEvent.NavigateToRegistration)

    }
}