package com.jvg.peopleapp.dashboard.di

import com.jvg.peopleapp.core.common.Constants.IO_DISPATCHER
import com.jvg.peopleapp.dashboard.presentation.viewmodel.DashborardViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val homeModule = module {

    factory {
        DashborardViewModel(get(), get(named(IO_DISPATCHER)))
    }
}
