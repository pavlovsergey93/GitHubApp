package com.gmail.pavlovsv93.viewmodel.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.pavlovsv93.retrofit.RepositoryRetrofitInterface
import com.gmail.pavlovsv93.viewmodel.accounts.AccountsAppState
import com.gmail.pavlovsv93.viewmodel.utils.convertToRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DetailsViewModel(
	private val liveData: MutableLiveData<DetailsAppState>,
	private val repo: RepositoryRetrofitInterface
) : ViewModel() {

	fun getLiveData(): LiveData<DetailsAppState> = liveData

	fun getRepositoriesAccount(user: String) {
		try {
			viewModelScope.launch {
				liveData.postValue(DetailsAppState.OnLoading(true))
				repo.getRepositoriesAccount(user)
					.map { success ->
						success.body()?.let { convertToRepository(it) }
					}
					.collect { result ->
						liveData.postValue(DetailsAppState.OnLoading(false))
						if (!result.isNullOrEmpty()) {
							liveData.postValue(DetailsAppState.OnSuccess(result))
						} else {
							liveData.postValue(DetailsAppState.OnError(throw ArrayIndexOutOfBoundsException()))
						}
					}
			}
		}catch (e: Exception){
			liveData.postValue(DetailsAppState.OnError(e))
		}finally {
			liveData.postValue(DetailsAppState.OnLoading(false))
		}
	}
}