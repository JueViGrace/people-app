package com.jvg.peopleapp.home.data.local.sources

import android.util.Log
import com.jvg.peopleapp.core.common.Constants.DB_ERROR_MESSAGE
import com.jvg.peopleapp.core.data.local.LocalDataSource
import com.jvg.peopleapp.core.state.RequestState
import com.jvg.peopleapp.home.data.local.model.PersonCollection
import com.jvg.peopleapp.home.domain.model.Person
import io.realm.kotlin.ext.query
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.mongodb.kbson.ObjectId

class PeopleDataSourceImpl(
    private val localDataSource: LocalDataSource<PersonCollection>,
    private val ioDispatcher: CoroutineDispatcher
) : PeopleDataSource {

    override fun getAllPeople(): Flow<RequestState<List<Person>>> = flow {
        emit(RequestState.Loading)

        localDataSource.findAll().catch { e ->
            emit(RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE))
        }.collect { list ->
            if (list.isNotEmpty()) {
                emit(
                    RequestState.Success(
                        data = list.map { personCollection -> personCollection.toDomain() }
                    )
                )
            } else {
                emit(RequestState.Error(message = "There are not people"))
            }
        }
    }.flowOn(ioDispatcher)

    override fun getActivePeople(): Flow<RequestState<List<Person>>> = flow {
        emit(RequestState.Loading)

        localDataSource.findWithOptions(query = "active == $0", true).catch { e ->
            emit(RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE))
        }.collect { list ->
            if (list.isNotEmpty()) {
                emit(
                    RequestState.Success(
                        data = list.map { personCollection -> personCollection.toDomain() }
                    )
                )
            } else {
                emit(RequestState.Error(message = "There are not active people"))
            }
        }
    }.flowOn(ioDispatcher)

    override fun getInactivePeople(): Flow<RequestState<List<Person>>> = flow {
        emit(RequestState.Loading)

        localDataSource.findWithOptions(query = "active == $0", false).catch { e ->
            emit(RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE))
        }.collect { list ->
            if (list.isNotEmpty()) {
                emit(
                    RequestState.Success(
                        data = list.map { personCollection -> personCollection.toDomain() }
                    )
                )
            } else {
                emit(RequestState.Error(message = "There are not inactive people"))
            }
        }
    }.flowOn(ioDispatcher)

    override fun getOneById(id: ObjectId?): Flow<RequestState<Person>> = flow {
        emit(RequestState.Loading)

        localDataSource.findOneById(id).catch { e ->
            emit(RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE))
        }.collect { pc ->
            if (pc != null) {
                emit(
                    RequestState.Success(
                        data = pc.toDomain()
                    )
                )
            } else {
                emit(RequestState.Error(message = "Person doesn't exists"))
            }
        }
    }.flowOn(ioDispatcher)

    override suspend fun addPerson(person: PersonCollection) {
        localDataSource.add(person)
    }

    override suspend fun updatePerson(person: Person) {
        val queriedPerson = localDataSource.realm.query<PersonCollection>(
            query = "_id == $0",
            person.id
        ).first().find()

        localDataSource.realm.write {
            queriedPerson?.let { per ->
                findLatest(per)?.let { currentPerson ->
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
        }
    }

    override suspend fun setActive(id: ObjectId?, isActive: Boolean) {
        localDataSource.realm.write {
            try {
                val queriedPerson = query<PersonCollection>(query = "_id == $0", id)
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

    override suspend fun deletePerson(id: ObjectId?) {
        localDataSource.delete(id)
    }
}
