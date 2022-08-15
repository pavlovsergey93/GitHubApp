package com.gmail.pavlovsv93.retrofit.di

import com.gmail.pavlovsv93.retrofit.GitHubAPI
import com.gmail.pavlovsv93.retrofit.RepositoryRetrofit
import com.gmail.pavlovsv93.retrofit.RepositoryRetrofitInterface
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.github.com/"

val retrofitModule = module {
	single<Retrofit> {
		Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.build()
	}
	single<GitHubAPI> { get<Retrofit>().create(GitHubAPI::class.java) }
	single<RepositoryRetrofitInterface> { RepositoryRetrofit(api = get()) }
}