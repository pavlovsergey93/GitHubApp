package com.gmail.pavlovsv93.githubapp.ui.detailsfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.pavlovsv93.githubapp.R
import com.gmail.pavlovsv93.githubapp.databinding.FragmentDetailsBinding
import com.gmail.pavlovsv93.githubapp.utils.showMessage
import com.gmail.pavlovsv93.viewmodel.details.DetailsAppState
import com.gmail.pavlovsv93.viewmodel.details.DetailsViewModel
import com.gmail.pavlovsv93.viewmodel.di.DETAILS_VIEW_MODEL
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import kotlin.math.log

class DetailsFragment : Fragment() {
	companion object {
		const val ARG_URL_REPO = "ARG_URL_REPO"
		const val KEY_URL_REPO = "KEY_URL_REPO"
		const val ARG_LOGIN = "ARG_URL_REPOS"
		fun newInstance(login: String): DetailsFragment =
			DetailsFragment().apply {
				arguments = Bundle().apply {
						putString(ARG_LOGIN, login)
					}
				}
	}

	interface OnClickToItem {
		fun clickedToItem(reposUrl: String)
	}

	private var _binding: FragmentDetailsBinding? = null
	private val binding get() = _binding!!
	private val viewModel: DetailsViewModel by viewModel(named(DETAILS_VIEW_MODEL))
	private var login: String? = null
	private val adapter: DetailsRvAdapter = DetailsRvAdapter(object : OnClickToItem {
		override fun clickedToItem(reposUrl: String) {
			val data: Bundle = Bundle().apply {
				putString(ARG_URL_REPO, reposUrl)
			}
			parentFragmentManager.setFragmentResult(KEY_URL_REPO, data)
		}
	})

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val recyclerView = binding.detailsRecyclerView
		recyclerView.layoutManager =
			LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
		recyclerView.adapter = adapter

		login = arguments?.getString(ARG_LOGIN)

		viewModel.getLiveData().observe(viewLifecycleOwner, Observer { state ->
			rangerData(state)
		})
		login?.let {
			viewModel.getRepositoriesAccount(it)
		}
	}

	private fun rangerData(state: DetailsAppState) {
		when (state) {
			is DetailsAppState.OnError -> {
				if (!state.throwable.message.isNullOrEmpty()) {
					binding.root.showMessage(
						message = state.throwable.message!!,
						length = Snackbar.LENGTH_INDEFINITE,
						actionText = getString(R.string.reload),
						action = {
							login?.let {
								viewModel.getRepositoriesAccount(it)
							}
						})
				} else {
					binding.root.showMessage(
						message = getString(R.string.unknown_error),
						length = Snackbar.LENGTH_LONG,
						actionText = getString(R.string.reload),
						action = {
							login?.let {
								viewModel.getRepositoriesAccount(it)
							}
						})
				}
			}
			is DetailsAppState.OnLoading -> {
				binding.progressBar.visibility = if (state.load) {
					View.VISIBLE
				} else {
					View.GONE
				}
			}
			is DetailsAppState.OnSuccess -> {
				adapter.setListRepos(state.success)
			}
		}

	}
}