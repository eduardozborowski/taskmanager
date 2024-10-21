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

    @Query("SELECT * FROM tarefa_table ORDER BY dataLimite ASC")
    fun getAllTarefas(): LiveData<List<Tarefa>>
}

