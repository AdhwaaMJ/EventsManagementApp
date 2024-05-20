package com.project.myeventsmanagementapp.Repository

import com.project.myeventsmanagementapp.data.dao.TaskDao
import com.project.myeventsmanagementapp.data.entity.TagWithTaskLists
import com.project.myeventsmanagementapp.data.entity.Tags
import com.project.myeventsmanagementapp.data.entity.Task
import com.project.myeventsmanagementapp.data.entity.TaskTagCrossRef
import com.project.myeventsmanagementapp.data.entity.TaskWithTags
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val taskDao: TaskDao
) {
    suspend fun insertTask(task: Task) : Long {
        return taskDao.addTask(task)
    }

    suspend fun insertTaskTagCrossRefs(taskTagCrossRefs: List<TaskTagCrossRef>){
        taskDao.insertTaskTagCrossRefs(taskTagCrossRefs)
    }

    suspend fun deleteTask(task: Task){
        taskDao.deleteTask(task)
    }

     fun getAllTasks(): Flow<List<Task>>{
        return taskDao.getAllTasks()
    }

    suspend fun insertTag(tag: Tags){
        taskDao.upsertTag(tag)
    }

    suspend fun deleteTag(tag: Tags){
        taskDao.deleteTag(tag)
    }

    fun getTagWithTasksList(tagName: String): Flow<List<TagWithTaskLists>>{
        return taskDao.getTagsWithTask(tagName)
    }

    fun getAllTags(): Flow<List<Tags>>{
        return taskDao.getAllTags()
    }

    suspend fun insertTagList(tagList: List<Tags>){
        return taskDao.upsertTagList(tagList)
    }

    fun sortTasksByDate(date:String) : Flow<List<TaskWithTags>> {
        return taskDao.sortByCreationDate(date)
    }

    fun getTagsWithTasks(type: String): Flow<List<TaskWithTags>>{
        return taskDao.getTaskWithTags()
    }

    fun getTagWithTaskLists() = taskDao.getTagWithTaskLists()

}