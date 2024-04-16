package com.jvg.peopleapp.people.presentation.navigation.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import com.jvg.peopleapp.R
import com.jvg.peopleapp.core.presentation.ui.components.AppBar
import com.jvg.peopleapp.core.presentation.ui.components.FABComponent
import com.jvg.peopleapp.home.routes.DetailScreens
import com.jvg.peopleapp.people.presentation.components.PeopleContent
import com.jvg.peopleapp.people.presentation.events.PeopleEvents
import com.jvg.peopleapp.people.presentation.viewmodel.PeopleViewModel

object PeopleScreen : Screen {
    private fun readResolve(): Any = PeopleScreen

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val tabNavigator = LocalTabNavigator.current
        val viewModel = getScreenModel<PeopleViewModel>()
        val state by viewModel.state.collectAsState()

        Scaffold(
            topBar = {
                AppBar(
                    title = tabNavigator.current.options.title,
                    icon = tabNavigator.current.options.icon,
                )
            },
            floatingActionButton = {
                FABComponent(
                    onAdd = {
                        navigator.push(
                            DetailScreens.CreatePerson().screen
                        )
                    },
                    icon = painterResource(id = R.drawable.ic_add_24px)
                )
            }
        ) { innerPadding ->
            PeopleContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = innerPadding.calculateTopPadding(),
                        bottom = innerPadding.calculateBottomPadding()
                    ),
                people = state.people,
                onSelect = { id ->
                    navigator.push(DetailScreens.PersonDetails(id).screen)
                },
                onActive = { id, isActive: Boolean ->
                    viewModel.onEvent(PeopleEvents.OnSetActive(id, isActive))
                },
                onDelete = { id ->
                    viewModel.onEvent(PeopleEvents.OnDeletePerson(id))
                }
            )
        }
    }
}
