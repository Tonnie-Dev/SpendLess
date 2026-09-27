package dev.tonnie.domain.usecase.account

import dev.tonnie.domain.constants.AccountConstants.PIN_LENGTH
import dev.tonnie.exceptions.DataError
import dev.tonnie.exceptions.Resource

class ValidatePinUseCase {

    operator fun invoke(pin: String): Resource<Boolean> {

        return if (pin.length != PIN_LENGTH) {
            Resource.Error(DataError.InvalidPinLength)
        } else if (pin.any { !it.isDigit() }) {
            Resource.Error(DataError.InvalidPinFormat)
        } else {
            Resource.Success(true)
        }
    }
}