package com.gmail.pavlovsv93.viewmodel.accounts

import com.gmail.pavlovsv93.entitysapp.Account

sealed class AccountsAppState {
	data class OnLoading(var load: Boolean) : AccountsAppState()
	data class OnSuccess(var success: List<Account>) : AccountsAppState()
	data class OnError(var throwable: Throwable) : AccountsAppState()
}