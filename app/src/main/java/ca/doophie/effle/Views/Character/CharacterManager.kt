package ca.doophie.effle.Views.Character

import android.content.Context
import android.support.v4.content.ContextCompat
import ca.doophie.effle.Models.Colours
import ca.doophie.effle.Models.ViewModels.DoophieManager
import ca.doophie.effle.Models.ViewModels.DoophieView
import ca.doophie.effle.R

class CharacterManager: DoophieManager(),
// Implements
CharacterViewListener {

    private var clickIteration = false

    override fun makeRootView(context: Context): DoophieView {
        return CharacterView(context, this)
    }

    override fun viewLoaded() {
        // do something
    }

    /***
     * View callback methods
     */
    override fun handleCharacterClicked() {
        rootView?.setBackgroundColor(
                if (clickIteration)
                    Colours.primary
                else
                    Colours.accent
        )
        clickIteration = !clickIteration
    }

}