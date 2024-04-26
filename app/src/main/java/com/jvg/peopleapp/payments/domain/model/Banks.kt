package com.jvg.peopleapp.payments.domain.model

sealed class Banks(val name: String) {
    data object BNC : Banks(name = "BNC")
    data object Bancamiga : Banks(name = "Bancamiga")
    data object Banesco : Banks(name = "Banesco")
    data object BanescoPanama : Banks(name = "Banesco Panamá")
    data object Zinli : Banks(name = "Zinli")
    data object Zele : Banks(name = "Zinli")
}
