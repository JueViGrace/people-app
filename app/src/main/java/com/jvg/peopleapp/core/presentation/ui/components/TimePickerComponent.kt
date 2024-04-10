package com.jvg.peopleapp.core.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.jvg.peopleapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerComponent(
    value: String,
    painterResource: Painter,
    onTextSelected: (String) -> Unit,
    errorStatus: Boolean = false
) {
    var openDialog by remember {
        mutableStateOf(false)
    }

    val state = rememberTimePickerState()

    var hourSelected = when {
        state.hour < 10 && state.minute < 10 -> {
            "0${state.hour}:0${state.minute}"
        }
        state.hour < 10 -> {
            "0${state.hour}:${state.minute}"
        }
        state.minute < 10 -> {
            "${state.hour}:0${state.minute}"
        }

        else -> {
            "${state.hour}:${state.minute}"
        }
    }

    if (openDialog) {
        BasicAlertDialog(
            modifier = Modifier
                .padding(5.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(10.dp)
                )
                .clip(
                    RoundedCornerShape(10.dp)
                ),
            onDismissRequest = {
                openDialog = false
            }
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TimePicker(state = state)
                ElevatedButton(
                    onClick = {
                        openDialog = false
                    }
                ) {
                    CustomText(text = stringResource(R.string.aceptar))
                }
            }
        }
    }

    onTextSelected(hourSelected)

    Box {
        CustomOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .clickable(
                    enabled = true,
                    role = Role.Button,
                    onClick = {
                        openDialog = true
                    },
                ),
            value = hourSelected,
            onValueChanged = {
                hourSelected = it
                onTextSelected.invoke(it)
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            label = { CustomText(text = value) }, leadingIcon = {
                Icon(
                    painter = painterResource,
                    contentDescription = "",
                    modifier = Modifier.clickable(
                        enabled = true,
                        role = Role.Button,
                        onClick = {
                            openDialog = true
                        },
                    )
                )
            }, singleLine = true, maxLines = 1, isError = errorStatus
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .alpha(0f)
                .clickable(onClick = { openDialog = true }),
        )
    }
}
