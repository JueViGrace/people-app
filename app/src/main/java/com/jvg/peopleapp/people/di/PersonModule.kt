package com.jvg.peopleapp.people.di

import com.jvg.peopleapp.core.common.Constants
import com.jvg.peopleapp.people.presentation.person.viewmodel.PersonViewModel
import com.jvg.peopleapp.people.data.local.sources.PeopleDataSource
import com.jvg.peopleapp.people.data.local.sources.PeopleDataSourceImpl
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