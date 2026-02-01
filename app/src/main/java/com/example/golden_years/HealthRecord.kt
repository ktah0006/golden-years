import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "records")
data class HealthRecord(
    @PrimaryKey(autoGenerate = true)
    val recordId: Int = 0,

    val userId: String,
    val bpSystolic: Int,
    val bpDiastolic: Int,
    val glucose: Int,
    val mealTiming: String,
    val createdAt: Long,

    val firestoreSynced: Boolean = false,
    val firestoreId: String? = null

)