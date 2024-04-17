package com.jvg.peopleapp.people.di

import com.jvg.peopleapp.core.common.Constants.IO_DISPATCHER
import com.jvg.peopleapp.core.common.Constants.MAIN_DISPATCHER
import com.jvg.peopleapp.people.data.local.sources.PeopleDataSource
import com.jvg.peopleapp.people.data.local.sources.PeopleDataSourceImpl
import com.jvg.peopleapp.people.presentation.person.viewmodel.PersonViewModel
import com.jvg.peopleapp.people.presentation.viewmodel.PeopleViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val peopleModule = module {
    single<PeopleDataSource> {
        PeopleDataSourceImpl(get(), get(named(IO_DISPATCHER)))
    }

    factory {
        PeopleViewModel(get(), get(named(IO_DISPATCHER)))
    }

    factory { parametersHolder ->
        if (parametersHolder.isNotEmpty()) {
            PersonViewModel(
                get(),
                get(named(IO_DISPATCHER)),
                get(named(MAIN_DISPATCHER)),
                id = parametersHolder.get()
            )
        } else {
            PersonViewModel(get(), get(named(IO_DISPATCHER)), get(named(MAIN_DISPATCHER)))
        }
    }
}
