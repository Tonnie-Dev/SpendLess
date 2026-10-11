package dev.tonnie.dashboard.screen.handling

import dev.tonnie.presentation.base.handling.UiState

class DashboardUiState(
    val username: String = "",
    val accountBalance: Double = 0.0,
    val previousWeekAmount: Double = 0.0,
    val largestTransaction: Double? = null,
    val hasTransactions: Boolean = false,
    val isLoading: Boolean = false
) : UiState
