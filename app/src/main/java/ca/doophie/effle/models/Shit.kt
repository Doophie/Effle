package ca.doophie.effle.models

import java.io.Serializable

class Shit: Serializable {
    val nuggets: ArrayList<String> = ArrayList()

    fun dropNug(nugget: String){
        nuggets.add(nugget)
    }
}
