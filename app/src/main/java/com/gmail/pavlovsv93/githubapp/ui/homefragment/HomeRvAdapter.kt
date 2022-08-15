package com.gmail.pavlovsv93.githubapp.ui.homefragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.gmail.pavlovsv93.entitysapp.Account
import com.gmail.pavlovsv93.githubapp.R
import com.gmail.pavlovsv93.githubapp.databinding.FragmentHomeRecyclerviewItemBinding
import com.gmail.pavlovsv93.githubapp.ui.GitHubAppActivity

class HomeRvAdapter(private val clickToAccount: HomeFragment.OnClickToAccount) : RecyclerView.Adapter<HomeRvAdapter.HomeRvViewHolder>() {
	private val accountList: MutableList<Account> = mutableListOf()

	inner class HomeRvViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		fun bind(account: Account) {
			val binding: FragmentHomeRecyclerviewItemBinding =
				FragmentHomeRecyclerviewItemBinding.bind(itemView)
			account.avatarUrl?.let {
				Glide.with(itemView.context)
					.load(account.avatarUrl)
					.centerCrop()
					.transform(MultiTransformation(CircleCrop(), FitCenter()))
					.placeholder(R.drawable.ic_baseline_person_24)
					.into(binding.avatarImageView)
			}
			binding.loginTextView.text = account.login
			binding.cardView.setOnClickListener {
					clickToAccount.clickToAccount(account)
			}
		}
	}

	fun setAccountList(accounts: List<Account>) {
		if(accountList.isEmpty()){
			accountList.addAll(accounts)
		} else {
			accountList.clear()
			accountList.addAll(accounts)
		}
		notifyDataSetChanged()
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRvViewHolder =
		HomeRvViewHolder(
			LayoutInflater.from(parent.context)
				.inflate(
					R.layout.fragment_home_recyclerview_item,
					parent,
					false
				) as View
		)


	override fun onBindViewHolder(holder: HomeRvViewHolder, position: Int) {
		holder.bind(accountList[position])
	}

	override fun getItemCount(): Int = accountList.size
}