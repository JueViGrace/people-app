package com.jvg.peopleapp.payments.domain.model

import com.jvg.peopleapp.R

sealed class PaymentMethods(val method: String, val icon: Int? = null) {
    data object Cash : PaymentMethods(method = "Efectivo", icon = R.drawable.ic_payments_24px)
    data object Transfer : PaymentMethods(method = "Transferencia", icon = R.drawable.ic_credit_card_24px)
}
