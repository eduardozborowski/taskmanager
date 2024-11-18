package com.taskmanager.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.taskmanager.model.Tarefa

@Dao
interface TarefaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(tarefa: Tarefa): Long

    @Update
    suspend fun update(tarefa: Tarefa): Int

    @Delete
    suspend fun delete(tarefa: Tarefa): Int

    @Query("SELECT * FROM tarefa_table ORDER BY " +
            "CASE WHEN prioridade = 'ALTA' THEN 1 " +
            "WHEN prioridade = 'MEDIA' THEN 2 " +
            "WHEN prioridade = 'BAIXA' THEN 3 END, " +
            "dataLimite ASC")
    fun getAllTarefas(): LiveData<List<Tarefa>>

    @Query("SELECT * FROM tarefa_table WHERE id = :id")
    fun getTarefaById(id: Int): LiveData<Tarefa>
}
