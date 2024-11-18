package com.taskmanager.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

enum class Prioridade {
    ALTA,
    MEDIA,
    BAIXA
}

@Entity(tableName = "tarefa_table")
data class Tarefa(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val titulo: String,
    val descricao: String,
    val dataLimite: Date?,
    val concluida: Boolean = false,
    val prioridade: Prioridade = Prioridade.MEDIA
)
