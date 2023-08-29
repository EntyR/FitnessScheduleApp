package health.fit.bodyz.app.data.db.entity


import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class WorkoutEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val desc: String,
    val timestamp: String
)