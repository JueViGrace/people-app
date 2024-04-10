package com.jvg.peopleapp.home.presentation.details.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.jvg.peopleapp.home.domain.model.Person
import com.jvg.peopleapp.home.domain.sources.HomeDataSource
import com.jvg.peopleapp.home.presentation.events.PeopleActions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PersonViewModel(
    private val homeDataSource: HomeDataSource
) : ScreenModel {

    fun setAction(action: PeopleActions) {
        when (action) {
            is PeopleActions.Add -> addPerson(action.person)
            is PeopleActions.Update -> updatePerson(action.person)
            else -> {
            }
        }
    }

    private fun addPerson(person: Person) {
        screenModelScope.launch(Dispatchers.IO) {
            homeDataSource.addPerson(person = person)
        }
    }

    private fun updatePerson(person: Person) {
        screenModelScope.launch(Dispatchers.IO) {
            homeDataSource.updatePerson(person = person)
        }
    }
}
