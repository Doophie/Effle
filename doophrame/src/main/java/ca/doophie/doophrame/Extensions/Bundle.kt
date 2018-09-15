package ca.doophie.doophrame.Extensions

import android.os.Bundle
import ca.doophie.doophrame.Models.DoophieObject

fun Bundle.getDoophieObject(key: String): DoophieObject {
    return DoophieObject(getByteArray(key))
}