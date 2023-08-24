package com.example.myapplication

import android.app.Application
import androidx.room.Room
import com.example.myapplication.data.db.instatnce.WorkoutDb
import com.example.myapplication.utils.FirebaseUtils

class FitnessApplication: Application() {

    private lateinit var db: WorkoutDb
    override fun onCreate() {
        super.onCreate()
        FirebaseUtils.setLatencyForUpdate(3000)
        db= Room.databaseBuilder(this, WorkoutDb::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    public fun getDbInstance() = db.hrDao()
}