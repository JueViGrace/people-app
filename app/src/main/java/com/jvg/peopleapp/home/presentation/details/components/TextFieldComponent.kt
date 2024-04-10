package com.jvg.peopleapp.home.presentation.details.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.jvg.peopleapp.core.presentation.ui.components.CustomOutlinedTextField
import com.jvg.peopleapp.core.presentation.ui.components.CustomText

@Composable
fun TextFieldComponent(
    modifier: Modifier = Modifier,
    value: String,
    newValue: (String) -> Unit,
    label: String = "",
    placeholder: String = "",
    supportingText: String? = null,
    keyboardOptions: KeyboardOptions,
    icon: Int,
    errorStatus: Boolean = false
) {
    CustomOutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChanged = {
            newValue(it)
        },
        label = {
            CustomText(text = label)
        },
        keyboardOptions = keyboardOptions,
        placeholder = {
            CustomText(text = placeholder)
        },
        supportingText = {
            if (supportingText != null) {
                CustomText(text = supportingText)
            }
        },
        leadingIcon = {
            Icon(painter = painterResource(icon), contentDescription = null)
        },
        isError = errorStatus
    )
}
