package com.jvg.peopleapp.core.data.local

import com.jvg.peopleapp.home.data.local.model.PersonCollection
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class RealmDb {
    var realm: Realm = configureRealm()

    init {
        configureRealm()
    }

    private fun configureRealm(): Realm {
        val config = RealmConfiguration.Builder(
            schema = setOf(PersonCollection::class)
        )
            .compactOnLaunch()
            .deleteRealmIfMigrationNeeded()
            .build()

        return Realm.open(config)
    }
}
