package com.jvg.peopleapp.home.presentation.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jvg.peopleapp.R
import com.jvg.peopleapp.home.domain.model.Person
import com.jvg.peopleapp.home.presentation.state.RequestState
import com.jvg.peopleapp.core.presentation.ui.components.CustomText
import com.jvg.peopleapp.core.presentation.ui.components.ErrorScreen
import com.jvg.peopleapp.core.presentation.ui.components.LoadingScreen

@Composable
fun DashboardContent(
    modifier: Modifier = Modifier,
    people: RequestState<List<Person>>,
    showActive: Boolean = true,
    onSelect: ((Person) -> Unit)? = null,
    onActive: ((Person, Boolean) -> Unit)? = null,
    onDelete: ((Person) -> Unit)? = null
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
                            onDelete?.invoke(person)
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

    Column(
        modifier.fillMaxWidth()
    ) {
        CustomText(
            modifier = Modifier.fillMaxWidth(),
            text = if (showActive) {
                stringResource(R.string.personas_activas)
            } else {
                stringResource(R.string.personas_inactivas)
            },
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )

        people.DisplayResult(
            onLoading = { LoadingScreen() },
            onError = { ErrorScreen(people.getErrorMessage()) },
            onSuccess = { list ->
                if (list.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(horizontal = 5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(3.dp, Alignment.Top)
                    ) {
                        items(
                            items = list,
                            key = { employee -> employee._id.toString() }
                        ) { person ->
                            PersonComponent(
                                person = person,
                                showActive = person.active,
                                onSelect = {
                                    onSelect?.invoke(it)
                                },
                                onActive = { selectedPerson, isActive ->
                                    onActive?.invoke(selectedPerson, isActive)
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
}
