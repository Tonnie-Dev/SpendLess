package dev.tonnie.authentication.pin.handling

sealed interface PinPurpose{

    data class CreateAccount(val username: String): PinPurpose
    data object UnlockAccount: PinPurpose
}