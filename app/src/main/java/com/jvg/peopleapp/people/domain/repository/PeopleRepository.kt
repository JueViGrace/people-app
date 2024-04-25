package com.jvg.peopleapp.people.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.jvg.peopleapp.core.common.Constants.DB_ERROR_MESSAGE
import com.jvg.peopleapp.core.common.Constants.MIN_PAGE
import com.jvg.peopleapp.core.database.mappers.toDomain
import com.jvg.peopleapp.core.state.RequestState
import com.jvg.peopleapp.people.data.local.sources.PeopleDataSource
import com.jvg.peopleapp.people.domain.model.Person
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class PeopleRepository(
    private val peopleDataSource: PeopleDataSource,
) {
    fun getAllPeople(): Flow<RequestState<Flow<PagingData<Person>>>> = flow {
        emit(RequestState.Loading)

        try {
            peopleDataSource.getAllPeople().collect { pagingSource ->
                emit(
                    RequestState.Success(
                        data = Pager(
                            PagingConfig(
                                pageSize = MIN_PAGE,
                                prefetchDistance = 20
                            )
                        ) {
                            pagingSource
                        }
                            .flow
                            .map { value ->
                                value.map { personEntity ->
                                    personEntity.toDomain()
                                }
                            }
                    )
                )
            }
        } catch (e: Exception) {
            emit(RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE))
        }
    }.flowOn(Dispatchers.IO)

    fun getActivePeople(): Flow<RequestState<List<Person>>> = flow {
        emit(RequestState.Loading)

        try {
            peopleDataSource.getActivePeople().collect { list ->
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
            peopleDataSource.getInactivePeople().collect { list ->
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
            peopleDataSource.getOneById(id = id).collect { person ->
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

    fun searchPeople(value: String): Flow<RequestState<List<Person>>> = flow<RequestState<List<Person>>> {
        emit(RequestState.Loading)

        try {
            peopleDataSource.searchPeople(value).collect { list ->
                emit(RequestState.Success(data = list))
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
