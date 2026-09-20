package dev.tonnie.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey
    @ColumnInfo(name = "username", collate = ColumnInfo.NOCASE)
    val username: String,
    @ColumnInfo(name = "encrypted_pin")
    val encryptedPin: String,
)