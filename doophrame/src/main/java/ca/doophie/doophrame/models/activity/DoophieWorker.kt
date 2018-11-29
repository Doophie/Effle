package ca.doophie.doophrame.models.activity

import java.io.Serializable

abstract class DoophieWorker: Serializable {

    lateinit var traveller: DoophieTravellable

    abstract fun start()

}