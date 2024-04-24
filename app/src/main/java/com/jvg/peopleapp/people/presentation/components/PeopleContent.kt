package com.jvg.peopleapp.people.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jvg.peopleapp.core.presentation.ui.components.DeleteAlertDialog
import com.jvg.peopleapp.core.presentation.ui.components.ErrorScreen
import com.jvg.peopleapp.core.presentation.ui.components.LoadingScreen
import com.jvg.peopleapp.core.state.RequestState
import com.jvg.peopleapp.people.domain.model.Person

@Composable
fun PeopleContent(
    modifier: Modifier = Modifier,
    people: RequestState<List<Person>>,
    onSelect: ((String) -> Unit)? = null,
    onActive: ((String?, Boolean) -> Unit)? = null,
    onDelete: ((String?) -> Unit)? = null
) {
    var showDialog by remember {
        mutableStateOf(false)
    }
    var personToDelete: Person? by remember {
        mutableStateOf(null)
    }

    if (showDialog) {
        DeleteAlertDialog(
            questionText = "¿Seguro que quieres borrar a ${personToDelete?.name}?",
            onShowChange = {
                showDialog = it
            },
            onDismiss = {
                personToDelete = null
            },
            onConfirm = {
                personToDelete?.let { person ->
                    onDelete?.invoke(person.id)
                }
                personToDelete = null
            }
        )
    }

    people.DisplayResult(
        onLoading = { LoadingScreen() },
        onError = { message ->
            ErrorScreen(message)
        },
        onSuccess = { list ->
            if (list.isNotEmpty()) {
                LazyColumn(
                    modifier = modifier
                        .padding(horizontal = 5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top)
                ) {
                    items(
                        items = list,
                        key = { person -> person.id }
                    ) { person ->
                        ListPersonComponent(
                            person = person,
                            showActive = person.active,
                            onSelect = { id ->
                                onSelect?.invoke(id)
                            },
                            onActive = { id, isActive ->
                                onActive?.invoke(id, isActive)
                            },
                            onDelete = { selectedPerson ->
                                personToDelete = selectedPerson
                                showDialog = true
                            }
                        )
                    }
                }
            } else {
                ErrorScreen()
            }
        }
    )
}
