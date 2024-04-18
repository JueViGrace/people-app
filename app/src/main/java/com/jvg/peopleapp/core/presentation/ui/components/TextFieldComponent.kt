package com.jvg.peopleapp.core.presentation.ui.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun TextFieldComponent(
    modifier: Modifier = Modifier,
    value: String,
    newValue: (String) -> Unit,
    label: String = "",
    placeholder: String = "",
    supportingText: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        imeAction = ImeAction.Done,
        keyboardType = KeyboardType.Text
    ),
    icon: Int,
    errorStatus: Boolean = false,
    readOnly: Boolean = false,
    enabled: Boolean = true
) {
    val focus = LocalFocusManager.current
    CustomOutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChanged = {
            newValue(it)
        },
        label = {
            CustomText(text = label)
        },
        readOnly = readOnly,
        enabled = enabled,
        keyboardOptions = keyboardOptions,
        placeholder = {
            CustomText(text = placeholder)
        },
        supportingText = if (supportingText != null) {
            { CustomText(text = supportingText) }
        } else {
            null
        },
        keyboardActions = KeyboardActions(
            onNext = {
                focus.moveFocus(FocusDirection.Down)
            }
        ),
        leadingIcon = {
            Icon(painter = painterResource(icon), contentDescription = null)
        },
        isError = errorStatus
    )
}
