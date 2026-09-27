package dev.tonnie.authentication.login.handling

import androidx.annotation.StringRes
import androidx.compose.foundation.text.input.TextFieldState
import dev.tonnie.presentation.handling.UiState

data class LoginUiState(
    val usernameTextFieldState: TextFieldState = TextFieldState(),
    val pinTextFieldState: TextFieldState = TextFieldState(),

    @StringRes
    val usernameErrorRes: Int? = null,

    @StringRes
    val pinErrorRes: Int? = null,

    val isUsernameValid: Boolean = false,
    val isPinValid: Boolean = false,
) : UiState {

    val loginButtonEnabled: Boolean
        get() = isUsernameValid && isPinValid
}