package com.example.golden_years

import HealthRecord
import android.app.Application
import kotlinx.coroutines.flow.Flow

class RecordRepository(application: Application) {

    private val recordDao: RecordDAO =
        RecordDatabase.getDatabase(application).recordDAO()

    fun getRecords(userId: String): Flow<List<HealthRecord>> {
        return recordDao.getUserRecords(userId)
    }

    suspend fun insert(record: HealthRecord) {
        recordDao.insertRecord(record)
    }

    suspend fun update(record: HealthRecord) {
        recordDao.updateRecord(record)
    }

    suspend fun delete(record: HealthRecord) {
        recordDao.deleteRecord(record)
    }
}