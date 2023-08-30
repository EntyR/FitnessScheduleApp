package health.fit.bodyz.app

import android.app.Application
import androidx.room.Room
import health.fit.bodyz.app.data.db.instatnce.WorkoutDb
import health.fit.bodyz.app.utils.FirebaseUtils

class FitnessApplication: Application() {

    private lateinit var db: WorkoutDb
    override fun onCreate() {
        super.onCreate()
        db= Room.databaseBuilder(this, WorkoutDb::class.java,
            health.fit.bodyz.app.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    public fun getDbInstance() = db.hrDao()
}