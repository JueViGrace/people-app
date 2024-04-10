package com.jvg.peopleapp.home.data.local

import android.util.Log
import com.jvg.peopleapp.core.data.local.RealmDb
import com.jvg.peopleapp.home.data.local.model.PersonCollection
import com.jvg.peopleapp.home.domain.model.Person
import com.jvg.peopleapp.home.domain.sources.HomeDataSource
import com.jvg.peopleapp.home.presentation.state.RequestState
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class HomeDataSourceImpl(
    private val realmDb: RealmDb
) : HomeDataSource {
    override fun getAllPeople(): Flow<RequestState<List<Person>>> {
        return realmDb.realm?.query<PersonCollection>()?.asFlow()?.map { result ->
            RequestState.Success(
                data = result.list.map { personCollection -> personCollection.toDomain() }
            )
        } ?: flow { RequestState.Error(message = "Realm is not available.") }
    }

    override fun getActivePeople(): Flow<RequestState<List<Person>>> {
        return realmDb.realm?.query<PersonCollection>(query = "active == $0", true)?.asFlow()?.map { result ->
            RequestState.Success(
                data = result.list.map { personCollection -> personCollection.toDomain() }
            )
        } ?: flow { RequestState.Error(message = "Realm is not available.") }
    }

    override fun getInactivePeople(): Flow<RequestState<List<Person>>> {
        return realmDb.realm?.query<PersonCollection>(query = "active == $0", false)?.asFlow()?.map { result ->
            RequestState.Success(
                data = result.list.map { personCollection -> personCollection.toDomain() }
            )
        } ?: flow { RequestState.Error(message = "Realm is not available.") }
    }

    override suspend fun addPerson(person: Person) {
        realmDb.realm?.write { copyToRealm(person.toDatabase()) }
    }

    override suspend fun updatePerson(person: Person) {
        realmDb.realm?.write {
            try {
                val queriedPerson = query<PersonCollection>(query = "_id == $0", person._id)
                    .first().find()
                queriedPerson?.let { p: PersonCollection ->
                    findLatest(p)?.let { currentPerson ->
                        currentPerson.name = person.name
                        currentPerson.lastname = person.lastname
                        currentPerson.code = person.code
                        currentPerson.phone = person.phone
                        currentPerson.email = person.email
                        currentPerson.startsAt = person.startsAt
                        currentPerson.finishesAt = person.finishesAt
                        currentPerson.active = person.active
                    }
                }
            } catch (e: Exception) {
                Log.e("Update person", "updatePerson: ${e.message}")
                e.message
            }
        }
    }

    override suspend fun setActive(person: Person, isActive: Boolean) {
        realmDb.realm?.write {
            try {
                val queriedPerson = query<PersonCollection>(query = "_id == $0", person._id)
                    .first().find()
                queriedPerson?.let { currentPerson ->
                    currentPerson.apply {
                        active = isActive
                    }
                }
            } catch (e: Exception) {
                Log.e("Set active person", "setActive: ${e.message}")
                e.message
            }
        }
    }

    override suspend fun deletePerson(person: Person) {
        realmDb.realm?.write {
            try {
                val queriedPerson = query<PersonCollection>(query = "_id == $0", person._id)
                    .first().find()
                queriedPerson?.let { p ->
                    findLatest(p)?.let { currentPerson ->
                        delete(currentPerson)
                    }
                }
            } catch (e: Exception) {
                Log.e("Delete person", "deletePerson: ${e.message}")
                e.message
            }
        }
    }
}
