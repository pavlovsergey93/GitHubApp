package com.gmail.pavlovsv93.viewmodel.accounts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.pavlovsv93.retrofit.RepositoryRetrofitInterface
import com.gmail.pavlovsv93.viewmodel.utils.convertToAccount
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class AccountsViewModel(
	private val liveData: MutableLiveData<AccountsAppState>,
	private val repo: RepositoryRetrofitInterface
) : ViewModel() {

	fun getLiveData(): LiveData<AccountsAppState> = liveData

	fun getAccountsGitHub(since: Int = 0) {
		try {
			viewModelScope.launch {
				liveData.postValue(AccountsAppState.OnLoading(true))
				repo.getAccounts(since)
					.map { success ->
						success.body()?.let {
							convertToAccount(it)
						}
					}.collect { result ->
						liveData.postValue(AccountsAppState.OnLoading(false))
						if (!result.isNullOrEmpty()) {
							liveData.postValue(AccountsAppState.OnSuccess(result))
						} else {
							liveData.postValue(AccountsAppState.OnError(throw ArrayIndexOutOfBoundsException()))
						}
					}
			}
		} catch (e: Exception) {
			liveData.postValue(AccountsAppState.OnError(e))
		} finally {
			liveData.postValue(AccountsAppState.OnLoading(false))
		}
	}
}