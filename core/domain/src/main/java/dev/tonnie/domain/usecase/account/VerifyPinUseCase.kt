package dev.tonnie.domain.usecase.account

import dev.tonnie.domain.hashing.PinHasher
import dev.tonnie.exceptions.Resource
import dev.tonnie.repository.AccountRepository
import dev.tonnie.repository.SessionPrefs
import kotlinx.coroutines.flow.first

class VerifyPinUseCase(
    private val accountRepository: AccountRepository,
    private val pinHasher: PinHasher,
    private val sessionPrefs: SessionPrefs,
) {

    suspend operator fun invoke(pin: String): Resource<Boolean> {

        val username = sessionPrefs.activeUsername.first() ?: return Resource.Success(false)

        when (val result = accountRepository.getPinHash(username)) {

            is Resource.Success -> {
                val hashedPin = result.data ?: return Resource.Success(false)

                return Resource.Success(pinHasher.verify(pin, hashedPin))
            }

            is Resource.Error -> return Resource.Error(error = result.error)
        }
    }
}