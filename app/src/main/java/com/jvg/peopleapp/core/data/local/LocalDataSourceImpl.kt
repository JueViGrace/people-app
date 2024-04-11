package com.jvg.peopleapp.core.data.local

import io.realm.kotlin.Realm
import io.realm.kotlin.ext.asFlow
import io.realm.kotlin.types.RealmObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId
import kotlin.reflect.KClass

class LocalDataSourceImpl<T : RealmObject>(
    override val realm: Realm,
    override val clazz: KClass<T>,
) : LocalDataSource<T> {

    override fun findAll(): Flow<List<T>> {
        return realm.query(clazz).asFlow().map { value ->
            value.list
        }
    }

    override fun findOneById(id: ObjectId?): Flow<T?> {
        return realm.query(
            clazz,
            query = "_id == $0",
            id
        ).first().find()?.asFlow()?.map { value ->
            value.obj
        } ?: emptyFlow()
    }

    override fun findWithOptions(query: String, vararg params: Any?): Flow<List<T>> {
        return realm.query(
            clazz,
            query,
            args = params
        ).asFlow().map { value ->
            value.list
        }
    }

    override suspend fun add(c: RealmObject) {
        realm.write { copyToRealm(c) }
    }

    override suspend fun delete(id: ObjectId?) {
        realm.write {
            val query = query(clazz, query = "_id == $0", id).find()
            delete(query)
        }
    }
}
