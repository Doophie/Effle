package ca.doophie.effle

import android.app.Application
import android.content.Context
import ca.doophie.doophrame.Models.Colours

class DoophieApp(): Application() {

    override fun onCreate() {
        super.onCreate()
        Colours.init(applicationContext)
    }
}