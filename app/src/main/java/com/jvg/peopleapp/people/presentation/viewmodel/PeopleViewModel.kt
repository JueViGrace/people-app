package com.jvg.peopleapp.people.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.jvg.peopleapp.people.data.local.sources.PeopleDataSource
import com.jvg.peopleapp.people.presentation.events.PeopleEvents
import com.jvg.peopleapp.people.presentation.state.PeopleState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PeopleViewModel(
    private val peopleDataSource: PeopleDataSource,
) : ScreenModel {
    private var _state: MutableStateFlow<PeopleState> = MutableStateFlow(PeopleState())
    val state = combine(
        _state,
        peopleDataSource.getAllPeople(),
        peopleDataSource.getActivePeople(),
        peopleDataSource.getInactivePeople()
    ) { state, people, activePeople, inactivePeople ->
        state.copy(
            people = people,
            activePeople = activePeople,
            inactivePeople = inactivePeople
        )
    }.stateIn(
        screenModelScope,
        SharingStarted.WhileSubscribed(5000L),
        PeopleState()
    )

    fun onEvent(event: PeopleEvents) {
        when (event) {
            is PeopleEvents.OnDeletePerson -> {
                screenModelScope.launch(Dispatchers.IO) {
                    peopleDataSource.deletePerson(event.id)
                }
            }
            is PeopleEvents.OnSetActive -> {
                screenModelScope.launch(Dispatchers.IO) {
                    peopleDataSource.setActive(id = event.id, isActive = event.isActive)
                }
            }
        }
    }
}
