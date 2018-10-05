package ca.doophie.effle.activities.displayShit;

import android.os.Bundle
import ca.doophie.doophrame.extensions.getObject
import ca.doophie.doophrame.models.doophieActivityModel.DoophieActivity
import ca.doophie.doophrame.models.viewModels.DoophieManager
import ca.doophie.effle.R
import ca.doophie.effle.activities.enterShit.EnterShitActivity
import ca.doophie.effle.activities.enterShit.EnterShitDependency
import ca.doophie.effle.models.Shit
import ca.doophie.effle.views.ShitDisplayerManager
import kotlinx.android.synthetic.main.activity_displayshit.*
import java.io.Serializable
import java.lang.Exception

class DisplayShitActivity : DoophieActivity() {

    override val tag: String = " [displayShit] "

    var shitDisplayer: ShitDisplayerManager? = null

    override val animIn: Int = R.anim.slide_left
    override val animOut: Int = R.anim.slide_out_left

    lateinit var shit: Shit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_displayshit)

        try { actionBar.title = "Here's the shit you've entered" } catch (_: Exception) {}

        // collect any objects from your dependency using the
        // intent - as long as this activity was loaded with a switch call
        // they should be there (y)
        shit = intent.getObject(DisplayShitDependency.SHIT) ?: return

        shitDisplayer = ShitDisplayerManager(shit)

        shitDisplayer?.fillView(displayShit_rootView, applicationContext, object: DoophieManager.DoophieManagerExitCallback() {
            override fun exit(exitCode: Int) {

            }
        }, R.anim.slide_left)
    }

    override fun onBackPressed() {
        savePrefNow(DisplayShitDependency.SHIT, shit, false)
        switch(EnterShitActivity(), EnterShitDependency())
    }

    override fun onDestroy() {
        savePrefNow(DisplayShitDependency.SHIT, shit)
        super.onDestroy()
    }

}
