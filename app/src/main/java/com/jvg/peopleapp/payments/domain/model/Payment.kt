package com.jvg.peopleapp.payments.domain.model

import com.jvg.peopleapp.payments.data.local.model.PaymentCollection
import org.mongodb.kbson.ObjectId

data class Payment(
    var id: ObjectId = ObjectId(),
    val reference: String = "",
    val madeAt: String = "",
    val bank: String = "",
    val amount: Double = 0.0,
    val note: String = "",
    val dueDate: String = "",
) {
    fun toDatabase(): PaymentCollection = PaymentCollection().apply {
        _id = this@Payment.id
        reference = this@Payment.reference
        madeAt = this@Payment.madeAt
        bank = this@Payment.bank
        amount = this@Payment.amount
        note = this@Payment.note
        dueDate = this@Payment.dueDate
    }
}
