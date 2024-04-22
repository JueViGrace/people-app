package com.jvg.peopleapp.payments.data.local.sources

import com.jvg.peopleapp.core.state.RequestState
import com.jvg.peopleapp.payments.domain.model.Payment
import kotlinx.coroutines.flow.Flow

interface PaymentsDataSource {
    fun getAllPayments(): Flow<RequestState<List<Payment>>>
    fun getPaymentById(id: String): Flow<RequestState<Payment>>
    fun getPaymentsByPerson(id: String): Flow<RequestState<List<Payment>>>
    fun getPaymentsWithOptions(query: String, vararg params: Any?): Flow<RequestState<List<Payment>>>
    suspend fun addPayment(payment: Payment)
    suspend fun deletePayment(id: String)
}
