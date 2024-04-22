package com.jvg.peopleapp.payments.presentation.payment.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.jvg.peopleapp.core.state.RequestState
import com.jvg.peopleapp.payments.data.local.sources.PaymentsDataSource
import com.jvg.peopleapp.payments.domain.model.Payment
import com.jvg.peopleapp.payments.domain.rules.PaymentValidator
import com.jvg.peopleapp.payments.presentation.payment.events.PaymentEvents
import com.jvg.peopleapp.payments.presentation.payment.state.PaymentDetailsState
import com.jvg.peopleapp.people.data.local.sources.PeopleDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId

class PaymentViewModel(
    private val paymentsDataSource: PaymentsDataSource,
    private val peopleDataSource: PeopleDataSource,
    private val id: ObjectId? = null
) : ScreenModel {
    private var _state: MutableStateFlow<PaymentDetailsState> = MutableStateFlow(PaymentDetailsState())
    val state: StateFlow<PaymentDetailsState> = _state.asStateFlow()

    var newPayment: Payment? by mutableStateOf(null)
        private set

    init {
        _state.update { paymentDetailsState ->
            paymentDetailsState.copy(
                people = RequestState.Loading
            )
        }

        screenModelScope.launch {
            peopleDataSource.getAllPeople().collect { value ->
                _state.update { paymentDetailsState ->
                    paymentDetailsState.copy(
                        people = value
                    )
                }
            }
        }
        if (id != null) {
            _state.update { paymentDetailsState ->
                paymentDetailsState.copy(
                    payment = RequestState.Loading
                )
            }

            screenModelScope.launch {
                paymentsDataSource.getPaymentById(id).collect { value ->
                    _state.update { paymentDetailsState ->
                        paymentDetailsState.copy(
                            payment = value,
                            selectedPayment = value.getSuccessDataOrNull()?.id
                        )
                    }
                    newPayment = value.getSuccessDataOrNull()
                }
            }
        } else {
            newPayment = Payment()
        }
    }

    fun onEvent(event: PaymentEvents) {
        when (event) {
            is PaymentEvents.OnPaymentMethodChanged -> {
                newPayment = newPayment?.copy(
                    paymentMethod = event.value
                )
            }
            is PaymentEvents.OnAmountChanged -> {
                newPayment = newPayment?.copy(
                    amount = event.value
                )
            }
            is PaymentEvents.OnBankChanged -> {
                newPayment = newPayment?.copy(
                    bank = event.value.name
                )
            }
            is PaymentEvents.OnHolderCodeChanged -> {
                newPayment = newPayment?.copy(
                    holderCode = event.value
                )
            }
            is PaymentEvents.OnMadeAtChanged -> {
                newPayment = newPayment?.copy(
                    madeAt = event.value
                )
            }
            is PaymentEvents.OnNoteChanged -> {
                newPayment = newPayment?.copy(
                    note = event.value
                )
            }
            is PaymentEvents.OnPersonChanged -> {
                newPayment = newPayment?.copy(
                    person = event.value
                )
            }
            is PaymentEvents.OnReferenceChanged -> {
                newPayment = newPayment?.copy(
                    reference = event.value
                )
            }
            PaymentEvents.DeletePayment -> {
                screenModelScope.launch(Dispatchers.IO) {
                    _state.value.selectedPayment?.let { id ->
                        paymentsDataSource.deletePayment(id)
                    }
                }
            }
            PaymentEvents.DismissPayment -> {
                _state.update { paymentDetailsState ->
                    paymentDetailsState.copy(
                        selectedPayment = null,
                        paymentMethodError = null,
                        referenceError = null,
                        bankError = null,
                        holderCodeError = null,
                        madeAtError = null,
                        amountError = null,
                        personError = null,
                        errors = null
                    )
                }
                newPayment = null
            }
            PaymentEvents.SavePayment -> {
                newPayment?.let { payment: Payment ->
                    val result = PaymentValidator.validatePayment(payment)
                    val errors = listOfNotNull(
                        result.paymentMethodError,
                        result.referenceError,
                        result.bankError,
                        result.amountError,
                        result.madeAtError,
                        result.holderCodeError,
                        result.personError
                    )

                    if (errors.isEmpty()) {
                        _state.update { paymentDetailsState ->
                            paymentDetailsState.copy(
                                selectedPayment = null,
                                paymentMethodError = null,
                                referenceError = null,
                                bankError = null,
                                holderCodeError = null,
                                madeAtError = null,
                                amountError = null,
                                personError = null,
                                errors = false
                            )
                        }

                        screenModelScope.launch(Dispatchers.IO) {
                            paymentsDataSource.addPayment(payment)
                        }
                    } else {
                        _state.update { paymentDetailsState ->
                            paymentDetailsState.copy(
                                paymentMethodError = result.paymentMethodError,
                                referenceError = result.referenceError,
                                bankError = result.bankError,
                                holderCodeError = result.holderCodeError,
                                madeAtError = result.madeAtError,
                                amountError = result.amountError,
                                personError = result.personError,
                                errors = true
                            )
                        }
                    }
                }
            }
        }
    }

    fun getError() = _state.value.errors == false
}
