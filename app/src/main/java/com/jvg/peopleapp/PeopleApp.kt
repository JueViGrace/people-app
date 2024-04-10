package com.jvg.peopleapp

import android.app.Application
import com.jvg.peopleapp.core.di.realmModule
import com.jvg.peopleapp.home.di.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PeopleApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()

            androidContext(this@PeopleApp)

            modules(realmModule(), homeModule)
        }
    }
}
