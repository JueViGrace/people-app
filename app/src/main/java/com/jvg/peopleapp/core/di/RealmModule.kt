package com.jvg.peopleapp.core.di

import com.jvg.peopleapp.core.data.local.LocalDataSource
import com.jvg.peopleapp.core.data.local.LocalDataSourceImpl
import com.jvg.peopleapp.home.data.local.model.PersonCollection
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module

val realmModule = module {
    single {
        val config = RealmConfiguration.Builder(
            schema = setOf(PersonCollection::class)
        )
            .compactOnLaunch()
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.open(config)
    }

    single<LocalDataSource<PersonCollection>> {
        LocalDataSourceImpl(get(), PersonCollection::class)
    }
}
