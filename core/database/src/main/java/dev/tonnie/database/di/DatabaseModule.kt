package dev.tonnie.database.di

import androidx.room.Room
import dev.tonnie.database.SpendlessDatabase
import dev.tonnie.database.dao.AccountDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
                context = androidContext(),
                klass = SpendlessDatabase::class.java,
                name = DATA_BASE_NAME
        )
                .build()
    }

    single<AccountDao> {
        get<SpendlessDatabase>().accountDao
    }
}

private const val DATA_BASE_NAME = "spend_less_database.db"