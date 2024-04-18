package com.jvg.peopleapp.payments.presentation.payment.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.jvg.peopleapp.core.state.RequestState
import com.jvg.peopleapp.payments.data.local.sources.PaymentsDataSource
import com.jvg.peopleapp.payments.domain.model.Payment
import com.jvg.peopleapp.payments.presentation.payment.events.PaymentEvents
import com.jvg.peopleapp.payments.presentation.payment.state.PaymentDetailsState
import com.jvg.peopleapp.people.data.local.sources.PeopleDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId

class PaymentViewModel(
    private val paymentsDataSource: PaymentsDataSource,
    private val peopleDataSource: PeopleDataSource,
    private val ioDispatcher: CoroutineDispatcher,
    private val mainDispatcher: MainCoroutineDispatcher,
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

        screenModelScope.launch(mainDispatcher) {
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

            screenModelScope.launch(mainDispatcher) {
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
            is PaymentEvents.OnAmountChanged -> TODO()
            is PaymentEvents.OnBankChanged -> TODO()
            is PaymentEvents.OnHolderCodeChanged -> TODO()
            is PaymentEvents.OnMadeAtChanged -> TODO()
            is PaymentEvents.OnNoteChanged -> TODO()
            is PaymentEvents.OnPersonChanged -> TODO()
            is PaymentEvents.OnReferenceChanged -> TODO()
            PaymentEvents.DeletePayment -> TODO()
            PaymentEvents.DismissPayment -> TODO()
            PaymentEvents.SavePayment -> TODO()
        }
    }

    fun getError() = _state.value.errors == false
}
