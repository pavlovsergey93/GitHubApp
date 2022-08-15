package com.gmail.pavlovsv93.viewmodel.di

import androidx.lifecycle.MutableLiveData
import com.gmail.pavlovsv93.retrofit.RepositoryRetrofitInterface
import com.gmail.pavlovsv93.viewmodel.accounts.AccountsViewModel
import com.gmail.pavlovsv93.viewmodel.details.DetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val ACCOUNTS_VIEW_MODEL = "AccountsViewModel"
const val DETAILS_VIEW_MODEL = "DetailsViewModel"
val viewModelModule = module {
	viewModel(named(ACCOUNTS_VIEW_MODEL)) {
		AccountsViewModel(MutableLiveData(), get<RepositoryRetrofitInterface>())
	}

	viewModel(named(DETAILS_VIEW_MODEL)) {
		DetailsViewModel(MutableLiveData(), get<RepositoryRetrofitInterface>())
	}
}