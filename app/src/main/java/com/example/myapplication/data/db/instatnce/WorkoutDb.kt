package com.example.myapplication.data.db.instatnce

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.data.db.dao.WorkoutsDao
import com.example.myapplication.data.db.entity.WorkoutEntity

@Database(entities = [WorkoutEntity::class], version = 1)
abstract class WorkoutDb : RoomDatabase() {
    abstract fun hrDao(): WorkoutsDao

}