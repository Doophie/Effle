package ca.doophie.effle.activities.newKindOfView

import android.view.ViewGroup
import ca.doophie.doophrame.models.activity.DoophieWorker
import kotlin.concurrent.thread

class NewKindOfViewWorker: DoophieWorker(), NewKindOfViewCallbacks {

    var isRunning = false

    lateinit var listener: NewKindOfViewCallbacks

    val view: NewKindOfView
        get() = traveller.view as NewKindOfView

    override fun start() {
        isRunning = true
        updateThread()
        view.listener = this
    }

    private fun updateThread() {
        thread {
            view.randomizeBackground()
            Thread.sleep(1000)
            view.setUpListeners()
            updateThread()
        }
    }

    override fun closeView() {
        isRunning = false
        (view.parent as ViewGroup).removeView(view)
    }
}