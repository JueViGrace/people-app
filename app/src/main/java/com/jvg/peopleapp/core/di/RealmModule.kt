package com.jvg.peopleapp.core.di

import com.jvg.peopleapp.core.data.local.RealmDb
import org.koin.dsl.module

fun realmModule() = module {
    single {
        RealmDb()
    }
}
