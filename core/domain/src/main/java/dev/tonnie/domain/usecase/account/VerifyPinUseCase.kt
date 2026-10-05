package dev.tonnie.domain.usecase.account

import dev.tonnie.domain.hashing.PinHasher
import dev.tonnie.exceptions.DataError
import dev.tonnie.exceptions.Resource
import dev.tonnie.repository.AccountRepository

class VerifyPinUseCase(
    private val accountRepository: AccountRepository,
    private val pinHasher: PinHasher,
) {

    suspend operator fun invoke(
        username: String,
        pin: String,
    ): Resource<Boolean> {

        return when (
            val result = accountRepository.getPinHash(username)
        ) {
            is Resource.Success -> {
                val hashedPin = result.data
                    ?: return Resource.Error(DataError.AccountNotFound)

                Resource.Success(
                        pinHasher.verify(
                                pin = pin,
                                hash = hashedPin,
                        )
                )
            }

            is Resource.Error -> {
                Resource.Error(result.error)
            }
        }
    }
}