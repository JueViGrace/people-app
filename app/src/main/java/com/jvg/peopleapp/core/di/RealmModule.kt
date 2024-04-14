package com.jvg.peopleapp.core.di

import com.jvg.peopleapp.core.data.local.LocalDataSource
import com.jvg.peopleapp.core.data.local.LocalDataSourceImpl
import com.jvg.peopleapp.people.data.local.model.PersonCollection
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module
import kotlin.reflect.KClass

val realmModule = module {
    single {
        Realm.open(
            configuration = RealmConfiguration.create(
                schema = setOf(PersonCollection::class)
            )
        )
    }

    single { PersonCollection::class }

    single<LocalDataSource<PersonCollection>> {
        LocalDataSourceImpl(get(), get<KClass<PersonCollection>>())
    }


}
