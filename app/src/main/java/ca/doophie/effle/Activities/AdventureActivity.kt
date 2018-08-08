package ca.doophie.effle.Activities

import android.os.Bundle
import android.view.MotionEvent
import ca.doophie.effle.Models.DoophieActivity
import ca.doophie.effle.R
import ca.doophie.effle.Views.Character.CharacterManager
import ca.doophie.effle.Views.ControllerInterface.ControllerManager
import ca.doophie.effle.Views.ControllerInterface.ControllerManager.ControllerManagerDependency
import ca.doophie.effle.Views.ControllerInterface.ControllerManagerListener
import kotlinx.android.synthetic.main.activity_adventure.*

class AdventureActivity: DoophieActivity(), ControllerManagerListener {

    // required variables for distinguishing this activity and its dependencies
    override val TAG: String = this::class.java.toString()
    override val dependencyType = AdventureDependency::class.java

    // Information about the players character
    var characterName = ""

    // the view managers
    var characterManager: CharacterManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adventure)

        // because of our dependency we can guarantee this will be in the intent
        characterName = intent.extras.getString(AdventureDependency.CHARACTER_NAME)

        // fill the activity views
        CharacterManager().fillView(characterFrame, applicationContext)

        val controllerDependency = ControllerManagerDependency(screenWidth, screenHeight)
        ControllerManager(this, controllerDependency).fillView(controllerInterfaceFrame, applicationContext)
    }

    override fun onBackPressed() {
        clearAllViews()
        switch(MainActivity(), MainActivity.MainDependency())
    }

    // clears all view managers to free memory
    private fun clearAllViews(){
        characterManager?.popView()
        characterManager = null
    }

    override fun handleJoystickMoved(strength: Double, angle: Double) {
        val decider = (angle/90 % 4).toFloat() - 2
        characterFrame.x += if (decider > 0) { if (decider == 1f) 5 else -5 } else 0
        characterFrame.y += if (decider < 0) { if (decider == -1f) -5 else 5 } else 0
    }


    /***
     * Adventure dependencies
     *-------------------------
     * These are the required objects in order to create this kind of activity
     */

    // the dependency class can collect all objects they need in the constructor
    class AdventureDependency(characterName: String): Dependency() {

        // the dependencies variable holds all the objects needed by this activity
        override val dependencies = HashMap<String, String>()

        // the companion object holds all the
        companion object {
            const val CHARACTER_NAME = "CharacterName"
        }

        // when we initialize the dependency we fill the hash map with each key/value
        // each object in the dependency variable will be put as an extra in the received intent
        init {
            dependencies[CHARACTER_NAME] = characterName
        }
    }
}
