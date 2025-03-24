package com.triad.mobile.data.source.local.converters

import androidx.room.TypeConverter
import java.util.Date

/**
 * Конвертер типов Date для Room
 */
class DateConverter {
    /**
     * Конвертирует метку времени (timestamp) в объект Date
     */
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }
    
    /**
     * Конвертирует объект Date в метку времени (timestamp)
     */
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
} 