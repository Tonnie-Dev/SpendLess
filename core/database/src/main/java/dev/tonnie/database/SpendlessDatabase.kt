package dev.tonnie.database

import android.accounts.Account
import androidx.room.Database
import androidx.room.RoomDatabase
import dev.tonnie.database.dao.AccountDao
import dev.tonnie.database.entity.AccountEntity

@Database(entities = [AccountEntity::class], version = 1, exportSchema = false)
abstract class SpendlessDatabase: RoomDatabase() {

    abstract val accountDao: AccountDao
}
