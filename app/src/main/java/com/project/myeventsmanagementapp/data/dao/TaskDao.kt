package com.project.myeventsmanagementapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.project.myeventsmanagementapp.data.entity.SearchResults
import com.project.myeventsmanagementapp.data.entity.TagWithTaskLists
import com.project.myeventsmanagementapp.data.entity.Tags
import com.project.myeventsmanagementapp.data.entity.Task
import com.project.myeventsmanagementapp.data.entity.TaskTagCrossRef
import com.project.myeventsmanagementapp.data.entity.TaskWithTags
import kotlinx.coroutines.flow.Flow
@Dao
interface TaskDao {

    @Upsert
    suspend fun addTask(task: Task) : Long

    @Transaction
    @Upsert
    suspend fun insertTaskTagCrossRefs(taskTagCrossRefs: List<TaskTagCrossRef>)

    @Delete
    suspend fun deleteTask(task: Task)

    @Upsert
    suspend fun upsertTag(tag: Tags)

    @Delete
    suspend fun deleteTag(tag:Tags)

    @Query("SELECT * From tags_table")
    fun getAllTags(): Flow<List<Tags>>

    @Transaction
    @Query(" Select * From tags_table where tag_name = :tagName Limit 1")
    fun getTagsWithTask(tagName: String): Flow<TagWithTaskLists>

    @Query("Select *From task_table WHERE date LIKE :date")
    fun sortByCreationDate(date: String): Flow<List<TaskWithTags>>

    @Upsert
    suspend fun upsertTagList(tag: List<Tags>)

    @Transaction
    @Query("SELECT * FROM tags_table")
    fun getTagWithTaskLists(): Flow<List<TagWithTaskLists>>

    //Search
    @Transaction
    @Query("SELECT * FROM task_table WHERE task_title LIKE '%' || :searchQuery || '%' OR task_description LIKE '%' || :searchQuery || '%'")
    fun searchTaskWithTags(searchQuery: String): List<TaskWithTags>

    @Transaction
    @Query("SELECT * FROM tags_table WHERE tag_name LIKE '%' || :searchQuery || '%' ")
    fun searchTagsWithTasks(searchQuery: String): List<TagWithTaskLists>

    @Transaction
    suspend fun searchCombined(searchQuery: String): SearchResults {
        val taskResults = searchTaskWithTags(searchQuery)
        val tagResults = searchTagsWithTasks(searchQuery)
        return SearchResults(taskResults , tagResults)
    }

    @Transaction
    @Query("SELECT * FROM task_table WHERE task_Id = :taskId Limit 1")
    suspend fun getTaskWithTagsById(taskId: Long): TaskWithTags

    @Transaction
    @Query("SELECT * FROM task_table")
    fun getAllTaskWithTags(): Flow<List<TaskWithTags>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTaskTagCrossRef(crossRef: TaskTagCrossRef): Long

    @Query("DELETE FROM tasktagcrossref WHERE task_Id = :taskId")
    suspend fun deleteTaskTagCrossRefs(taskId: Long)

   //update
   @Transaction
   suspend fun updateTaskWithTags(task: Task, tags: List<Tags>) {
       addTask(task)

       deleteTaskTagCrossRefs(task.taskId!!)

       for (tag in tags) {
           upsertTag(tag)
           insertTaskTagCrossRef(TaskTagCrossRef(task.taskId!!, tag.name))
       }
   }

    @Transaction
    @Query("SELECT * FROM task_table")
    suspend fun getAllTasksWithTags(): List<TaskWithTags>

    @Query("""
        SELECT strftime('%Y-%m-%d', date) AS day, * FROM task_table
        WHERE strftime('%Y-%W', date) = strftime('%Y-%W', 'now')
        ORDER BY day
    """)
    fun getTasksWithTagsByDayOfCurrentWeek(): Flow<List<TaskWithTags>>

}