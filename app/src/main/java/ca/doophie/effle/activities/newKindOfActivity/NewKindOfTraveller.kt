package ca.doophie.effle.activities.newKindOfActivity

import ca.doophie.doophrame.models.activity.DoophieTraveller
import ca.doophie.doophrame.models.activity.DoophieWorker
import ca.doophie.effle.R
import java.io.Serializable

class NewKindOfTraveller: DoophieTraveller() {

    override val activityClass: Class<*> = NewKindOfActivity::class.java

    override val animIn: Int = R.anim.slide_right
    override val animOut: Int = R.anim.slide_out_right

    override val worker: DoophieWorker = NewKindOfWorker()

}

class NewKindOfDependency: DoophieTraveller.Dependency {
    override val dependencies: HashMap<String, Serializable> = HashMap()
}