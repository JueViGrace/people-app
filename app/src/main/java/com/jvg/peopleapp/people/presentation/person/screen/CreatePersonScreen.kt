package com.jvg.peopleapp.people.presentation.person.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jvg.peopleapp.people.presentation.person.components.CreatePersonComponent
import com.jvg.peopleapp.people.presentation.person.viewmodel.PersonViewModel
import org.koin.core.parameter.emptyParametersHolder
import org.koin.core.parameter.parametersOf
import org.mongodb.kbson.ObjectId

data class CreatePersonScreen(val id: ObjectId? = null) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<PersonViewModel>(
            parameters = {
                if (id != null) parametersOf(id) else emptyParametersHolder()
            }
        )
        val state = viewModel.state.collectAsState()
        val editPerson = viewModel.newPerson

        CreatePersonComponent(
            editPerson = editPerson,
            viewModel = viewModel,
            state = state,
            navigator = navigator
        )
    }
}
