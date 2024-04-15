package com.jvg.peopleapp.people.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.jvg.peopleapp.core.common.Constants.DELAY
import com.jvg.peopleapp.core.state.RequestState
import com.jvg.peopleapp.people.data.local.sources.PeopleDataSource
import com.jvg.peopleapp.people.domain.model.Person
import com.jvg.peopleapp.people.domain.rules.Validator
import com.jvg.peopleapp.people.presentation.person.events.PersonEvent
import com.jvg.peopleapp.people.presentation.state.PeopleState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PeopleViewModel(
    private val peopleDataSource: PeopleDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : ScreenModel {
    private var _state: MutableStateFlow<PeopleState> = MutableStateFlow(PeopleState())

//    val state: StateFlow<PeopleState> = _state.asStateFlow()
    val state = combine(
        _state,
        peopleDataSource.getActivePeople(),
        peopleDataSource.getAllPeople(),
        peopleDataSource.getInactivePeople()
    ) { state, inactivePeople, people, activePeople ->
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

    var newPerson: Person? by mutableStateOf(null)
        private set

    init {
        _state.update { s ->
            s.copy(activePeople = RequestState.Loading, inactivePeople = RequestState.Loading)
        }
        screenModelScope.launch {
            delay(DELAY)
            peopleDataSource.getActivePeople().collect { value ->
                _state.update { s ->
                    s.copy(
                        activePeople = value
                    )
                }
            }
        }

        screenModelScope.launch {
            delay(DELAY)
            peopleDataSource.getInactivePeople().collect { value ->
                _state.update { s ->
                    s.copy(
                        inactivePeople = value
                    )
                }
            }
        }
    }

    fun onEvent(event: PersonEvent) {
        when (event) {
            is PersonEvent.DeletePerson -> {
                screenModelScope.launch {
                    if (event.id != null) {
                        peopleDataSource.deletePerson(event.id)
                    }

                    _state.value.selectedPerson?.id?.let { id ->
                        peopleDataSource.deletePerson(id)
                    }
                }
            }
            is PersonEvent.EditPerson -> {
                newPerson = event.person
            }
            is PersonEvent.OnActiveChanged -> {
                newPerson = newPerson?.copy(
                    active = event.value
                )
            }
            is PersonEvent.OnCodeChanged -> {
                newPerson = newPerson?.copy(
                    code = event.value
                )
            }
            is PersonEvent.OnEmailChanged -> {
                newPerson = newPerson?.copy(
                    email = event.value
                )
            }
            is PersonEvent.OnFinishesAtChanged -> {
                newPerson = newPerson?.copy(
                    finishesAt = event.value
                )
            }
            is PersonEvent.OnLastNameChanged -> {
                newPerson = newPerson?.copy(
                    lastname = event.value
                )
            }
            is PersonEvent.OnNameChanged -> {
                newPerson = newPerson?.copy(
                    name = event.value
                )
            }
            is PersonEvent.OnPhoneChanged -> {
                newPerson = newPerson?.copy(
                    phone = event.value
                )
            }
            is PersonEvent.OnStartsAtChanged -> {
                newPerson = newPerson?.copy(
                    startsAt = event.value
                )
            }
            PersonEvent.SavePerson -> {
                newPerson?.let { person: Person ->
                    val result = Validator.validatePerson(person)
                    val errors = listOfNotNull(
                        result.nameError,
                        result.lastnameError,
                        result.codeError,
                        result.phoneError,
                        result.emailError,
                        result.startsAtError,
                        result.finishesAtError
                    )

                    if (errors.isEmpty()) {
                        _state.update { peopleState ->
                            peopleState.copy(
                                nameError = null,
                                lastNameError = null,
                                codeError = null,
                                phoneError = null,
                                emailError = null,
                                startsAtError = null,
                                finishesAtError = null,
                                errors = false
                            )
                        }
                        screenModelScope.launch {
                            peopleDataSource.addPerson(person.toDatabase())
                        }
                    } else {
                        _state.update { peopleState ->
                            peopleState.copy(
                                nameError = result.nameError,
                                lastNameError = result.lastnameError,
                                codeError = result.codeError,
                                phoneError = result.phoneError,
                                emailError = result.emailError,
                                startsAtError = result.startsAtError,
                                finishesAtError = result.finishesAtError,
                                errors = true
                            )
                        }
                    }
                }
            }
            is PersonEvent.SelectedPerson -> {
                _state.update { peopleState ->
                    peopleState.copy(
                        selectedPerson = event.person
                    )
                }
            }
            PersonEvent.OnAddNewPerson -> {
                newPerson = Person(
                    name = "",
                    lastname = "",
                    code = "",
                    phone = "",
                    email = "",
                    startsAt = "",
                    finishesAt = "",
                    active = true
                )
            }

            is PersonEvent.SetActive -> {
                screenModelScope.launch {
                    peopleDataSource.setActive(event.id, event.isActive)
                }
            }

            PersonEvent.DissmissPerson -> {
                screenModelScope.launch {
                    _state.update { peopleState ->
                        peopleState.copy(
                            nameError = null,
                            lastNameError = null,
                            codeError = null,
                            phoneError = null,
                            emailError = null,
                            startsAtError = null,
                            finishesAtError = null,
                            errors = null,
                            selectedPerson = null
                        )
                    }
                    newPerson = null
                }
            }
        }
    }
}
