package com.project.myeventsmanagementapp.data.dao

import androidx.room.Insert
import androidx.room.Query
import com.project.myeventsmanagementapp.data.entity.Task

interface TaskDao {
    @Insert
    fun insertAll(vararg task: Task)

    @Query("SELECT * FROM Task")
    fun getAll(): List<Task>
}