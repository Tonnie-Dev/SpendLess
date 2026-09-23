package dev.tonnie.data.repository

import dev.tonnie.common.utils.safeIoCall
import dev.tonnie.data.mappers.toEntity
import dev.tonnie.data.mappers.toModel
import dev.tonnie.database.dao.AccountDao
import dev.tonnie.exceptions.Resource
import dev.tonnie.model.Account
import dev.tonnie.repository.AccountRepository

class AccountRepositoryImpl(private val accountDao: AccountDao) : AccountRepository {
    override suspend fun usernameExists(username: String): Resource<Boolean> {
        return safeIoCall { accountDao.usernameExists(username) }
    }

    override suspend fun getAccount(username: String): Resource<Account?> {
        return safeIoCall { accountDao.getAccount(username)?.toModel() }
    }

    override suspend fun createAccount(
        account: Account,
        encryptedPin: String
    ): Resource<Unit> {
        return safeIoCall {
           val entity = account.toEntity(encryptedPin = encryptedPin)
            accountDao.insert(entity) }
    }
}