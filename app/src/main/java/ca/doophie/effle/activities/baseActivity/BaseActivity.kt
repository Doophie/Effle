package ca.doophie.effle.activities.baseActivity

import android.os.Bundle
import android.util.Log
import ca.doophie.doophrame.models.activity.*
import ca.doophie.effle.R
import ca.doophie.effle.activities.newKindOfActivity.NewKindOfDependency
import ca.doophie.effle.activities.newKindOfActivity.NewKindOfTraveller
import kotlinx.android.synthetic.main.activity_base.*

class BaseActivity: DoophieActivity() {

    override val layout: Int = R.layout.activity_base

    override val tag: String = "[BaseActivity]"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(tag, "Creating base activity.")

        val rand = (Math.random() * 100).toInt() % 50

        baseText.text = getString(R.string.random_text).format(rand)

        baseButton.setOnClickListener {
            baseListener?.switchToActivity(NewKindOfTraveller(), NewKindOfDependency())
        }
    }

}

