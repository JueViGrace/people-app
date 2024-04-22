package com.jvg.peopleapp.core.di

import com.jvg.peopleapp.core.database.AndroidDatabaseDriverFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        AndroidDatabaseDriverFactory(
            androidContext()
        ).createDriver()
    }
}
