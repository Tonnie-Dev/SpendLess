@file:OptIn(FlowPreview::class)

package dev.tonnie.authentication.registration

import androidx.annotation.StringRes
import androidx.compose.runtime.snapshotFlow
import dev.tonnie.authentication.R
import dev.tonnie.authentication.registration.handling.RegistrationActionEvent
import dev.tonnie.authentication.registration.handling.RegistrationUiEvent
import dev.tonnie.authentication.registration.handling.RegistrationUiState
import dev.tonnie.domain.usecase.account.IsUsernameAvailableUseCase
import dev.tonnie.domain.usecase.account.ValidateUsernameUseCase
import dev.tonnie.exceptions.DataError
import dev.tonnie.exceptions.Resource
import dev.tonnie.presentation.BaseViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.map

typealias RegistrationBaseViewModel = BaseViewModel<RegistrationUiState, RegistrationUiEvent, RegistrationActionEvent>

class RegistrationViewModel(
    private val isUsernameAvailableUseCase: IsUsernameAvailableUseCase,
    private val validateUsernameUseCase: ValidateUsernameUseCase
) : RegistrationBaseViewModel(initialState = RegistrationUiState()) {

    init {
        observeUsernameInput()
    }

    override fun onEvent(event: RegistrationUiEvent) {
        when (event) {
            is RegistrationUiEvent.NextClicked -> {
                checkUsernameAvailability()
            }

            RegistrationUiEvent.SignInClicked -> {
                onSignInClicked()
            }
        }
    }

    private fun observeUsernameInput() {
        launch {
            snapshotFlow { currentState.usernameTextFieldState.text.toString() }
                    .map { it.trim() }
                    .collect { username ->

                        when(val result = validateUsernameUseCase(username)){

                            is Resource.Success -> {
                                updateState { state ->
                                    state.copy(
                                            nextButtonEnabled = true,
                                            error = null,
                                            usernameError = null
                                    )
                                }

                        }

                            is Resource.Error -> {
                                updateState { state ->
                                    state.copy(
                                            nextButtonEnabled = false,

                                            usernameError = result.error.toUsernameError(username)
                                    )
                                }
                            }

                        }

                    /*    val isValid = when (validateUsernameUseCase.invoke(username)) {
                            is Resource.Success -> true
                            is Resource.Error -> false
                        }
                        updateState { state ->
                            state.copy(
                                    nextButtonEnabled = isValid,
                                    error = null
                            )
                        }*/
                    }
        }
    }

    private fun checkUsernameAvailability() {
        val username = currentState.usernameTextFieldState.text
                .toString()
                .trim()

        if (!currentState.nextButtonEnabled) return

        launch {
            updateState {
                it.copy(
                        isLoading = true,
                        nextButtonEnabled = false
                )
            }

            when (val result = isUsernameAvailableUseCase(username)) {
                is Resource.Success -> {
                    updateState {
                        it.copy(isLoading = false)
                    }

                    sendActionEvent(RegistrationActionEvent.NavigateToCreatePin(username))

                }

                is Resource.Error -> {
                    when (result.error) {

                        is DataError.UsernameAlreadyExists -> {
                            updateState { state ->
                                state.copy(
                                        isLoading = false,
                                        nextButtonEnabled = false,
                                        error = R.string.banner_text_username_taken
                                )
                            }
                        }

                        else -> {
                            updateState {
                                it.copy(
                                        isLoading = false,
                                        nextButtonEnabled = true,
                                        error = R.string.banner_text_generic_error
                                )
                            }
                        }
                    }
                    // Handle unexpected DB / IO error                  }
                }
            }
        }
    }

    private fun onSignInClicked() {
        sendActionEvent(RegistrationActionEvent.NavigateToLogin)
    }
}

@StringRes
private fun DataError.toUsernameError(username: String): Int? {
    return when (this) {
        DataError.InvalidUsernameLength -> {
            if (username.length > 14) {
                R.string.supporting_text_username_error_length
            } else {
                null
            }
        }

        DataError.InvalidUsernameFormat -> {
            R.string.supporting_text_username_error_format
        }

        else -> null
    }
}