package com.jvg.peopleapp.people.data.local.sources

import android.util.Log
import com.jvg.peopleapp.core.common.Constants.DB_ERROR_MESSAGE
import com.jvg.peopleapp.core.state.RequestState
import com.jvg.peopleapp.people.data.local.model.PersonCollection
import com.jvg.peopleapp.people.domain.model.Person
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.exceptions.RealmException
import io.realm.kotlin.ext.query
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId

class PeopleDataSourceImpl(
    private val realm: Realm,
    private val ioDispatcher: CoroutineDispatcher
) : PeopleDataSource {

    override fun getAllPeople(): Flow<RequestState<List<Person>>> = realm.query<PersonCollection>()
        .asFlow()
        .catch { e ->
            RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE)
        }
        .map { value ->
            if (value.list.isNotEmpty()) {
                RequestState.Success(value.list.map { it.toDomain() })
            } else {
                RequestState.Error(message = "No existen personas")
            }
        }
        .flowOn(ioDispatcher)

    override fun getActivePeople(): Flow<RequestState<List<Person>>> =
        realm.query<PersonCollection>(query = "active == $0", true)
            .asFlow()
            .catch { e ->
                RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE)
            }
            .map { value ->
                RequestState.Success(value.list.map { it.toDomain() })
            }
            .flowOn(ioDispatcher)

    override fun getInactivePeople(): Flow<RequestState<List<Person>>> =
        realm.query<PersonCollection>(query = "active == $0", false)
            .asFlow()
            .catch { e ->
                RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE)
            }
            .map { value ->
                RequestState.Success(data = value.list.map { it.toDomain() })
            }
            .flowOn(ioDispatcher)

    override fun getOneById(id: ObjectId?): Flow<RequestState<Person>> =
        realm.query<PersonCollection>(query = "_id == $0", id)
            .find()
            .asFlow()
            .catch { e ->
                RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE)
            }
            .map { value ->
                RequestState.Success(value.list.first().toDomain())
            }
            .flowOn(ioDispatcher)

    override suspend fun addPerson(person: Person) {
        realm.write {
            copyToRealm(person.toDatabase(), UpdatePolicy.ALL)
        }
    }

    override suspend fun setActive(id: ObjectId?, isActive: Boolean) {
        realm.write {
            try {
                val queriedPerson = query<PersonCollection>(query = "_id == $0", id)
                    .first().find()
                queriedPerson?.let { currentPerson ->
                    currentPerson.apply {
                        active = isActive
                    }
                }
            } catch (e: RealmException) {
                Log.e("Set active person", "setActive: ${e.message}")
                e.message
            }
        }
    }

    override suspend fun deletePerson(id: ObjectId?) {
        realm.write {
            try {
                val queriedPerson = query<PersonCollection>(query = "_id == $0", id)
                    .first()
                    .find()
                queriedPerson?.let { personCollection ->
                    findLatest(personCollection)?.let { currentPerson ->
                        delete(currentPerson)
                    }
                }
            } catch (e: Exception) {
                Log.e("Delete person", "deletePerson: $e")
            }
        }
    }
}
