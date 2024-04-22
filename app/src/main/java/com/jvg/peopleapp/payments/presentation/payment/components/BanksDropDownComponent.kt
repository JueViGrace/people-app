package com.jvg.peopleapp.payments.presentation.payment.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.jvg.peopleapp.R
import com.jvg.peopleapp.core.presentation.ui.components.CustomOutlinedTextField
import com.jvg.peopleapp.core.presentation.ui.components.CustomText
import com.jvg.peopleapp.payments.domain.model.Banks

@Composable
fun BanksDropDownComponent(
    modifier: Modifier = Modifier,
    label: String,
    placeholder: String,
    painter: Painter? = null,
    items: List<Banks>,
    onValueChanged: (Banks) -> Unit,
    supportingText: String? = null,
    errorStatus: Boolean = false
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    var selectedBank by remember {
        mutableStateOf("")
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
            value = selectedBank,
            onValueChanged = {
                selectedBank = it
            },
            label = { CustomText(text = label) },
            placeholder = { CustomText(text = placeholder) },
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = "",
                    modifier = Modifier.clickable {
                        expanded = !expanded
                    }
                )
            },
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
            supportingText = if (supportingText != null) {
                { CustomText(text = supportingText) }
            } else {
                null
            },
            singleLine = true,
            maxLines = 1,
            isError = errorStatus,
            readOnly = true
        )

        DropdownMenu(
            modifier = Modifier
                .width(
                    with(LocalDensity.current) { textFieldSize.width.toDp() }
                ),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_person_24px),
                                contentDescription = ""
                            )
                            CustomText(
                                text = item.name,
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    },
                    onClick = {
                        selectedBank = item.name
                        onValueChanged(item)
                        expanded = false
                    }
                )
            }
        }
    }
}
