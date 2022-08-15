package com.gmail.pavlovsv93.viewmodel.details

import com.gmail.pavlovsv93.entitysapp.RepositoriesAccount

sealed class DetailsAppState{
	data class OnLoading(var load: Boolean) : DetailsAppState()
	data class OnSuccess(var success: List<RepositoriesAccount> ) : DetailsAppState()
	data class OnError(var throwable: Throwable) : DetailsAppState()
}
