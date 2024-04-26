package com.jvg.peopleapp.payments.data.local.sources

import androidx.paging.PagingData
import com.jvg.peopleapp.GetPayments
import com.jvg.peopleapp.payments.domain.model.Payment
import kotlinx.coroutines.flow.Flow

interface PaymentsDataSource {
    suspend fun countPayments(): Flow<Long>
    fun getAllPayments(): Flow<Flow<PagingData<GetPayments>>>
    suspend fun getPaymentById(id: String): Flow<Payment>
    suspend fun addPayment(payment: Payment)
    suspend fun softDeletePayment(id: String)
    suspend fun deletePayment(id: String)
    suspend fun deletePayments()
}
