package com

import android.app.Application
import com.tonyxlab.echojournal.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class EchoJournalApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {


            Timber.plant(Timber.DebugTree())
        }
    }
}