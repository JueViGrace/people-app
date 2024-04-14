package com.jvg.peopleapp.people.presentation.person.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.jvg.peopleapp.core.state.RequestState
import com.jvg.peopleapp.people.data.local.sources.PeopleDataSource
import com.jvg.peopleapp.people.domain.model.Person
import com.jvg.peopleapp.people.presentation.person.state.PersonDetailsState
import com.jvg.peopleapp.people.presentation.events.PeopleActions
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId

class PersonViewModel(
    private val peopleDataSource: PeopleDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : ScreenModel {

    private var _state: MutableStateFlow<PersonDetailsState> = MutableStateFlow(PersonDetailsState())
    val state: StateFlow<PersonDetailsState> = _state.asStateFlow()

    fun setAction(action: PeopleActions) {
        when (action) {
            is PeopleActions.Add -> addPerson(action.person)
            is PeopleActions.Update -> updatePerson(action.person)
            else -> {
            }
        }
    }

    fun findUser(id: ObjectId?) {
        _state.update { s ->
            s.copy(person = RequestState.Loading)
        }

        screenModelScope.launch {
            peopleDataSource.getOneById(id).collect { value ->
                _state.update { s ->
                    s.copy(person = value)
                }
            }
        }
    }

    private fun addPerson(person: Person) {
        screenModelScope.launch(ioDispatcher) {
            peopleDataSource.addPerson(person = person.toDatabase())
        }
    }

    private fun updatePerson(person: Person) {
        screenModelScope.launch(ioDispatcher) {
            peopleDataSource.updatePerson(person = person)
        }
    }
}
