package ${packageName};

import ca.doophie.doophrame.models.doophieActivityModel.DoophieActivity.Dependency
 
// Use the Dependency to declare all the objects needed by the Activity
// All objects can be retrieved from the extras of the intent of the activitys onCreate method
class ${className}Dependency : Dependency{

    override val dependencies = HashMap<String, String>()
}
