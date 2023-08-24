package com.example.myapplication.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.data.db.entity.WorkoutEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutsDao {


    @Query("SELECT * from WorkoutEntity")
    fun getHRRecords(): Flow<List<WorkoutEntity>>

    @Query("DELETE from WorkoutEntity")
    suspend fun deleteAllRecords()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewRecord(record: WorkoutEntity)
}