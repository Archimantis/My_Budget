package br.com.alma.mybudget.persistencia

import androidx.room.TypeConverter
import java.util.Date

object Conversores {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
