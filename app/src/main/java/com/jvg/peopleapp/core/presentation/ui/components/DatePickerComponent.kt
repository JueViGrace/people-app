package com.jvg.peopleapp.core.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jvg.peopleapp.R
import com.jvg.peopleapp.core.common.toStringFormat
import java.time.LocalDate.now
import java.time.ZoneId
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerComponent(
    modifier: Modifier = Modifier,
    label: String,
    painterResource: Painter,
    value: String,
    onTextSelected: (String) -> Unit,
    supportingText: String? = null,
    errorStatus: Boolean = false
) {
    var openDialog by remember {
        mutableStateOf(false)
    }

    val date by remember {
        mutableStateOf(now())
    }

    val state = rememberDatePickerState()

    val confirmEnabled by remember { derivedStateOf { state.selectedDateMillis != null } }

    var selectedDate by remember {
        mutableLongStateOf(
            date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        )
    }

    var dateSelected = Date(selectedDate.plus(86400000)).toStringFormat(2)

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
                DatePicker(state = state)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = { openDialog = false }
                    ) {
                        CustomText(text = "Cancel")
                    }

                    ElevatedButton(
                        onClick = {
                            openDialog = false
                            state.selectedDateMillis?.let {
                                selectedDate = it
                            }
                            onTextSelected(dateSelected)
                        },
                        enabled = confirmEnabled
                    ) {
                        CustomText(text = stringResource(R.string.aceptar))
                    }
                }
            }
        }
    }

    Box {
        CustomOutlinedTextField(
            modifier = modifier
                .fillMaxWidth(),
            value = value,
            onValueChanged = {
                dateSelected = it
                onTextSelected(dateSelected)
            },
            label = { CustomText(text = label) },
            leadingIcon = {
                Icon(
                    painter = painterResource,
                    contentDescription = "",
                    modifier = Modifier.clickable {
                        openDialog = true
                    }
                )
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
        Box(
            modifier = Modifier
                .clickable { openDialog = true }
                .matchParentSize()
                .alpha(0f)
        )
    }
}
