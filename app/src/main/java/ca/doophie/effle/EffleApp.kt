package ca.doophie.effle

import android.app.Application
import android.content.Context

class EffleApp(): Application() {

    override fun onCreate() {
        super.onCreate()
        Colours.init(applicationContext)
    }
}