package com.fave.breezil.fave.utils

import android.annotation.SuppressLint
import android.content.res.Resources
import com.fave.breezil.fave.R

private const val SECOND_MILLIS = 1000
private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
private const val DAY_MILLIS = 24 * HOUR_MILLIS

/**
 * Source for this function: https://stackoverflow.com/a/13018647/5992987
 */
@SuppressLint("StringFormatMatches")
fun getTimeAgo(time: Long, now: Long, resources: Resources): String {
  var timeAgo = time
  if (timeAgo < 1000000000000L) {
    // if timestamp given in seconds, convert to millis
    timeAgo *= 1000
  }
  if (timeAgo > now || timeAgo <= 0) {
    throw IllegalStateException("Time $timeAgo is in the future")
  }

  val diff = now - timeAgo
  return when {
    diff < MINUTE_MILLIS -> resources.getString(R.string.time_ago_just_now)
    diff < 2 * MINUTE_MILLIS -> resources.getString(R.string.time_ago_a_minute)
    diff < 50 * MINUTE_MILLIS -> resources.getString(
      R.string.time_ago_minutes,
      "${diff / MINUTE_MILLIS}"
    )
    diff < 90 * MINUTE_MILLIS -> resources.getString(R.string.time_ago_an_hour)
    diff < 24 * HOUR_MILLIS -> resources.getString(
      R.string.time_ago_hours,
      "${diff / HOUR_MILLIS}"
    )
    diff < 48 * HOUR_MILLIS -> resources.getString(R.string.time_ago_yesterday)
    else -> resources.getString(R.string.time_ago_days, "${diff / DAY_MILLIS}")
  }
}