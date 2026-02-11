package com.example.golden_years.record_room

import com.example.golden_years.record_room.HealthRecord
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

// taken and adapted from Lab Week 5
@Dao
interface RecordDAO {
    @Query("SELECT * FROM records WHERE userId = :userId ORDER BY createdAt DESC")
    fun getUserRecords(userId: String): Flow<List<HealthRecord>>

    @Insert
    suspend fun insertRecord(record: HealthRecord)
    @Update
    suspend fun updateRecord(record: HealthRecord)
    @Delete
    suspend fun deleteRecord(record: HealthRecord)

    @Query("DELETE FROM records")
    suspend fun deleteAllRecords()

    @Query("SELECT * FROM records WHERE userId = :userId ORDER BY createdAt DESC LIMIT 6")
    fun getRecentRecords(userId: String): Flow<List<HealthRecord>>
}