package dev.tonnie.dashboard.screen.handling

import dev.tonnie.presentation.base.handling.ActionEvent

sealed interface DashboardActionEvent : ActionEvent{

    object NavigateToSetting: DashboardActionEvent
    object NavigateToAddTransaction: DashboardActionEvent
    object NavigateToExport: DashboardActionEvent
}
