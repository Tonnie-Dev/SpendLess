package dev.tonnie.domain.usecase.account

import dev.tonnie.exceptions.DataError
import dev.tonnie.exceptions.Resource
import dev.tonnie.repository.AccountRepository

class IsUsernameAvailableUseCase(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(username: String): Resource<Unit> {
        return when (
            val result = accountRepository.usernameExists(username)
        ) {
            is Resource.Success -> {
                if (result.data)
                    Resource.Error(DataError.UsernameAlreadyExists)
                else
                    Resource.Success(Unit)
            }
            is Resource.Error -> result

        }
    }
}