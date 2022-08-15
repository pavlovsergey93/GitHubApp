package com.gmail.pavlovsv93.githubapp.ui.detailsfragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.pavlovsv93.entitysapp.RepositoriesAccount
import com.gmail.pavlovsv93.githubapp.R
import com.gmail.pavlovsv93.githubapp.databinding.FragmentDetailsRecyclerviewItemBinding

class DetailsRvAdapter(private val clickedToItem :  DetailsFragment.OnClickToItem) : RecyclerView.Adapter<DetailsRvAdapter.DetailsRvViewHolder>() {
	private val listRepos: MutableList<RepositoriesAccount> = mutableListOf()

	@SuppressLint("NotifyDataSetChanged")
	fun setListRepos(listRepos: List<RepositoriesAccount>){
		if (!this.listRepos.isEmpty()){
			this.listRepos.clear()
		}
		this.listRepos.addAll(listRepos)
		notifyDataSetChanged()
	}

	inner class DetailsRvViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		fun bind(repositoriesAccount: RepositoriesAccount) {
			val binding = FragmentDetailsRecyclerviewItemBinding.bind(itemView)
			binding.cardView.setOnClickListener {
				clickedToItem.clickedToItem(repositoriesAccount.url)
			}
			binding.nameTextView.text = repositoriesAccount.name
			binding.languageTextView.text = "Language: ${repositoriesAccount.language}"
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsRvViewHolder =
		DetailsRvViewHolder(
			LayoutInflater.from(parent.context)
				.inflate(R.layout.fragment_details_recyclerview_item, parent, false) as View
		)

	override fun onBindViewHolder(holder: DetailsRvViewHolder, position: Int) {
		holder.bind(listRepos[position])
	}

	override fun getItemCount(): Int = listRepos.size
}