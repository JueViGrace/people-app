package com.jvg.peopleapp.payments.di

import com.jvg.peopleapp.payments.data.local.sources.PaymentsDataSource
import com.jvg.peopleapp.payments.data.local.sources.PaymentsDataSourceImpl
import com.jvg.peopleapp.payments.domain.repository.PaymentsRepository
import com.jvg.peopleapp.payments.presentation.payment.viewmodel.PaymentViewModel
import com.jvg.peopleapp.payments.presentation.viewmodel.PaymentsViewModel
import org.koin.dsl.module

val paymentsModule = module {
    single<PaymentsDataSource> {
        PaymentsDataSourceImpl(get(), get())
    }

    single {
        PaymentsRepository(get())
    }

    factory {
        PaymentsViewModel(get())
    }

    factory { parametersHolder ->
        if (parametersHolder.isNotEmpty()) {
            PaymentViewModel(
                get(),
                get(),
                id = parametersHolder.get()
            )
        } else {
            PaymentViewModel(get(), get())
        }
    }
}
