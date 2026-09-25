package dev.tonnie.domain.usecase.account

import dev.tonnie.domain.hashing.PinHasher
import dev.tonnie.exceptions.Resource
import dev.tonnie.model.Account
import dev.tonnie.repository.AccountRepository

class CreateAccountUseCase(
    private val accountRepository: AccountRepository,
    private val pinHasher: PinHasher
) {
    suspend operator fun invoke(
        username: String,
        pin: String
    ): Resource<Unit> {
        val pinHash = pinHasher.hash(pin = pin)
        val account = Account(username = username)

        return accountRepository.createAccount(
                account = account,
                pinHash = pinHash
        )
    }
}