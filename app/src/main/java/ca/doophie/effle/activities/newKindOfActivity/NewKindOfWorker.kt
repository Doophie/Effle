package ca.doophie.effle.activities.newKindOfActivity

import android.util.Log
import android.widget.FrameLayout
import ca.doophie.doophrame.models.activity.DoophieWorker
import ca.doophie.effle.R
import ca.doophie.effle.activities.newKindOfView.NewKindOfView
import ca.doophie.effle.activities.newKindOfView.NewKindOfViewDependency
import ca.doophie.effle.activities.newKindOfView.NewKindOfViewTraveller
import kotlin.concurrent.thread

class NewKindOfWorker: DoophieWorker(), NewKindOfActivityCallbacks {

    val activity: NewKindOfActivity?
        get() = traveller.activity as NewKindOfActivity?

    override fun start() {
        // do something here
        activity?.listener = this
    }

    // Activity callbacks
    override fun openNewView() {
        val viewHolder: FrameLayout = activity?.findViewById(R.id.newKindOfViewHolder) ?: return
        traveller.present(NewKindOfViewTraveller(viewHolder.context), NewKindOfViewDependency(), viewHolder)
    }

}