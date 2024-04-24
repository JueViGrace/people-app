package com.jvg.peopleapp.people.presentation.person.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import com.jvg.peopleapp.core.presentation.ui.components.ErrorScreen
import com.jvg.peopleapp.core.presentation.ui.components.LoadingScreen
import com.jvg.peopleapp.home.routes.DetailScreens
import com.jvg.peopleapp.home.routes.HomeTabs
import com.jvg.peopleapp.people.presentation.person.components.PersonDetailsComponent
import com.jvg.peopleapp.people.presentation.person.events.PersonEvent
import com.jvg.peopleapp.people.presentation.person.viewmodel.PersonViewModel
import org.koin.core.parameter.parametersOf

data class PersonDetailsScreen(val id: String) : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val tabNavigator = LocalTabNavigator.current
        val viewModel = getScreenModel<PersonViewModel>(parameters = { parametersOf(id) })
        val state by viewModel.state.collectAsState()

        state.person.DisplayResult(
            onLoading = {
                LoadingScreen()
            },
            onError = { message ->
                ErrorScreen(message = message)
            },
            onSuccess = { person ->
                PersonDetailsComponent(
                    person = person,
                    popBack = {
                        navigator.pop()
                    },
                    onAdd = { id ->
                        navigator.push(DetailScreens.CreatePerson(id).screen)
                    },
                    onPayment = { id ->
                        if (tabNavigator.current != HomeTabs.Payments.tab){
                            navigator.push(DetailScreens.PaymentDetails(id).screen)
                        }
                    },
                    onDelete = {
                        viewModel.onEvent(PersonEvent.DeletePerson)
                        navigator.pop()
                    }
                )
            }
        )
    }
}
