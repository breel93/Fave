package com.fave.breezil.fave.model

import androidx.room.TypeConverter
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.Date

class Converters {

  @TypeConverter
  fun fromTimestamp(value: Long?): Date? {
    return if (value == null) null else Date(value)
  }

  @TypeConverter
  fun dateToTimestamp(date: Date?): Long? {
    return date?.time
  }

}

//object Converters {
//  private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
//
//  @TypeConverter
//  @JvmStatic
//  fun toOffsetDateTime(value: String?): OffsetDateTime? {
//    return value?.let {
//      return formatter.parse(value, OffsetDateTime::from)
//    }
//  }
//
//  @TypeConverter
//  @JvmStatic
//  fun fromOffsetDateTime(date: OffsetDateTime?): String? {
//    return date?.format(formatter)
//  }
//}