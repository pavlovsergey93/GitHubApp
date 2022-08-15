package com.gmail.pavlovsv93.githubapp

import android.app.Application
import com.gmail.pavlovsv93.retrofit.di.retrofitModule
import com.gmail.pavlovsv93.viewmodel.di.viewModelModule
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
	override fun onCreate() {
		super.onCreate()
		startKoin {
			androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
			androidContext(this@App)
			modules(appModule, retrofitModule, viewModelModule)
		}
	}
}