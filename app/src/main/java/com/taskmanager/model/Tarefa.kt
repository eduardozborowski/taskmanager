package com.taskmanager.model

import androidx.room.*
import androidx.room.Entity
import java.util.Date

@Entity(tableName = "tarefa_table")
data class Tarefa(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val titulo: String,
    val descricao: String,
    val dataLimite: Date?,
    val concluida: Boolean = false
)
