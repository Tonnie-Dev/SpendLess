package dev.tonnie.dashboard.screen.handling

import dev.tonnie.presentation.handling.UiEvent

sealed interface DashboardUiEvent : UiEvent{

    object OpenSettings : DashboardUiEvent
    object AddTransaction : DashboardUiEvent
    object ExportData: DashboardUiEvent
}
