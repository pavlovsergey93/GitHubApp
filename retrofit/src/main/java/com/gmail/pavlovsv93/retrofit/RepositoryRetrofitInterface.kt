package com.gmail.pavlovsv93.retrofit

import com.gmail.pavlovsv93.retrofit.dto.GitHubAccountItemDTO
import com.gmail.pavlovsv93.retrofit.dto.GitHubRepositoryItemDTO
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RepositoryRetrofitInterface {
	suspend fun getAccounts(since: Int): Flow<Response<List<GitHubAccountItemDTO>>>
	suspend fun getRepositoriesAccount(user: String): Flow<Response<List<GitHubRepositoryItemDTO>>>
}