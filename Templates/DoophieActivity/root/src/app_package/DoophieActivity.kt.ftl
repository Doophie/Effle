package ${packageName};

import android.os.Bundle
import ca.doophie.doophrame.models.doophieActivityModel.DoophieActivity
import kotlinx.android.synthetic.main.activity_main.*

class ${className}Activity : DoophieActivity() {

    override val tag: String = "[${className}] "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_${className?lower_case})
        
        // collect any objects from your dependency using the 
        // intent
    }

}