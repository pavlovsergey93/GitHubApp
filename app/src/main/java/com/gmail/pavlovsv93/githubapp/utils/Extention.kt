package com.gmail.pavlovsv93.githubapp.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showMessage(
	message: String = "",
	length: Int = Snackbar.LENGTH_SHORT
) {
	Snackbar.make(this, message, length).show()
}
fun View.showMessage(
	message: String = "",
	length: Int = Snackbar.LENGTH_SHORT,
	actionText: String,
	action: (View) -> Unit
) {
	Snackbar.make(this, message, length)
		.setAction(actionText, action)
		.show()
}
