package com.example.golden_years

// Taken and adapted from Week 5 Lab
// Creates and provides a single instance of your Room database for the whole app
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import HealthRecord
@Database(entities = [HealthRecord::class], version = 2, exportSchema = false)
// You will never instantiate this class yourself
abstract class RecordDatabase : RoomDatabase() {
    abstract fun recordDAO(): RecordDAO // to use this: val dao = db.recordDAO()
    companion object { // companion object in Kotlin is the same as static in Java
        @Volatile
        private var INSTANCE: RecordDatabase? = null // holds the one and only database instance

        // return database instance if it exists, otherwise, block this and create it
        fun getDatabase(context: Context): RecordDatabase {
            return INSTANCE ?: synchronized(this) {

                // creating the instance
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecordDatabase::class.java,
                    "golden_years_record_db"
                )
                    .fallbackToDestructiveMigration(true) // If the schema changes and no migration is provided, delete the database and recreate it
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}