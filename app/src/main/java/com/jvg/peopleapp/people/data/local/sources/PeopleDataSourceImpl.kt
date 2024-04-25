package com.jvg.peopleapp.people.data.local.sources

import androidx.paging.PagingSource
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import app.cash.sqldelight.paging3.QueryPagingSource
import com.jvg.peopleapp.core.database.helper.DbHelper
import com.jvg.peopleapp.core.database.mappers.toDatabase
import com.jvg.peopleapp.core.database.mappers.toDomain
import com.jvg.peopleapp.core.database.mappers.toPerson
import com.jvg.peopleapp.people.domain.model.Person
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import com.jvg.peopleapp.Person as PersonEntity

class PeopleDataSourceImpl(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) : PeopleDataSource {
    override suspend fun countPeople(): Flow<Long> = scope.async {
        dbHelper.withDatabase { db ->
            db.selfManagerDBQueries
                .countPeople()
                .asFlow()
                .mapToOne(scope.coroutineContext)
        }
    }.await()

    override suspend fun getAllPeople(): Flow<PagingSource<Int, PersonEntity>> = flow {
        emit(
            scope.async {
                dbHelper.withDatabase { db ->
                    val count = db.selfManagerDBQueries.countPeople()

                    QueryPagingSource(
                        countQuery = count,
                        transacter = db.selfManagerDBQueries,
                        context = scope.coroutineContext,
                        queryProvider = db.selfManagerDBQueries::getPeople
                    )
                }
            }.await()
        )
    }

    override suspend fun getActivePeople(): Flow<List<Person>> = scope.async {
        dbHelper.withDatabase { db ->
            db.selfManagerDBQueries
                .getActivePeople()
                .asFlow()
                .mapToList(scope.coroutineContext)
                .map { list ->
                    list.map { person ->
                        person.toDomain()
                    }
                }
        }
    }.await()

    override suspend fun getInactivePeople(): Flow<List<Person>> = scope.async {
        dbHelper.withDatabase { db ->
            db.selfManagerDBQueries
                .getInactivePeople()
                .asFlow()
                .mapToList(scope.coroutineContext)
                .map { list ->
                    list.map { person ->
                        person.toDomain()
                    }
                }
        }
    }.await()

    override suspend fun searchPeople(value: String): Flow<List<Person>> = scope.async {
        dbHelper.withDatabase { db ->
            db.selfManagerDBQueries
                .searchPeople(value)
                .asFlow()
                .mapToList(scope.coroutineContext)
                .map { list ->
                    list.map { personEntity ->
                        personEntity.toDomain()
                    }
                }
        }
    }.await()

    override suspend fun getOneById(id: String): Flow<Person> = scope.async {
        dbHelper.withDatabase { db ->
            db.selfManagerDBQueries
                .getPersonById(id = id)
                .asFlow()
                .mapToList(scope.coroutineContext)
                .map { person ->
                    person.toPerson()
                }
        }
    }.await()

    override suspend fun addPerson(person: Person) {
        dbHelper.withDatabase { db ->
            db.selfManagerDBQueries.addPerson(
                person.copy(
                    id = person.id.ifEmpty {
                        UUID.randomUUID().toString()
                    }
                ).toDatabase()
            )
        }
    }

    override suspend fun setActive(id: String?, isActive: Boolean) {
        dbHelper.withDatabase { db ->
            if (id != null) {
                db.selfManagerDBQueries.updateActivePerson(isActive = isActive, id = id)
            }
        }
    }

    override suspend fun softDeletePerson(id: String) {
        dbHelper.withDatabase { db ->
            db.selfManagerDBQueries.softDeletePerson(id = id)
        }
    }

    override suspend fun deletePerson(id: String) {
        dbHelper.withDatabase { db ->
            db.selfManagerDBQueries.deletePerson(id = id)
        }
    }

    override suspend fun deletePeople() {
        dbHelper.withDatabase { db ->
            db.selfManagerDBQueries.deletePeople()
        }
    }
}
