package com.gmail.pavlovsv93.githubapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentResultListener
import com.gmail.pavlovsv93.githubapp.R
import com.gmail.pavlovsv93.githubapp.ui.detailsfragment.DetailsFragment.Companion.ARG_URL_REPO
import com.gmail.pavlovsv93.githubapp.ui.detailsfragment.DetailsFragment.Companion.KEY_URL_REPO
import com.gmail.pavlovsv93.githubapp.ui.homefragment.HomeFragment

class GitHubAppActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_git_hub_app)

		if (savedInstanceState == null) {
			supportFragmentManager.beginTransaction()
				.replace(R.id.fragmentContainerView, HomeFragment.newInstance())
				.commit()
		}

		supportFragmentManager.setFragmentResultListener(
			KEY_URL_REPO,
			this,
			FragmentResultListener { _, result ->
				val url = result.getString(ARG_URL_REPO)
				val uri: Uri = Uri.parse(url)
				val browser = Intent(Intent.ACTION_VIEW, uri)
				startActivity(browser)
			})
	}
}