package ca.doophie.effle.activities.newKindOfView

import android.content.Context
import android.view.ViewGroup
import ca.doophie.doophrame.models.activity.DoophieTraveller
import ca.doophie.doophrame.models.activity.DoophieView
import ca.doophie.doophrame.models.activity.DoophieWorker
import ca.doophie.effle.R
import java.io.Serializable

class NewKindOfViewTraveller(context: Context): DoophieTraveller() {

    override val activityClass: Class<*> = NewKindOfView::class.java

    override val animIn: Int = R.anim.fade_in
    override val animOut: Int = R.anim.fade_out

    override val worker: DoophieWorker = NewKindOfViewWorker()

    init {
        view = NewKindOfView(context)
    }

}

class NewKindOfViewDependency(): DoophieTraveller.Dependency {
    override val dependencies: HashMap<String, Serializable> = HashMap()

}
