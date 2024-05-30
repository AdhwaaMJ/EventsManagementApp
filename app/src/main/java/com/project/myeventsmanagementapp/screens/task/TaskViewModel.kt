package com.project.myeventsmanagementapp.screens.task

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.myeventsmanagementapp.Repository.TaskRepository
import com.project.myeventsmanagementapp.data.entity.TagWithTaskLists
import com.project.myeventsmanagementapp.data.entity.Tags
import com.project.myeventsmanagementapp.data.entity.Task
import com.project.myeventsmanagementapp.data.entity.TaskTagCrossRef
import com.project.myeventsmanagementapp.data.entity.TaskType
import com.project.myeventsmanagementapp.data.entity.TaskWithTags
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel()

{

    val task = mutableStateOf<List<Task>>(emptyList())
    val tags = taskRepository.getAllTags()

    val cancelledTasks = taskRepository.getTagWithTasksList(TaskType.Cancelled.type)
    val pendingTasks = taskRepository.getTagWithTasksList(TaskType.Pending.type)
    val completedTasks = taskRepository.getTagWithTasksList(TaskType.Completed.type)
    val onGoingTasks = taskRepository.getTagWithTasksList(TaskType.OnGoing.type)

    val tagWithTasks = mutableStateOf<List<TagWithTaskLists>>(emptyList())
    val taskWithTags =  mutableStateOf<List<TaskWithTags>> (emptyList())

    val title: MutableState<String> = mutableStateOf("")
    val description: MutableState<String> = mutableStateOf("")
    val taskDate: MutableState<String> = mutableStateOf("")
    val startTime: MutableState<String> = mutableStateOf("")
    val endTime: MutableState<String> = mutableStateOf("")
    private val taskType: MutableState<String> = mutableStateOf("")
    private val category: MutableState<String> = mutableStateOf("")

    val tagName: MutableState<String> = mutableStateOf("")
    val tagColor: MutableState<String> = mutableStateOf("")
    val tagIcon: MutableState<String> = mutableStateOf("")
    val isSelected : MutableState<Boolean> = mutableStateOf(false)

    val selectedTags = mutableStateOf<List<Tags>>(emptyList())

    var allTags: MutableStateFlow<List<Tags>> = MutableStateFlow(emptyList())

    init {
        viewModelScope.launch {
            val tagsList = TaskType.entries.map {
                Tags(it.type, it.color, it.icon,it.isSelected == true)
            }
            taskRepository.insertTagList(tagsList)
            taskRepository.getAllTags().collect {
                allTags.value = it
            }
        }
        getTagWithTaskLists()

    }

    fun addTask() {
            val task = Task(
                title = title.value,
                description = description.value,
                date = taskDate.value,
                timeTo = startTime.value,
                timeFrom = endTime.value,
                taskType = taskType.value,
                tagName = category.value
            )
           viewModelScope.launch {
            insertTaskWithTags(
                task,
                selectedTags.value
            )
        }
    }

    fun addTag() {
        viewModelScope.launch {
            taskRepository.insertTag(
                Tags(
                    tagName.value,
                    tagColor.value,
                    tagIcon.value,
                    isSelected.value
                    )
            )
        }
    }


 //delete function
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }
    }

     //search
    fun searchInTasksAndTags(query: String){
        viewModelScope.launch {
            tagWithTasks.value = taskRepository.searchCombined(query).tagResults
            taskWithTags.value = taskRepository.searchCombined(query).taskResults
        }
    }

    private suspend fun insertTaskWithTags(task: Task, tags: List<Tags>) {
        val taskId = taskRepository.insertTask(task)
        val taskTagCrossRer =
            tags.map { TaskTagCrossRef(taskId, it.name) }
        taskRepository.insertTaskTagCrossRefs(taskTagCrossRer)
    }



    fun sortTasksByDate(date: String) {
        viewModelScope.launch {
            taskRepository.sortTasksByDate(date).collect {
                taskWithTags.value = it
            }
        }
    }

    private fun getTagWithTaskLists() {
        viewModelScope.launch {
            taskRepository.getTagWithTaskLists().collect{
                tagWithTasks.value =it
            }
        }
    }
}