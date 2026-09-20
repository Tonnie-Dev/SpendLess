package dev.tonnie.database.dao

import androidx.room.Dao
import androidx.room.Query
import dev.tonnie.database.entity.AccountEntity

@Dao
interface AccountDao: BaseDao<AccountEntity>{

    @Query("""
        SELECT EXISTS(
        SELECT 1
        FROM accounts
        WHERE username = :username
        )
        
    """
    )
    suspend fun usernameExists(username: String): Boolean

    @Query("SELECT * FROM accounts WHERE username = :username LIMIT 1")
    suspend fun getAccount(username: String): AccountEntity?
}
