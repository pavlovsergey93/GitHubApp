package com.gmail.pavlovsv93.viewmodel.utils

import com.gmail.pavlovsv93.entitysapp.Account
import com.gmail.pavlovsv93.entitysapp.RepositoriesAccount
import com.gmail.pavlovsv93.retrofit.dto.GitHubAccountItemDTO
import com.gmail.pavlovsv93.retrofit.dto.GitHubRepositoryItemDTO

fun convertToAccount(accountsList: List<GitHubAccountItemDTO>): List<Account> {
	val list: MutableList<Account> = mutableListOf()
	accountsList.forEach { account ->
		val acc = Account(
			id = account.id,
			avatarUrl = account.avatarUrl,
			htmlUrl = account.htmlUrl,
			login = account.login,
			reposUrl = account.reposUrl
		)
		list.add(acc)
	}
	return list
}

fun convertToRepository(repositoryList: List<GitHubRepositoryItemDTO>): List<RepositoriesAccount> {
	val list: MutableList<RepositoriesAccount> = mutableListOf()
	repositoryList.forEach { repo ->
		val itemRepo = RepositoriesAccount(
			id = repo.id,
			name = repo.name,
			description = repo.description,
			nodeId = repo.nodeId,
			url = repo.htmlUrl,
			language = repo.language
		)
		list.add(itemRepo)
	}
	return list
}