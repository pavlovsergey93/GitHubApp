package com.gmail.pavlovsv93.githubapp.ui.homefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.pavlovsv93.entitysapp.Account
import com.gmail.pavlovsv93.githubapp.R
import com.gmail.pavlovsv93.githubapp.databinding.FragmentHomeBinding
import com.gmail.pavlovsv93.githubapp.ui.detailsfragment.DetailsFragment
import com.gmail.pavlovsv93.githubapp.utils.showMessage
import com.gmail.pavlovsv93.viewmodel.accounts.AccountsAppState
import com.gmail.pavlovsv93.viewmodel.accounts.AccountsViewModel
import com.gmail.pavlovsv93.viewmodel.di.ACCOUNTS_VIEW_MODEL
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class HomeFragment : Fragment() {
	companion object {
		fun newInstance() = HomeFragment()
	}
	interface OnClickToAccount{
		fun clickToAccount(account: Account)
	}

	private var _binding: FragmentHomeBinding? = null
	private val binding get() = _binding!!
	private val viewModel: AccountsViewModel by viewModel(named(ACCOUNTS_VIEW_MODEL))
	private val adapter: HomeRvAdapter = HomeRvAdapter(object : OnClickToAccount{
		override fun clickToAccount(account: Account) {
			if(!account.reposUrl.isNullOrEmpty()) {
				parentFragmentManager.beginTransaction()
					.replace(
						R.id.fragmentContainerView,
						DetailsFragment.newInstance(account.login)
					)
					.addToBackStack("")
					.commit()
			} else {
				binding.root.showMessage(getString(R.string.repos_empty_or_null))
			}
		}
	})

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_binding = FragmentHomeBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val recyclerView = binding.recyclerView
		recyclerView.layoutManager =
			LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
		recyclerView.adapter = adapter
		viewModel.getLiveData().observe(viewLifecycleOwner, Observer { state ->
			rangerData(state)
		})
		viewModel.getAccountsGitHub()
	}

	private fun rangerData(state: AccountsAppState) {
		when (state) {
			is AccountsAppState.OnError -> {
				if (!state.throwable.message.isNullOrEmpty()) {
					binding.root.showMessage(
						message = state.throwable.message!!,
						length = Snackbar.LENGTH_INDEFINITE,
						actionText = getString(R.string.reload),
						action = {
							viewModel.getAccountsGitHub()
						})
				} else {
					binding.root.showMessage(
						message = getString(R.string.unknown_error),
						length = Snackbar.LENGTH_LONG,
						actionText = getString(R.string.reload),
						action = {
							viewModel.getAccountsGitHub()
						})
				}
			}
			is AccountsAppState.OnLoading -> {
				binding.progressBar.visibility = if (state.load) {
					View.VISIBLE
				} else {
					View.GONE
				}
			}
			is AccountsAppState.OnSuccess -> {
				adapter.setAccountList(state.success)
			}
		}

	}

	override fun onDestroy() {
		_binding = null
		super.onDestroy()
	}
}