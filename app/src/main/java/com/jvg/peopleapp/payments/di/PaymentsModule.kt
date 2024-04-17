package com.jvg.peopleapp.payments.di

import com.jvg.peopleapp.core.common.Constants.IO_DISPATCHER
import com.jvg.peopleapp.payments.data.local.sources.PaymentsDataSource
import com.jvg.peopleapp.payments.data.local.sources.PaymentsDataSourceImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val paymentsModule = module {
    single<PaymentsDataSource> {
        PaymentsDataSourceImpl(get(), get(named(IO_DISPATCHER)))
    }
}
