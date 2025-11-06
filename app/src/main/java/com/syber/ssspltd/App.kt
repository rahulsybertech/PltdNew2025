package com.syber.ssspltd
import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App: Application() {
 //   lateinit var repository: UserRepository

    override fun onCreate() {
        super.onCreate()
        // Initialize timber to provide logging without need to add TAG to logs
     //   Timber.plant(Timber.DebugTree())
       // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // initialize()
    }
  /*  private fun initialize() {
        val quoteService = RetrofitHelper.getInstance().create(ApiService::class.java)
        repository= UserRepository(quoteService)

    }*/
}