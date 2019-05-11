package ca.doophie.doophrame.models.fargment

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class DoophieWorker(appContext: Context, workerParams: WorkerParameters)
    : Worker(appContext, workerParams) {

    override fun doWork(): Result {



        return Result.success()
    }

    fun <T: ((Any)->Result)>executeTask(task: T, params: Any) {
        task(params)
    }

}


