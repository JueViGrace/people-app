package com.jvg.peopleapp

import android.app.Application
import com.jvg.peopleapp.core.di.dispatchersModule
import com.jvg.peopleapp.core.di.realmModule
import com.jvg.peopleapp.dashboard.di.homeModule
import com.jvg.peopleapp.person.di.personModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SelfManagerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()

            androidContext(this@SelfManagerApp)

            modules(realmModule, dispatchersModule, personModule, homeModule)
        }
    }
}
