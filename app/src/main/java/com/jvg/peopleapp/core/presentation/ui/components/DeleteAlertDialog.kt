package com.jvg.peopleapp.core.presentation.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.jvg.peopleapp.R

@Composable
fun DeleteAlertDialog(
    questionText: String,
    onShowChange: (Boolean) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        title = {
            CustomText(
                text = stringResource(R.string.delete),
                fontSize = MaterialTheme.typography.titleLarge.fontSize
            )
        },
        text = {
            CustomText(
                text = questionText,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize
            )
        },
        onDismissRequest = {
            onShowChange(false)
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onShowChange(false)
                    onDismiss()
                }
            ) {
                CustomText(text = stringResource(R.string.cancel))
            }
        },
        confirmButton = {
            ElevatedButton(
                onClick = {
                    onShowChange(false)
                    onConfirm()
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