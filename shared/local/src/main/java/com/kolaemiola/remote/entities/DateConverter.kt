package com.kolaemiola.remote.entities

import androidx.room.TypeConverter
import java.util.Date

class DateConverter {

  @TypeConverter
  fun fromTimestamp(value: Long?): Date? {
    return if (value == null) null else Date(value)
  }

  @TypeConverter
  fun dateToTimestamp(date: Date?): Long? {
    return date?.time
  }

}