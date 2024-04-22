package com.jvg.peopleapp.people.data.local.sources

import com.jvg.peopleapp.SelfManagerDB
import com.jvg.peopleapp.core.state.RequestState
import com.jvg.peopleapp.people.domain.model.Person
import kotlinx.coroutines.flow.Flow

class PeopleDataSourceImpl(
    db: SelfManagerDB
) : PeopleDataSource {
    private val peopleQueries = db.selfManagerDBQueries
    override fun getAllPeople(): Flow<RequestState<List<Person>>> {
        TODO("Not yet implemented")
    }

    override fun getActivePeople(): Flow<RequestState<List<Person>>> {
        TODO("Not yet implemented")
    }

    override fun getInactivePeople(): Flow<RequestState<List<Person>>> {
        TODO("Not yet implemented")
    }

    override fun getOneById(id: String?): Flow<RequestState<Person>> {
        TODO("Not yet implemented")
    }

    override suspend fun addPerson(person: Person) {
        TODO("Not yet implemented")
    }

    override suspend fun setActive(id: String?, isActive: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun deletePerson(id: String?) {
        TODO("Not yet implemented")
    }
}
