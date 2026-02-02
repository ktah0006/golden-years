package com.example.golden_years

import HealthRecord
import android.app.Application
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import java.util.Date

class RecordRepository(application: Application) {
    private val db = FirebaseFirestore.getInstance()

    private val recordDao: RecordDAO =
        RecordDatabase.getDatabase(application).recordDAO()

    fun getRecords(userId: String): Flow<List<HealthRecord>> {
        return recordDao.getUserRecords(userId)
    }

    suspend fun insert(record: HealthRecord) {
        // insert in Room
        recordDao.insertRecord(record)

        // insert in firestore
        try {

            val recordForFirestore = mapOf(
                "userId" to record.userId,
                "bpSystolic" to record.bpSystolic,
                "bpDiastolic" to record.bpDiastolic,
                "glucose" to record.glucose,
                "mealTiming" to record.mealTiming,
                "createdAt" to Timestamp(Date(record.createdAt))
            )

            val recordsDocRef = db.collection("users")
                .document(record.userId)
                .collection("records")
//                .add(record)
                .add(recordForFirestore)
                .await()

            // update Health Record firestore fields after inserting in firestore
            recordDao.updateRecord(
                record.copy(
                    firestoreSynced = true,
                    firestoreId = recordsDocRef.id,
                )
            )

        } catch (e: Exception){
            Log.e("RecordRepository", "Failed to sync record", e)
        }
    }

    suspend fun update(record: HealthRecord) {
        recordDao.updateRecord(record)
    }

    suspend fun delete(record: HealthRecord) {
        recordDao.deleteRecord(record)
    }
}