@file:OptIn(FlowPreview::class)

package dev.tonnie.authentication.registration

import androidx.compose.runtime.snapshotFlow
import dev.tonnie.authentication.R
import dev.tonnie.authentication.registration.handling.RegistrationActionEvent
import dev.tonnie.authentication.registration.handling.RegistrationUiEvent
import dev.tonnie.authentication.registration.handling.RegistrationUiState
import dev.tonnie.authentication.utils.toErrorMessage
import dev.tonnie.domain.constants.AppDefaults
import dev.tonnie.domain.usecase.account.IsUsernameAvailableUseCase
import dev.tonnie.domain.usecase.account.ValidateUsernameUseCase
import dev.tonnie.exceptions.DataError
import dev.tonnie.exceptions.Resource
import dev.tonnie.presentation.BaseViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

typealias RegistrationBaseViewModel = BaseViewModel<RegistrationUiState, RegistrationUiEvent, RegistrationActionEvent>

class RegistrationViewModel(
    private val isUsernameAvailableUseCase: IsUsernameAvailableUseCase,
    private val validateUsernameUseCase: ValidateUsernameUseCase
) : RegistrationBaseViewModel(initialState = RegistrationUiState()) {

    private var bannerDismissJob: Job? = null

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
                   // .map { it.trim() }
                    .collect { username ->

                        when (val result = validateUsernameUseCase(username)) {

                            is Resource.Success -> {
                                updateState { state ->
                                    state.copy(
                                            nextButtonEnabled = true,
                                            error = null,
                                            usernameInputError = null
                                    )
                                }
                            }

                            is Resource.Error -> {
                                updateState { state ->
                                    state.copy(
                                            nextButtonEnabled = false,

                                            usernameInputError = result.error.toErrorMessage(
                                                    username
                                            )
                                    )
                                }
                            }

                        }

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
                        it.copy(isLoading = false, nextButtonEnabled = true)
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
                                        error = R.string.banner_text_username_taken,
                                        unavailableUsername = username
                                )
                            }

                            scheduleBannerDismissal()
                        }

                        else -> {

                            updateState {
                                it.copy(
                                        isLoading = false,
                                        nextButtonEnabled = true,
                                        error = R.string.banner_text_generic_error
                                )
                            }

                            scheduleBannerDismissal()
                        }
                    }
                }
            }
        }
    }

    private fun scheduleBannerDismissal() {
        bannerDismissJob?.cancel()

        bannerDismissJob = launch {

            delay(AppDefaults.BANNER_DURATION_LENGTH)
            updateState { state ->
                state.copy(
                        error = null,
                        unavailableUsername = null
                )
            }
        }
    }

    private fun onSignInClicked() {
        sendActionEvent(RegistrationActionEvent.NavigateToLogin)
    }
}
