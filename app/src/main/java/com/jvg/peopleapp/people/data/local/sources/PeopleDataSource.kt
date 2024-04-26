package com.jvg.peopleapp.people.data.local.sources

import androidx.paging.PagingData
import com.jvg.peopleapp.people.domain.model.Person
import kotlinx.coroutines.flow.Flow
import com.jvg.peopleapp.Person as PersonEntity

interface PeopleDataSource {
    suspend fun getAllPeople(): Flow<Flow<PagingData<PersonEntity>>>
    suspend fun getActivePeople(): Flow<List<Person>>
    suspend fun getInactivePeople(): Flow<List<Person>>
    suspend fun searchPeople(value: String): Flow<List<Person>>
    suspend fun getOneById(id: String): Flow<Person>
    suspend fun addPerson(person: Person)
    suspend fun setActive(id: String?, isActive: Boolean)
    suspend fun softDeletePerson(id: String)
    suspend fun deletePerson(id: String)
    suspend fun deletePeople()
}
