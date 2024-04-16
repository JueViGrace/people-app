package com.jvg.peopleapp.people.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jvg.peopleapp.R
import com.jvg.peopleapp.core.presentation.ui.components.CustomText
import com.jvg.peopleapp.core.presentation.ui.components.ErrorScreen
import com.jvg.peopleapp.core.presentation.ui.components.LoadingScreen
import com.jvg.peopleapp.core.state.RequestState
import com.jvg.peopleapp.people.domain.model.Person
import org.mongodb.kbson.ObjectId

@Composable
fun PeopleContent(
    modifier: Modifier = Modifier,
    people: RequestState<List<Person>>,
    onSelect: ((ObjectId) -> Unit)? = null,
    onActive: ((ObjectId?, Boolean) -> Unit)? = null,
    onDelete: ((ObjectId?) -> Unit)? = null
) {
    var showDialog by remember {
        mutableStateOf(false)
    }
    var personToDelete: Person? by remember {
        mutableStateOf(null)
    }

    if (showDialog) {
        AlertDialog(
            title = {
                CustomText(
                    text = stringResource(R.string.delete),
                    fontSize = MaterialTheme.typography.titleLarge.fontSize
                )
            },
            text = {
                CustomText(
                    text = "¿Seguro que quieres borrar a ${personToDelete?.name}?",
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize
                )
            },
            onDismissRequest = {
                personToDelete = null
                showDialog = false
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        personToDelete = null
                        showDialog = false
                    }
                ) {
                    CustomText(text = stringResource(R.string.cancel))
                }
            },
            confirmButton = {
                ElevatedButton(
                    onClick = {
                        personToDelete?.let { person ->
                            onDelete?.invoke(person.id)
                        }
                        showDialog = false
                        personToDelete = null
                    },
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                    )
                ) {
                    CustomText(
                        text = stringResource(R.string.delete),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.surface
                    )
                }
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
                    verticalArrangement = Arrangement.spacedBy(3.dp, Alignment.Top)
                ) {
                    items(
                        items = list,
                        key = { person -> person.id.toString() }
                    ) { person ->
                        PersonComponent(
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
