package com.jvg.peopleapp.people.data.local.sources

import com.jvg.peopleapp.people.domain.model.Person
import kotlinx.coroutines.flow.Flow

interface PeopleDataSource {
    suspend fun getAllPeople(): Flow<List<Person>>
    suspend fun getActivePeople(): Flow<List<Person>>
    suspend fun getInactivePeople(): Flow<List<Person>>
    suspend fun getOneById(id: String): Flow<Person>
    suspend fun addPerson(person: Person)
    suspend fun setActive(id: String?, isActive: Boolean)
    suspend fun softDeletePerson(id: String)
    suspend fun deletePerson(id: String)
    suspend fun deletePeople()
}
