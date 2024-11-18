package com.taskmanager.database

import androidx.room.TypeConverter
import com.taskmanager.model.Prioridade
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    // Conversores para a enumeração Prioridade
    @TypeConverter
    fun fromPrioridade(value: String): Prioridade {
        return Prioridade.valueOf(value)
    }

    @TypeConverter
    fun prioridadeToString(prioridade: Prioridade): String {
        return prioridade.name
    }
}
