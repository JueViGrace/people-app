package com.jvg.peopleapp.core.di

import com.jvg.peopleapp.payments.data.local.model.PaymentCollection
import com.jvg.peopleapp.people.data.local.model.PersonCollection
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module

val realmModule = module {
    single {
        val config = RealmConfiguration.Builder(
            schema = setOf(PersonCollection::class, PaymentCollection::class)
        ).schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.open(
            configuration = config
        )
    }
}
