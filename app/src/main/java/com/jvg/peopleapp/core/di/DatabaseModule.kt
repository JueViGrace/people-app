package com.jvg.peopleapp.core.di

import com.jvg.peopleapp.core.database.helper.DbHelper
import com.jvg.peopleapp.core.database.drivers.AndroidDatabaseDriverFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        AndroidDatabaseDriverFactory(
            androidContext()
        )
    }

    single {
        DbHelper(get())
    }
}
