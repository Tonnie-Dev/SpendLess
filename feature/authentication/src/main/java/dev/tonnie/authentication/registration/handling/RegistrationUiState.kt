package dev.tonnie.authentication.registration.handling

import dev.tonnie.presentation.handling.UiState

data class RegistrationUiState(
    val username: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
): UiState
