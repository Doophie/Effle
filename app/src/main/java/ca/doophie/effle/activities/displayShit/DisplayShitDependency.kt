package ca.doophie.effle.activities.displayShit;

import ca.doophie.doophrame.models.doophieActivityModel.DoophieActivity.Dependency
import ca.doophie.effle.models.Shit
import java.io.Serializable

// Use the Dependency to declare all the objects needed by the Activity
// All objects can be retrieved from the extras of the intent of the activitys onCreate method
class DisplayShitDependency(shit: Shit) : Dependency {

    // the dependencies variable holds all the objects needed by this activity
    override val dependencies = HashMap<String, Serializable>()

    // the companion object holds all the keys
    companion object {
        const val SHIT = "ShittyKey"
    }

    // when we initialize the dependency we fill the hash map with each key/value
    // each object in the dependency variable will be put as an extra in the received intent
    init {
        dependencies[SHIT] = shit
    }
}
