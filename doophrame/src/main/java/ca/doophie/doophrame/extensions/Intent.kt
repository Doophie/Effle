package ca.doophie.doophrame.extensions

import android.content.Intent
import ca.doophie.doophrame.models.ObjectSerializer
import java.io.Serializable

fun <T: Serializable>Intent.getObject(key: String) : T? {
    return ObjectSerializer.deserialize(this.extras.getString(key))
}

fun <T: Serializable>Intent.putObject(key: String, obj: T) {
    this.putExtra(key, ObjectSerializer.serialize(obj))
}