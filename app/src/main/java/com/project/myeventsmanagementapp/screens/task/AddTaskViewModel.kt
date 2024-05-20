package com.project.myeventsmanagementapp.screens.task

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.myeventsmanagementapp.Repository.TaskRepository
import com.project.myeventsmanagementapp.data.entity.Tags
import com.project.myeventsmanagementapp.data.entity.Task
import com.project.myeventsmanagementapp.data.entity.TaskTagCrossRef
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddTaskViewModel @Inject constructor( private val taskRepository: TaskRepository) : ViewModel() {

    val title: MutableState<String> = mutableStateOf("")
    val description: MutableState<String> = mutableStateOf("")
    val taskDate: MutableState<String> = mutableStateOf("")
    val startTime: MutableState<String> = mutableStateOf("")
    val endTime: MutableState<String> = mutableStateOf("")
    private val taskType: MutableState<String> = mutableStateOf("")
    val category: MutableState<String> = mutableStateOf("")

    val tagName: MutableState<String> = mutableStateOf("")
    val tagColor: MutableState<String> = mutableStateOf("")
    val tagIcon: MutableState<String> = mutableStateOf("")

    val allTags = taskRepository.getAllTags()

    val selectedTags = mutableStateOf<Set<Tags>>(emptySet())

    fun addTask() {
        viewModelScope.launch {
            val task = Task(
                title = title.value,
                description = description.value,
                date = taskDate.value,
                timeTo = startTime.value,
                timeFrom = endTime.value,
                taskType = taskType.value,
                tagName = category.value
            )
            insertTaskWithTags(
                task,
                selectedTags.value.toList()
            )
        }
    }

    fun addTag() {
        viewModelScope.launch {
            taskRepository.insertTag(
                Tags(
                    tagName.value,
                    tagColor.value,
                    tagIcon.value
                )
            )
        }
    }


    private suspend fun insertTaskWithTags(task: Task, tags: List<Tags>) {
        val taskId = taskRepository.insertTask(task)
        val taskTagCrossRer =
            tags.map { TaskTagCrossRef(taskId, it.name) }
        taskRepository.insertTaskTagCrossRefs(taskTagCrossRer)
    }
}