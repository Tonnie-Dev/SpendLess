package dev.tonnie.authentication.registration.handling

import dev.tonnie.presentation.BaseViewModel

typealias RegistrationBaseViewModel = BaseViewModel<RegistrationUiState,RegistrationUiEvent, RegistrationActionEvent>
class RegistrationViewModel: RegistrationBaseViewModel(initialState = RegistrationUiState()) {
    override fun onEvent(event: RegistrationUiEvent) {
        TODO("Not yet implemented")
    }
}