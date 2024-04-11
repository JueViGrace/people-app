package com.jvg.peopleapp.home.di

import com.jvg.peopleapp.core.common.Constants.IO_DISPATCHER
import com.jvg.peopleapp.home.data.local.sources.PeopleDataSource
import com.jvg.peopleapp.home.data.local.sources.PeopleDataSourceImpl
import com.jvg.peopleapp.home.presentation.dashboard.viewmodel.DashborardViewModel
import com.jvg.peopleapp.home.presentation.details.viewmodel.PersonViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val homeModule = module {

    single<PeopleDataSource> {
        PeopleDataSourceImpl(get(), get(named(IO_DISPATCHER)))
    }

    factory {
        DashborardViewModel(get(), get(named(IO_DISPATCHER)))
    }

    factory {
        PersonViewModel(get(), get(named(IO_DISPATCHER)))
    }
}
