package com.fave.breezil.fave.db

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

  protected var db: AppDatabase? = null

  @Before
  fun initDb() {
    db = Room.inMemoryDatabaseBuilder(
        InstrumentationRegistry.getInstrumentation().context,
      AppDatabase::class.java
    ).build()
  }

  @After
  fun closeDb() {
    db!!.close()
  }
}