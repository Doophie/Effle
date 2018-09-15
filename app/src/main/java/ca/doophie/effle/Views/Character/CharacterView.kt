package ca.doophie.effle.Views.Character

import android.content.Context
import android.view.LayoutInflater
import android.widget.RelativeLayout
import ca.doophie.doophrame.models.viewModels.DoophieView
import ca.doophie.effle.R

interface CharacterViewListener {
    fun handleCharacterClicked()
}

class CharacterView(context: Context, val listener: CharacterViewListener) : DoophieView(context){

    override val rootView = LayoutInflater.from(context).inflate(R.layout.charater_view, null) as RelativeLayout

    var clickIteration = false

    override fun setUpListeners() {

        rootView.setOnClickListener { listener.handleCharacterClicked() }

    }
}