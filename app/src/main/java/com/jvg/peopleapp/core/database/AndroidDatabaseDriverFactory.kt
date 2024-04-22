package com.jvg.peopleapp.core.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.jvg.peopleapp.SelfManagerDB

class AndroidDatabaseDriverFactory(private val context: Context) {
    fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(SelfManagerDB.Schema, context, "sefl_manager.db")
    }
}
