package ca.doophie.effle.Activities

import android.os.Bundle
import ca.doophie.doophrame.Models.DoophieActivityModel.DoophieActivity
import ca.doophie.doophrame.UIObjects.Joystick
import ca.doophie.effle.Activities.AdventureActivity.AdventureDependency
import ca.doophie.effle.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : DoophieActivity() {

    override val TAG: String = this::class.java.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_rootView.setOnClickListener {
            switch(AdventureActivity(), AdventureDependency("doophie"))
        }

    }

    class MainDependency: DoophieActivity.Dependency {
        override val dependencies = HashMap<String, String>()
    }

}