package com.jvg.peopleapp.payments.domain.model

sealed class Banks(val name: String) {
    data object Banesco : Banks(name = "Banesco")
    data object BNC : Banks(name = "Banco Nacional de Crédito")
}