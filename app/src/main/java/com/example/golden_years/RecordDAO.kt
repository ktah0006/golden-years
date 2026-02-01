package com.example.golden_years

import HealthRecord
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDAO {
//    @Query("SELECT * FROM Records")
//    fun getAllRecords(): Flow<List<Record>>

    @Query("SELECT * FROM records WHERE userId = :userId ORDER BY createdAt DESC")
    fun getUserRecords(userId: String): Flow<List<HealthRecord>>
    @Insert
    suspend fun insertRecord(record: HealthRecord)
    @Update
    suspend fun updateRecord(record: HealthRecord)
    @Delete
    suspend fun deleteRecord(record: HealthRecord)
}