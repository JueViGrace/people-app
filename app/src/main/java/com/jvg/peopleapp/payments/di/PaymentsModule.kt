package com.jvg.peopleapp.payments.di

import com.jvg.peopleapp.core.common.Constants.IO_DISPATCHER
import com.jvg.peopleapp.core.common.Constants.MAIN_DISPATCHER
import com.jvg.peopleapp.payments.data.local.sources.PaymentsDataSource
import com.jvg.peopleapp.payments.data.local.sources.PaymentsDataSourceImpl
import com.jvg.peopleapp.payments.presentation.payment.viewmodel.PaymentViewModel
import com.jvg.peopleapp.payments.presentation.viewmodel.PaymentsViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val paymentsModule = module {
    single<PaymentsDataSource> {
        PaymentsDataSourceImpl(get(), get(named(IO_DISPATCHER)))
    }

    factory {
        PaymentsViewModel(get(), get(named(MAIN_DISPATCHER)))
    }

    factory { parametersHolder ->
        if (parametersHolder.isNotEmpty()) {
            PaymentViewModel(
                get(),
                get(),
                get(named(IO_DISPATCHER)),
                get(named(MAIN_DISPATCHER)),
                id = parametersHolder.get()
            )
        } else {
            PaymentViewModel(get(), get(), get(named(IO_DISPATCHER)), get(named(MAIN_DISPATCHER)))
        }
    }
}
