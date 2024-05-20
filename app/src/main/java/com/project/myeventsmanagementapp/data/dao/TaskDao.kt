package com.project.myeventsmanagementapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.project.myeventsmanagementapp.data.entity.TagWithTaskLists
import com.project.myeventsmanagementapp.data.entity.Tags
import com.project.myeventsmanagementapp.data.entity.Task
import com.project.myeventsmanagementapp.data.entity.TaskTagCrossRef
import com.project.myeventsmanagementapp.data.entity.TaskWithTags
import kotlinx.coroutines.flow.Flow
@Dao
interface TaskDao {
    @Transaction
    @Upsert
    suspend fun addTask(task: Task) : Long

    @Transaction
    @Upsert
    suspend fun insertTaskWithTags(task: Task, tags: List<Tags>)

    @Transaction
    @Upsert
    suspend fun insertTaskTagCrossRefs(taskTagCrossRefs: List<TaskTagCrossRef>)

    @Delete
    suspend fun deleteTask(task: Task)


    @Query("SELECT * From task_table")
    fun getAllTasks(): Flow<List<Task>>

    @Upsert
    suspend fun upsertTag(tag: Tags)
    @Delete
    suspend fun deleteTag(tag:Tags)

    @Query("SELECT * From tags_table")
    fun getAllTags(): Flow<List<Tags>>


    @Query("Select * From tags_table where tag_name = :tagName")
    fun getTagsWithTask(tagName: String): Flow<List<TagWithTaskLists>>

    @Query("Select *From task_table WHERE date LIKE :date")
    fun sortByCreationDate(date: String): Flow<List<TaskWithTags>>

    @Upsert
    suspend fun upsertTagList(tag: List<Tags>)

    @Transaction
    @Query("SELECT * FROM task_table")
    fun getTaskWithTags(): Flow<List<TaskWithTags>>

    @Transaction
    @Query("SELECT * FROM tags_table")
    fun getTagWithTaskLists(): Flow<List<TagWithTaskLists>>

}