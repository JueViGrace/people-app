package com.jvg.peopleapp.people.data.local.sources

import com.jvg.peopleapp.core.state.RequestState
import com.jvg.peopleapp.people.domain.model.Person
import kotlinx.coroutines.flow.Flow

interface PeopleDataSource {
    fun getAllPeople(): Flow<RequestState<List<Person>>>
    fun getActivePeople(): Flow<RequestState<List<Person>>>
    fun getInactivePeople(): Flow<RequestState<List<Person>>>
    fun getOneById(id: String?): Flow<RequestState<Person>>
    suspend fun addPerson(person: Person)
    suspend fun setActive(id: String?, isActive: Boolean)
    suspend fun deletePerson(id: String?)
}
