package com.jvg.peopleapp.home.domain.sources

import com.jvg.peopleapp.home.domain.model.Person
import com.jvg.peopleapp.home.presentation.state.RequestState
import kotlinx.coroutines.flow.Flow

interface HomeDataSource {
    fun getAllPeople(): Flow<RequestState<List<Person>>>
    fun getActivePeople(): Flow<RequestState<List<Person>>>
    fun getInactivePeople(): Flow<RequestState<List<Person>>>
    suspend fun addPerson(person: Person)
    suspend fun updatePerson(person: Person)
    suspend fun setActive(person: Person, isActive: Boolean)
    suspend fun deletePerson(person: Person)
}
