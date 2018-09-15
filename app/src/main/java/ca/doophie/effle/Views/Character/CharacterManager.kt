    package ca.doophie.effle.Views.Character

    import android.content.Context
    import ca.doophie.doophrame.models.viewModels.DoophieManager
    import ca.doophie.doophrame.models.viewModels.DoophieView
    import ca.doophie.effle.Colours

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