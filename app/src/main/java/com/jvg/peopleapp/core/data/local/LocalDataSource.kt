package com.jvg.peopleapp.core.data.local

import io.realm.kotlin.Realm
import io.realm.kotlin.types.RealmObject
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId
import kotlin.reflect.KClass

interface LocalDataSource<T : RealmObject> {
    val clazz: KClass<T>
    val realm: Realm

    fun findAll(): Flow<List<T>>

    fun findOneById(id: ObjectId?): Flow<T?>

    fun findWithOptions(query: String, vararg params: Any?): Flow<List<T>>

    suspend fun add(c: RealmObject)

    suspend fun delete(id: ObjectId?)
}
