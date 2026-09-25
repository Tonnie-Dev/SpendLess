package dev.tonnie.data.mappers

import dev.tonnie.database.entity.AccountEntity
import dev.tonnie.model.Account

fun AccountEntity.toModel(): Account = Account(username = username)

fun Account.toEntity(pinHash: String): AccountEntity = AccountEntity(
        username = username,
        pinHash = pinHash
)