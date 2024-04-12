package com.jvg.peopleapp.person.di

import com.jvg.peopleapp.core.common.Constants
import com.jvg.peopleapp.person.presentation.details.viewmodel.PersonViewModel
import com.jvg.peopleapp.person.data.local.sources.PeopleDataSource
import com.jvg.peopleapp.person.data.local.sources.PeopleDataSourceImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val personModule = module {
    single<PeopleDataSource> {
        PeopleDataSourceImpl(get(), get(named(Constants.IO_DISPATCHER)))
    }

    factory {
        PersonViewModel(get(), get(named(Constants.IO_DISPATCHER)))
    }
}