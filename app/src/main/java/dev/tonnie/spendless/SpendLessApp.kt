package dev.tonnie.spendless

import android.app.Application
import dev.tonnie.authentication.di.authenticationModule
import dev.tonnie.data.di.dataModule
import dev.tonnie.database.di.databaseModule
import dev.tonnie.domain.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SpendLessApp : Application(){
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@SpendLessApp)
            modules(
                    databaseModule,
                    dataModule,
                    domainModule,
                    authenticationModule
            )
        }
    }
}