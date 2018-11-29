package ca.doophie.effle.activities.newKindOfActivity

import android.os.Bundle
import ca.doophie.doophrame.models.activity.DoophieActivity
import ca.doophie.effle.R
import kotlinx.android.synthetic.main.activity_newkindof.*

interface NewKindOfActivityCallbacks {
    fun openNewView()
}

class NewKindOfActivity: DoophieActivity() {

    lateinit var listener: NewKindOfActivityCallbacks

    override val layout: Int = R.layout.activity_newkindof

    override val tag: String = "[NewKindOfActivity]"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        newkindOfButton.setOnClickListener {
            listener.openNewView()
        }
    }

}