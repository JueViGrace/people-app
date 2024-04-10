package com.jvg.peopleapp.home.data.local.model

import com.jvg.peopleapp.core.common.toStringFormat
import com.jvg.peopleapp.home.domain.model.Person
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import io.realm.kotlin.types.annotations.PrimaryKey
import java.util.Date

class PersonCollection : RealmObject {
    @PrimaryKey
    var _id: RealmUUID = RealmUUID.random()
    var name: String = ""
    var lastname: String = ""
    var code: String = ""
    var phone: String = ""
    var email: String = ""
    var createdAt: String = Date().toStringFormat(1)
    var startsAt: String? = ""
    var finishesAt: String? = ""
    var active: Boolean = true

    fun toDomain(): Person = Person(
        _id = _id,
        name = name,
        lastname = lastname,
        code = code,
        phone = phone,
        email = email,
        createdAt = createdAt,
        startsAt = startsAt,
        finishesAt = finishesAt,
        active = active
    )
}
