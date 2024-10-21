package com.taskmanager.database

import android.content.Context
import androidx.room.*
import androidx.room.RoomDatabase
import com.taskmanager.dao.TarefaDao
import com.taskmanager.model.Tarefa

@Database(entities = [Tarefa::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TarefaDatabase : RoomDatabase() {

    abstract fun tarefaDao(): TarefaDao

    companion object {
        @Volatile
        private var INSTANCE: TarefaDatabase? = null

        fun getDatabase(context: Context): TarefaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TarefaDatabase::class.java,
                    "tarefa_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
