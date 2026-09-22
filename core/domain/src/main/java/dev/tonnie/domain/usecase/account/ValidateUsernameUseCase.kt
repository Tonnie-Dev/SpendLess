package dev.tonnie.domain.usecase.account

import dev.tonnie.exceptions.DataError
import dev.tonnie.exceptions.Resource

class ValidateUsernameUseCase {

    operator fun invoke(username: String): Resource<Boolean> {
        if (username.length !in 3..14) {
            return Resource.Error(DataError.InvalidUsernameLength)
        }

        if (!username.matches(USERNAME_REGEX)) {
            return Resource.Error(DataError.InvalidUsernameFormat)
        }

        if (username.none { it.isLetter() }) {
            return Resource.Error(DataError.InvalidUsernameFormat)
        }

        return Resource.Success(true)
    }

    private companion object {
        val USERNAME_REGEX = Regex("^[A-Za-z0-9]+$")
    }
}