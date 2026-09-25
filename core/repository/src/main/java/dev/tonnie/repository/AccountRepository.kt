package dev.tonnie.repository

import dev.tonnie.model.Account
import dev.tonnie.exceptions.Resource

interface AccountRepository {

    suspend fun usernameExists(username: String): Resource<Boolean>

    suspend fun getAccount(username: String): Resource<Account?>

    suspend fun createAccount(account: Account, pinHash: String): Resource<Unit>
}