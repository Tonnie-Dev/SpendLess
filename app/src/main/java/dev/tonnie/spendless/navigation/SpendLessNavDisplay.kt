package dev.tonnie.spendless.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import dev.tonnie.authentication.pin.PinScreen
import dev.tonnie.authentication.pin.handling.PinPurpose
import dev.tonnie.authentication.registration.RegistrationScreen
import dev.tonnie.dashboard.DashboardScreen

@Composable
fun SpendlessNavDisplay() {

    val backStack = rememberNavBackStack(RegistrationDestination)

    val navigator = remember(backStack) {
        Navigator(backStack = backStack)
    }

    val entryProvider = entryProvider {
        entry<RegistrationDestination> {
            RegistrationScreen(
                    onNavigateToPin = {
                        navigator.navigateToPin(mode = PinMode.Create(username = it))
                    },
                    onNavigateToLogin = navigator::navigateToLogin
            )
        }

        entry<PinDestination> { destination ->

            val purpose = when (val mode = destination.mode) {
                is PinMode.Create -> {
                    PinPurpose.CreateAccount(mode.username)
                }

                PinMode.Unlock -> {
                    PinPurpose.UnlockAccount
                }
            }

            PinScreen(
                    pinPurpose = purpose,
                    onNavigateBack = navigator::popBackstack,
                    onNavigateToDashboard = navigator::navigateToDashboard,
                    onNavigateToLogin = navigator::navigateToLogin
            )
        }
        entry<LoginDestination> { }
        entry<DashboardDestination> {

            DashboardScreen()
        }
    }

    NavDisplay(
            backStack = backStack,
            entryProvider = entryProvider,
            entryDecorators = listOf(
                    rememberSaveableStateHolderNavEntryDecorator(),
                    rememberViewModelStoreNavEntryDecorator()
            )
    )
}
