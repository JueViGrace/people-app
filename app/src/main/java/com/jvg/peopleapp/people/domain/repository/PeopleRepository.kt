package com.jvg.peopleapp.people.domain.repository

import com.jvg.peopleapp.core.common.Constants.DB_ERROR_MESSAGE
import com.jvg.peopleapp.core.state.RequestState
import com.jvg.peopleapp.people.data.local.sources.PeopleDataSource
import com.jvg.peopleapp.people.domain.model.Person
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PeopleRepository(
    private val peopleDataSource: PeopleDataSource,
) {
    fun getAllPeople(): Flow<RequestState<List<Person>>> = flow {
        emit(RequestState.Loading)

        try {
            peopleDataSource.getAllPeople()
                .catch { e ->
                    RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE)
                }
                .collect { list ->
                    if (list.isNotEmpty()) {
                        emit(RequestState.Success(data = list))
                    } else {
                        emit(RequestState.Error(message = "No existen personas"))
                    }
                }
        } catch (e: Exception) {
            emit(RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE))
        }
    }.flowOn(Dispatchers.IO)

    fun getActivePeople(): Flow<RequestState<List<Person>>> = flow {
        emit(RequestState.Loading)

        try {
            peopleDataSource.getActivePeople()
                .catch { e ->
                    RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE)
                }
                .collect { list ->
                    if (list.isNotEmpty()) {
                        emit(RequestState.Success(data = list))
                    } else {
                        emit(RequestState.Error(message = "Lista vacía"))
                    }
                }
        } catch (e: Exception) {
            emit(RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE))
        }
    }.flowOn(Dispatchers.IO)

    fun getInactivePeople(): Flow<RequestState<List<Person>>> = flow {
        emit(RequestState.Loading)

        try {
            peopleDataSource.getInactivePeople()
                .catch { e ->
                    RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE)
                }
                .collect { list ->
                    if (list.isNotEmpty()) {
                        emit(RequestState.Success(data = list))
                    } else {
                        emit(RequestState.Error(message = "Lista vacía"))
                    }
                }
        } catch (e: Exception) {
            emit(RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE))
        }
    }.flowOn(Dispatchers.IO)

    fun getOneById(id: String): Flow<RequestState<Person>> = flow {
        emit(RequestState.Loading)

        try {
            peopleDataSource.getOneById(id = id)
                .catch { e ->
                    RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE)
                }
                .collect { person ->
                    if (person.id.isNotEmpty()) {
                        emit(RequestState.Success(data = person))
                    } else {
                        emit(RequestState.Error(message = "Esta persona no existe"))
                    }
                }
        } catch (e: Exception) {
            emit(RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun addPerson(person: Person) = peopleDataSource.addPerson(person = person)

    suspend fun setActive(id: String?, isActive: Boolean) = peopleDataSource.setActive(id = id, isActive = isActive)

    suspend fun softDeletePerson(id: String) = peopleDataSource.softDeletePerson(id = id)

    suspend fun deletePerson(id: String) = peopleDataSource.deletePerson(id = id)

    suspend fun deletePeople() = peopleDataSource.deletePeople()
}
