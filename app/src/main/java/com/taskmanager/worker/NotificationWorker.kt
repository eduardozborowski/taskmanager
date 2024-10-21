package com.taskmanager.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.taskmanager.R

class NotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        val titulo = inputData.getString("titulo") ?: "Tarefa"
        val descricao = inputData.getString("descricao") ?: "Você tem uma tarefa pendente."

        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "tarefa_channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Tarefas", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle(titulo)
            .setContentText(descricao)
            .setSmallIcon(R.drawable.ic_notification)
            .build()

        notificationManager.notify(1, notification)

        return Result.success()
    }
}
