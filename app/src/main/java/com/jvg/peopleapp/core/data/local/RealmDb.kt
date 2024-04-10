package com.jvg.peopleapp.core.data.local

import com.jvg.peopleapp.home.data.local.model.PersonCollection
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class RealmDb {
    var realm: Realm? = null

    init {
        configureRealm()
    }

    private fun configureRealm() {
        if (realm == null || realm!!.isClosed()) {
            val config = RealmConfiguration.Builder(
                schema = setOf(PersonCollection::class)
            )
                .compactOnLaunch()
                .deleteRealmIfMigrationNeeded()
                .build()
            realm = Realm.open(config)
        }
    }
}
