package dev.tonnie.dashboard.screen

import dev.tonnie.dashboard.screen.handling.DashboardActionEvent
import dev.tonnie.dashboard.screen.handling.DashboardUiEvent
import dev.tonnie.dashboard.screen.handling.DashboardUiState
import dev.tonnie.presentation.base.BaseViewModel
import dev.tonnie.repository.SessionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.withContext

typealias BaseDashboardViewModel = BaseViewModel<DashboardUiState, DashboardUiEvent, DashboardActionEvent>

class DashboardViewModel(
    private val sessionRepository: SessionRepository,
) : BaseDashboardViewModel(initialState = DashboardUiState()) {

    init {
        observeActiveUsername()
    }

    override fun onEvent(event: DashboardUiEvent) {

        when (event) {
            DashboardUiEvent.AddTransaction -> verifySession()

                     DashboardUiEvent.ExportData -> verifySession()
            DashboardUiEvent.OpenSettings ->verifySession()
        }

    }

    private fun observeActiveUsername() {
        launch {

            sessionRepository.activeUsername
                    .distinctUntilChanged()
                    .collect { username ->
                        updateState { state ->
                            state.copy(username = username.orEmpty())
                        }
                    }

        }
    }

    private suspend fun isSessionActive(): Boolean {

        return withContext(Dispatchers.IO) {
            sessionRepository.isSessionValid()
        }
    }

    private fun verifySession() {
        launch {

            val isSessionActive = isSessionActive()

            if (isSessionActive) {
                sendActionEvent(DashboardActionEvent.ShowToast)
            }else{
                sendActionEvent(DashboardActionEvent.NavigateToPin)
            }
        }

    }
}
