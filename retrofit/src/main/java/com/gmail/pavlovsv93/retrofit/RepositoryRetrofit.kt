package com.gmail.pavlovsv93.retrofit

import com.gmail.pavlovsv93.retrofit.dto.GitHubAccountItemDTO
import com.gmail.pavlovsv93.retrofit.dto.GitHubRepositoryItemDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class RepositoryRetrofit(val api: GitHubAPI) : RepositoryRetrofitInterface {

	override suspend fun getAccounts(since: Int): Flow<Response<List<GitHubAccountItemDTO>>> =
		flow {
			emit(api.accountsList(since))
		}

	override suspend fun getRepositoriesAccount(user: String): Flow<Response<List<GitHubRepositoryItemDTO>>> =
		flow {
			emit(api.accountRepoList(user))
		}
}