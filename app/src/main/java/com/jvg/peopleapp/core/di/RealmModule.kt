package com.jvg.peopleapp.core.di

import com.jvg.peopleapp.core.common.Constants
import com.jvg.peopleapp.core.data.local.LocalDataSource
import com.jvg.peopleapp.core.data.local.LocalDataSourceImpl
import com.jvg.peopleapp.person.data.local.model.PersonCollection
import com.jvg.peopleapp.person.data.local.sources.PeopleDataSource
import com.jvg.peopleapp.person.data.local.sources.PeopleDataSourceImpl
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.core.qualifier.named
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
