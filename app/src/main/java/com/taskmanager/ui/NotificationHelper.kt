package com.taskmanager.ui

import android.content.Context
import androidx.work.*
import com.taskmanager.model.Tarefa
import com.taskmanager.worker.NotificationWorker
import java.util.concurrent.TimeUnit

object NotificationHelper {

    fun scheduleNotification(context: Context, tarefa: Tarefa) {
        val workManager = WorkManager.getInstance(context)
        val data = Data.Builder()
            .putString("titulo", tarefa.titulo)
            .putString("descricao", tarefa.descricao)
            .build()

        val delay = tarefa.dataLimite?.time?.minus(System.currentTimeMillis()) ?: 0L

        if (delay > 0) {
            val notificationWork = OneTimeWorkRequest.Builder(NotificationWorker::class.java)
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .setInputData(data)
                .build()

            workManager.enqueue(notificationWork)
        }
    }
}
