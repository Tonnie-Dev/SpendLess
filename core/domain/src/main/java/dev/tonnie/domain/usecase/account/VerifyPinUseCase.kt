package dev.tonnie.domain.usecase.account

import dev.tonnie.domain.hashing.PinHasher
import dev.tonnie.repository.AccountRepository

class VerifyPinUseCase(
    private val accountRepository: AccountRepository,
    private val pinHasher: PinHasher
) {

    suspend operator fun invoke(pin: String): Boolean {

        return false
    }
}