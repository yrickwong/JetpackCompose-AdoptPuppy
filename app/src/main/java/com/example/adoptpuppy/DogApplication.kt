package com.example.adoptpuppy

import android.app.Application
import com.airbnb.mvrx.Mavericks

/**
 * Launcher icon made by Freepik at flaticon.com.
 */
class DogApplication : Application() {
    val dogsRepository = DogRepository()

    override fun onCreate() {
        super.onCreate()
        Mavericks.initialize(this)
    }
}