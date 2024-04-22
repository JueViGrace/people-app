package com.jvg.peopleapp.people.data.local.model

import com.jvg.peopleapp.payments.data.local.model.PaymentCollection
import com.jvg.peopleapp.people.domain.model.Person
import io.realm.kotlin.ext.realmSetOf
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmSet
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class PersonCollection : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var name: String = ""
    var lastname: String = ""
    var code: String = ""
    var phone: String = ""
    var email: String = ""
    var startsAt: String = ""
    var finishesAt: String = ""
    var active: Boolean = true
    var paymentCollection: RealmSet<PaymentCollection> = realmSetOf()

    fun toDomain(): Person = Person(
        id = _id,
        name = name,
        lastname = lastname,
        code = code,
        phone = phone,
        email = email,
        startsAt = startsAt,
        finishesAt = finishesAt,
        active = active,
        payments = paymentCollection.map { it.toDomain() }.toList()
    )
}
