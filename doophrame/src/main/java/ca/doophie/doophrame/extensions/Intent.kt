package ca.doophie.doophrame.extensions

import android.content.Intent
import ca.doophie.doophrame.models.ObjectSerializer
import java.io.Serializable

// TODO: Handle encryption / decryption here

fun <T: Serializable>Intent.getObject(key: String) : T? {
    if (this.extras == null) return null
    return ObjectSerializer.deserialize(this.extras.getString(key))
}

fun <T: Serializable>Intent.putObject(key: String, obj: T) {
    this.putExtra(key, ObjectSerializer.serialize(obj))
}