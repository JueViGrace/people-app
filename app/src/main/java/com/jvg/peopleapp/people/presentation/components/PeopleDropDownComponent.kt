package com.jvg.peopleapp.people.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.jvg.peopleapp.R
import com.jvg.peopleapp.core.presentation.ui.components.CustomOutlinedTextField
import com.jvg.peopleapp.core.presentation.ui.components.CustomText
import com.jvg.peopleapp.core.state.RequestState
import com.jvg.peopleapp.people.domain.model.Person

@Composable
fun PeopleDropDownComponent(
    modifier: Modifier = Modifier,
    label: String,
    placeholder: String,
    painter: Painter? = null,
    people: RequestState<List<Person>>,
    value:String,
    onValueChanged: (String) -> Unit,
    onPersonSelected: (Person) -> Unit,
    supportingText: String? = null,
    errorStatus: Boolean = false
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    var textFieldSize by remember {
        mutableStateOf(Size.Zero)
    }

    val icon = if (expanded) {
        Icons.Filled.ArrowDropUp
    } else {
        Icons.Filled.ArrowDropDown
    }

    Box {
        CustomOutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                }
                .padding(horizontal = 5.dp),
            value = value,
            onValueChanged = { newValue ->
                onValueChanged(newValue)
            },
            label = { CustomText(text = label) },
            placeholder = { CustomText(text = placeholder) },
            supportingText = if (supportingText != null) {
                { CustomText(text = supportingText) }
            } else {
                null
            },
            isError = errorStatus,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    expanded = true
                }
            ),
            leadingIcon = if (painter != null) {
                {
                    Icon(
                        painter = painter,
                        contentDescription = "",
                    )
                }
            } else {
                null
            },
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = "",
                    modifier = Modifier.clickable {
                        expanded = !expanded
                    }
                )
            }
        )

        people.DisplayResult(
            onError = { _ -> },
            onLoading = {},
            onSuccess = { list ->
                DropdownMenu(
                    modifier = Modifier
                        .width(
                            with(LocalDensity.current) { textFieldSize.width.toDp() }
                        ),
                    offset = DpOffset(
                        y = with(LocalDensity.current) { textFieldSize.height.toDp() },
                        x = 0.dp
                    ),
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    list.forEach { person ->
                        DropdownMenuItem(
                            text = {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(
                                        5.dp,
                                        Alignment.CenterHorizontally
                                    ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_person_24px),
                                        contentDescription = ""
                                    )
                                    CustomText(
                                        text = "${person.name} ${person.lastname}",
                                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            },
                            onClick = {
                                onPersonSelected(person)
                                expanded = false
                            }
                        )
                    }
                }
            }
        )
    }
}
