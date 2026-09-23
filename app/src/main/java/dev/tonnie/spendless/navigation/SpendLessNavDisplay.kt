package dev.tonnie.spendless.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import dev.tonnie.authentication.pin.PinScreen
import dev.tonnie.authentication.registration.RegistrationScreen

@Composable
fun SpendlessNavDisplay() {

    val backStack = rememberNavBackStack(RegistrationDestination)

    val navigator = remember (backStack){
        Navigator(backStack = backStack)
    }

    val entryProvider = entryProvider {
        entry<RegistrationDestination> {
            RegistrationScreen(
                    onNavigateToPin = navigator::navigateToPin,
                    onNavigateToLogin = navigator::navigateToLogin
            )
        }

        entry<PinDestination> {
            PinScreen(
                    username = it.username,
                    onNavigateToLogin = navigator::navigateToLogin
            )
        }
        entry<LoginDestination> { }

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
