package dev.tonnie.authentication.registration.handling

import androidx.annotation.StringRes
import androidx.compose.foundation.text.input.TextFieldState
import dev.tonnie.presentation.handling.UiState

data class RegistrationUiState(
    val usernameTextFieldState: TextFieldState = TextFieldState(),
    val isLoading: Boolean = false,
    @StringRes val error: Int? = null,
    @StringRes val usernameError: Int? = null,
    val nextButtonEnabled: Boolean = false
) : UiState
