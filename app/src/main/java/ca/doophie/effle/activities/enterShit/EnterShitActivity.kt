package ca.doophie.effle.activities.enterShit;

import android.os.Bundle
import ca.doophie.doophrame.models.doophieActivityModel.DoophieActivity
import ca.doophie.effle.R
import ca.doophie.effle.activities.displayShit.DisplayShitActivity
import ca.doophie.effle.activities.displayShit.DisplayShitDependency
import ca.doophie.effle.activities.newKindOfActivity.NewKindOfActivity
import ca.doophie.effle.activities.newKindOfActivity.NewKindOfTraveller
import ca.doophie.effle.models.Shit
import kotlinx.android.synthetic.main.activity_entershit.*

class EnterShitActivity : DoophieActivity() {

    override val tag: String = " [enterShit] "

    override val animIn: Int = R.anim.slide_right
    override val animOut: Int = R.anim.slide_out_right

    private lateinit var shit: Shit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entershit)

        try { actionBar.title = "Enter some shit" } catch (_: Exception) {}

        // collect any objects from your dependency using the 
        // intent - as long as this activity was loaded with a switch call
        // they should be there (y)

        shit = getPref(DisplayShitDependency.SHIT, false) ?: Shit()

        button_enterShit.setOnClickListener {
            shit.dropNug(shitBox.text.toString())
        }

        button_viewShit.setOnClickListener {
            NewKindOfTraveller()
        }
    }

    override fun onDestroy() {
        savePrefNow(DisplayShitDependency.SHIT, shit)
        super.onDestroy()
    }

}
