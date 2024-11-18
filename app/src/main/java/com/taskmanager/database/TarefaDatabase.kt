package com.taskmanager.database

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.taskmanager.dao.TarefaDao
import com.taskmanager.model.Tarefa

@Database(entities = [Tarefa::class], version = 2, exportSchema = false)
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
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Adicionar a coluna 'prioridade' à tabela existente
                database.execSQL("ALTER TABLE tarefa_table ADD COLUMN prioridade TEXT NOT NULL DEFAULT 'MEDIA'")
            }
        }
    }
}
