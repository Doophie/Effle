package ca.doophie.effle.activities.newKindOfView

import android.content.Context
import android.view.ViewGroup
import ca.doophie.doophrame.models.activity.DoophieView
import ca.doophie.effle.R

class NewKindOfView(context: Context, parent: ViewGroup): DoophieView(context, parent) {

    override val layout: Int = R.layout.view_newkindofview

    override val tag: String = "[NewKindOfView]"

}