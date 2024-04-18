package com.jvg.peopleapp.payments.presentation.payment.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jvg.peopleapp.R
import com.jvg.peopleapp.core.presentation.ui.components.AppBar
import com.jvg.peopleapp.core.presentation.ui.components.CustomOutlinedTextField
import com.jvg.peopleapp.core.presentation.ui.components.DatePickerComponent
import com.jvg.peopleapp.core.presentation.ui.components.FABComponent
import com.jvg.peopleapp.core.presentation.ui.components.TextFieldComponent
import com.jvg.peopleapp.payments.presentation.payment.events.PaymentEvents
import com.jvg.peopleapp.payments.presentation.payment.viewmodel.PaymentViewModel
import com.jvg.peopleapp.people.presentation.components.PeopleDropDownComponent
import org.koin.core.parameter.emptyParametersHolder
import org.koin.core.parameter.parametersOf
import org.mongodb.kbson.ObjectId

data class CreatePaymentScreen(val id: ObjectId? = null) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<PaymentViewModel>(
            parameters = {
                if (id != null) parametersOf(id) else emptyParametersHolder()
            }
        )
        val state by viewModel.state.collectAsState()
        val editPayment = viewModel.newPayment

        val topBarTitle = if (
            editPayment?.bank?.isNotEmpty() == true && editPayment.reference.isNotEmpty()
        ) {
            "${editPayment.bank}: ${editPayment.reference}"
        } else {
            "Cree un nuevo pago"
        }

        Scaffold(
            topBar = {
                AppBar(
                    title = topBarTitle,
                    icon = rememberVectorPainter(Icons.AutoMirrored.Default.ArrowBack),
                    onClick = {
                        viewModel.onEvent(PaymentEvents.DismissPayment)
                        navigator.pop()
                    }
                )
            },
            floatingActionButton = {
                FABComponent(
                    onAdd = {
                        viewModel.onEvent(PaymentEvents.SavePayment)

                        if (viewModel.getError()) {
                            viewModel.onEvent(PaymentEvents.DismissPayment)
                            navigator.pop()
                        }
                    },
                    icon = painterResource(id = R.drawable.ic_done_24px)
                )
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = innerPadding.calculateTopPadding(),
                        bottom = innerPadding.calculateBottomPadding()
                    ),
                verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Icon(
                        painter = painterResource(R.drawable.ic_receipt_24px),
                        modifier = Modifier.size(120.dp),
                        contentDescription = "Receipt"
                    )

                    TextFieldComponent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        value = editPayment?.reference ?: "",
                        newValue = { newValue ->
                            viewModel.onEvent(PaymentEvents.OnReferenceChanged(newValue))
                        },
                        label = "Referencia",
                        placeholder = "Referencia bancaria...",
                        supportingText = state.referenceError,
                        errorStatus = state.referenceError?.isNotEmpty() ?: false,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Text
                        ),
                        icon = R.drawable.ic_checkbook_24px
                    )

                    TextFieldComponent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        value = editPayment?.bank ?: "",
                        newValue = { newValue ->
                            viewModel.onEvent(PaymentEvents.OnBankChanged(newValue))
                        },
                        label = "Banco",
                        placeholder = "Banco...",
                        supportingText = state.bankError,
                        errorStatus = state.bankError?.isNotEmpty() ?: false,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Text,
                            capitalization = KeyboardCapitalization.Words
                        ),
                        icon = R.drawable.ic_account_balance_24px
                    )

                    TextFieldComponent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        value = editPayment?.holderCode ?: "",
                        newValue = { newValue ->
                            viewModel.onEvent(PaymentEvents.OnHolderCodeChanged(newValue))
                        },
                        label = "Cédula",
                        placeholder = "Cédula del titular de la cuenta...",
                        supportingText = state.holderCodeError,
                        errorStatus = state.holderCodeError?.isNotEmpty() ?: false,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Text
                        ),
                        icon = R.drawable.ic_id_card_24px
                    )

                    TextFieldComponent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        value = editPayment?.amount?.toString() ?: "",
                        newValue = { newValue ->
                            viewModel.onEvent(PaymentEvents.OnAmountChanged(newValue.toDouble()))
                        },
                        label = "Cantidad",
                        placeholder = "Cantidad...",
                        supportingText = state.amountError,
                        errorStatus = state.amountError?.isNotEmpty() ?: false,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Number
                        ),
                        icon = R.drawable.ic_payments_24px
                    )

                    DatePickerComponent(
                        modifier = Modifier.padding(5.dp),
                        label = "Fecha del pago",
                        painterResource(id = R.drawable.ic_calendar_month_24px),
                        value = editPayment?.madeAt ?: "",
                        onTextSelected = { newValue ->
                            viewModel.onEvent(PaymentEvents.OnMadeAtChanged(newValue))
                        },
                        supportingText = state.madeAtError,
                        errorStatus = state.madeAtError?.isNotEmpty() ?: false
                    )

                    state.people.DisplayResult(
                        onError = { message ->
                            CustomOutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 5.dp),
                                value = message,
                                onValueChanged = {},
                                readOnly = true,
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_person_24px),
                                        contentDescription = "",
                                    )
                                }
                            )
                        },
                        onLoading = {
                            CustomOutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 5.dp),
                                value = "",
                                onValueChanged = {},
                                readOnly = true,
                                trailingIcon = {
                                    CircularProgressIndicator()
                                },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_person_24px),
                                        contentDescription = "",
                                    )
                                }
                            )
                        },
                        onSuccess = { list ->
                            PeopleDropDownComponent(
                                people = list,
                                label = "Persona",
                                painter = painterResource(id = R.drawable.ic_person_24px),
                                placeholder = "Persona a relacionar el pago",
                                onValueChanged = { newValue ->
                                    viewModel.onEvent(PaymentEvents.OnPersonChanged(newValue))
                                },
                                supportingText = state.personError,
                                errorStatus = state.personError?.isNotEmpty() ?: false
                            )
                        },
                    )
                }
            }
        }
    }
}
