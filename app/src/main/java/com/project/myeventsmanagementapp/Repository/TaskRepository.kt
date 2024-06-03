package com.project.myeventsmanagementapp.Repository

import com.project.myeventsmanagementapp.data.dao.TaskDao
import com.project.myeventsmanagementapp.data.entity.SearchResults
import com.project.myeventsmanagementapp.data.entity.TagWithTaskLists
import com.project.myeventsmanagementapp.data.entity.Tags
import com.project.myeventsmanagementapp.data.entity.Task
import com.project.myeventsmanagementapp.data.entity.TaskTagCrossRef
import com.project.myeventsmanagementapp.data.entity.TaskWithTags
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val taskDao: TaskDao
) {
    suspend fun insertTask(task: Task): Long {
        return taskDao.addTask(task)
    }

    suspend fun insertTaskTagCrossRefs(taskTagCrossRefs: List<TaskTagCrossRef>) {
        taskDao.insertTaskTagCrossRefs(taskTagCrossRefs)
    }


    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    suspend fun insertTag(tag: Tags) {
        taskDao.upsertTag(tag)
    }

    fun getTagWithTasksList(tagName: String): Flow<TagWithTaskLists> {
        return taskDao.getTagsWithTask(tagName)
    }

     fun getAllTags() = taskDao.getAllTags()


    suspend fun insertTagList(tagList: List<Tags>) {
        return taskDao.upsertTagList(tagList)
    }

    fun sortTasksByDate(date: String): Flow<List<TaskWithTags>> {
        return taskDao.sortByCreationDate(date)
    }

    fun getTagWithTaskLists() = taskDao.getTagWithTaskLists()

    //search
    suspend fun searchCombined(searchQuery: String): SearchResults {
        return taskDao.searchCombined(searchQuery)
    }

    suspend fun getTaskWithTagsById(taskId: Long) = taskDao.getTaskWithTagsById(taskId)

    fun getAllTaskWithTags() = taskDao.getAllTaskWithTags()

    suspend fun updateTaskWithTags(task: Task, tags: List<Tags>) {
        taskDao.updateTaskWithTags(task, tags)
    }

    suspend fun getAllTasksWithTags(): List<TaskWithTags> {
        return taskDao.getAllTasksWithTags()
    }

    fun getTasksWithTagsByDayOfCurrentWeek() = taskDao.getTasksWithTagsByDayOfCurrentWeek()

}