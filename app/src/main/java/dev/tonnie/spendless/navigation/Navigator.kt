package dev.tonnie.spendless.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

class Navigator(private val backStack: NavBackStack<NavKey>) {
    private fun push(destination: NavKey){
        backStack.add(destination)
    }

    fun navigateToRegistration(){

        push(RegistrationDestination)
    }

    fun navigateToPin(username: String? = null){

        push(PinDestination(username))
    }

    fun navigateToLogin(){

        push(LoginDestination)
    }


    fun popBackstack() {
        if (backStack.size > 1) {
            backStack.removeLastOrNull()
        }
    }
}