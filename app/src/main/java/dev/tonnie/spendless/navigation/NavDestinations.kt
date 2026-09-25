package dev.tonnie.spendless.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
@Serializable
data object RegistrationDestination : NavKey

@Serializable
data class PinDestination(val username:String? = null) : NavKey

@Serializable
data object LoginDestination : NavKey



@Serializable
data object DashboardDestination : NavKey

/*
@Serializable
data class PinDestination(
    val mode: PinMode
) : NavKey
@Serializable
sealed interface PinMode {

    @Serializable
    data class Create(
        val username: String
    ) : PinMode

    @Serializable
    data object Unlock : PinMode
}
*/
