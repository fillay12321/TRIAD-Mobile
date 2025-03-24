package com.triad.mobile.data.source.local.converters

import androidx.room.TypeConverter
import java.math.BigDecimal

/**
 * Конвертер типов BigDecimal для Room
 */
class DecimalConverter {
    /**
     * Конвертирует строку в BigDecimal
     */
    @TypeConverter
    fun fromString(value: String?): BigDecimal? {
        return value?.let { BigDecimal(it) }
    }
    
    /**
     * Конвертирует BigDecimal в строку
     */
    @TypeConverter
    fun toString(decimal: BigDecimal?): String? {
        return decimal?.toPlainString()
    }
} 