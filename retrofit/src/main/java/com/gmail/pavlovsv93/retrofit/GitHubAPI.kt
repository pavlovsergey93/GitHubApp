package com.gmail.pavlovsv93.retrofit

import com.gmail.pavlovsv93.retrofit.dto.GitHubAccountItemDTO
import com.gmail.pavlovsv93.retrofit.dto.GitHubRepositoryItemDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubAPI {
	@GET("users")
	suspend fun accountsList(
		@Query("since") since: Int
	): Response<List<GitHubAccountItemDTO>>

	@GET("users/{user}/repos")
	suspend fun accountRepoList(
		@Path("user") user: String
	): Response<List<GitHubRepositoryItemDTO>>
}