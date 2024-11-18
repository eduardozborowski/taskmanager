package com.taskmanager.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.taskmanager.database.TarefaDatabase
import com.taskmanager.model.Tarefa
import com.taskmanager.repository.TarefaRepository
import kotlinx.coroutines.launch

class TarefaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TarefaRepository
    val allTarefas: LiveData<List<Tarefa>>

    init {
        val tarefaDao = TarefaDatabase.getDatabase(application).tarefaDao()
        repository = TarefaRepository(tarefaDao)
        allTarefas = repository.allTarefas
    }

    fun insert(tarefa: Tarefa) = viewModelScope.launch {
        repository.insert(tarefa)
    }

    fun update(tarefa: Tarefa) = viewModelScope.launch {
        repository.update(tarefa)
    }

    fun delete(tarefa: Tarefa) = viewModelScope.launch {
        repository.delete(tarefa)
    }

    fun getTarefaById(id: Int): LiveData<Tarefa> {
        return repository.getTarefaById(id)
    }
}
