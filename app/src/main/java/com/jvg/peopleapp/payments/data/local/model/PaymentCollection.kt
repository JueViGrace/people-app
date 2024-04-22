package com.jvg.peopleapp.payments.data.local.model

import com.jvg.peopleapp.payments.domain.model.Payment
import com.jvg.peopleapp.people.data.local.model.PersonCollection
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class PaymentCollection : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var paymentMethod: String = ""
    var reference: String? = ""
    var bank: String? = null
    var holderCode: String? = ""
    var amount: Double = 0.0
    var madeAt: String = ""
    var note: String = ""
    var personCollection: PersonCollection? = null

    fun toDomain(): Payment = Payment(
        id = _id,
        paymentMethod = paymentMethod,
        reference = reference,
        bank = bank,
        holderCode = holderCode,
        amount = amount,
        madeAt = madeAt,
        note = note,
        person = personCollection?.toDomain()
    )
}
