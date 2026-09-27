package dev.tonnie.spendless.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

class Navigator(private val backStack: NavBackStack<NavKey>) {

    fun navigateToRegistration() {
        push(RegistrationDestination)
    }


    fun navigateToPin(mode: PinMode) {
        push(PinDestination(mode))
    }

    fun navigateToLogin() {

        clearAndReplace(LoginDestination)
    }

    fun navigateToDashboard() {
        push(DashboardDestination)
    }

    fun popBackstack() {
        if (backStack.size > 1) {
            backStack.removeLastOrNull()
        }
    }

    private fun push(destination: NavKey) {
        backStack.add(destination)
    }

    private fun clearAndReplace(destination: NavKey) {
        backStack.clear()

        backStack.add(destination)
    }
}