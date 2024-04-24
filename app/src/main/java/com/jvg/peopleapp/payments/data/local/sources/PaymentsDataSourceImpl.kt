package com.jvg.peopleapp.payments.data.local.sources

import androidx.paging.PagingSource
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOne
import app.cash.sqldelight.paging3.QueryPagingSource
import com.jvg.peopleapp.GetPayments
import com.jvg.peopleapp.core.database.helper.DbHelper
import com.jvg.peopleapp.core.database.mappers.toDatabase
import com.jvg.peopleapp.core.database.mappers.toPayment
import com.jvg.peopleapp.payments.domain.model.Payment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.util.UUID

class PaymentsDataSourceImpl(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) : PaymentsDataSource {
    override suspend fun countPayments(): Flow<Long> = scope.async {
        dbHelper.withDatabase { db ->
            db.selfManagerDBQueries
                .countPayments()
                .asFlow()
                .mapToOne(scope.coroutineContext)
        }
    }.await()

    override fun getAllPayments(): Flow<PagingSource<Int, GetPayments>> = flow {
        emit(
            scope.async {
                dbHelper.withDatabase { db ->
                    val count = db.selfManagerDBQueries.countPayments()

                    QueryPagingSource(
                        countQuery = count,
                        transacter = db.selfManagerDBQueries,
                        context = scope.coroutineContext,
                        queryProvider = db.selfManagerDBQueries::getPayments
                    )
                }
            }.await()
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun getPaymentById(id: String): Flow<Payment> = scope.async {
        dbHelper.withDatabase { db ->
            db.selfManagerDBQueries
                .getPaymentById(id = id)
                .asFlow()
                .mapToOne(scope.coroutineContext)
                .map { payment ->
                    payment.toPayment()
                }
        }
    }.await()

    override suspend fun addPayment(payment: Payment) {
        dbHelper.withDatabase { db ->
            db.selfManagerDBQueries.addPayment(
                payment = payment.copy(
                    id = payment.id.ifEmpty {
                        UUID.randomUUID().toString()
                    }
                ).toDatabase()
            )
        }
    }

    override suspend fun softDeletePayment(id: String) {
        dbHelper.withDatabase { db ->
            db.selfManagerDBQueries.softDeletePayment(id = id)
        }
    }

    override suspend fun deletePayment(id: String) {
        dbHelper.withDatabase { db ->
            db.selfManagerDBQueries.deletePayment(id = id)
        }
    }

    override suspend fun deletePayments() {
        dbHelper.withDatabase { db ->
            db.selfManagerDBQueries.deletePayments()
        }
    }
}
