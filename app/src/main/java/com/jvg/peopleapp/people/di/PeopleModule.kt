package com.jvg.peopleapp.people.di

import com.jvg.peopleapp.people.data.local.sources.PeopleDataSource
import com.jvg.peopleapp.people.data.local.sources.PeopleDataSourceImpl
import com.jvg.peopleapp.people.presentation.person.viewmodel.PersonViewModel
import com.jvg.peopleapp.people.presentation.viewmodel.PeopleViewModel
import org.koin.dsl.module

val peopleModule = module {
    single<PeopleDataSource> {
        PeopleDataSourceImpl(get())
    }

    factory {
        PeopleViewModel(get())
    }

    factory { parametersHolder ->
        if (parametersHolder.isNotEmpty()) {
            PersonViewModel(
                get(),
                id = parametersHolder.get()
            )
        } else {
            PersonViewModel(get())
        }
    }
}
