package health.fit.bodyz.app.data.db.instatnce

import androidx.room.Database
import androidx.room.RoomDatabase
import health.fit.bodyz.app.data.db.dao.WorkoutsDao
import health.fit.bodyz.app.data.db.entity.WorkoutEntity

@Database(entities = [WorkoutEntity::class], version = 1)
abstract class WorkoutDb : RoomDatabase() {
    abstract fun hrDao(): WorkoutsDao

}