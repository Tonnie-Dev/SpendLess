package dev.tonnie.authentication.login.handling

import androidx.annotation.StringRes
import androidx.compose.foundation.text.input.TextFieldState
import dev.tonnie.presentation.handling.UiState

data class LoginUiState(
    val usernameTextFieldState: TextFieldState = TextFieldState(),
    val pinTextFieldState: TextFieldState = TextFieldState(),

    @StringRes
    val usernameErrorRes: Int? = null,
    val isUsernameValid: Boolean = false,

    @StringRes
    val pinErrorRes: Int? = null,
    val isPinValid: Boolean = false,

    val loginErrorState: LoginErrorState = LoginErrorState()
) : UiState {

    val loginButtonEnabled: Boolean
        get() = isUsernameValid && isPinValid

    data class LoginErrorState(
        val invalidCredentialsError: Boolean = false,
        val accountNotFoundError: Boolean = false,
        val unknownError: Boolean = false,
    )
}