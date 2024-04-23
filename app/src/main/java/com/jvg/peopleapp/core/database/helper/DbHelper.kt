package com.jvg.peopleapp.core.database.helper

import com.jvg.peopleapp.SelfManagerDB
import com.jvg.peopleapp.core.database.drivers.AndroidDatabaseDriverFactory
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class DbHelper(val driverFactory: AndroidDatabaseDriverFactory) {
    private var db: SelfManagerDB? = null

    private val mutex = Mutex()

    suspend fun <Result : Any> withDatabase(block: suspend (SelfManagerDB) -> Result): Result = mutex.withLock {
        if (db == null) {
            db = createDb(driverFactory)
        }

        return@withLock block(db!!)
    }

    private fun createDb(driverFactory: AndroidDatabaseDriverFactory): SelfManagerDB {
        return SelfManagerDB(driver = driverFactory.createDriver())
    }
}
