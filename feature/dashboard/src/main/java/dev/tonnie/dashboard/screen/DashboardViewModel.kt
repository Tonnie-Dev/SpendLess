package dev.tonnie.dashboard.screen

import dev.tonnie.dashboard.screen.handling.DashboardActionEvent
import dev.tonnie.dashboard.screen.handling.DashboardUiEvent
import dev.tonnie.dashboard.screen.handling.DashboardUiState
import dev.tonnie.presentation.BaseViewModel

typealias BaseDashboardViewModel = BaseViewModel<DashboardUiState, DashboardUiEvent, DashboardActionEvent>

class DashboardViewModel : BaseDashboardViewModel(initialState = DashboardUiState()) {
    override fun onEvent(event: DashboardUiEvent) {}
}
