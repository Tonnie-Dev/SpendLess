package dev.tonnie.dashboard.di

import dev.tonnie.dashboard.screen.DashboardViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val dashboardModule = module {

    viewModelOf(::DashboardViewModel)
}