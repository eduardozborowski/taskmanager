package com.taskmanager.repository

import androidx.lifecycle.LiveData
import com.taskmanager.dao.TarefaDao
import com.taskmanager.model.Tarefa

class TarefaRepository(private val tarefaDao: TarefaDao) {

    val allTarefas: LiveData<List<Tarefa>> = tarefaDao.getAllTarefas()

    suspend fun insert(tarefa: Tarefa) {
        tarefaDao.insert(tarefa)
    }

    suspend fun update(tarefa: Tarefa) {
        tarefaDao.update(tarefa)
    }

    suspend fun delete(tarefa: Tarefa) {
        tarefaDao.delete(tarefa)
    }
}
