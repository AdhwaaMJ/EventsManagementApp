package com.project.myeventsmanagementapp.screens.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.project.myeventsmanagementapp.component.TaskCard
import com.project.myeventsmanagementapp.data.entity.TagWithTaskLists
import com.project.myeventsmanagementapp.component.TaskHeaderView


@Composable
fun TasksByCategory(tagWithTaskLists: TagWithTaskLists?, navController: NavHostController) {
    Column(modifier = Modifier.padding(16.dp)) {
        TaskHeaderView(tagWithTaskLists?.tags?.name.orEmpty()) {
            navController.popBackStack()
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(tagWithTaskLists?.task.orEmpty()) {

                TaskCard(
                    taskTitle = it.title,
                    it.timeFrom,
                    timeTo = it.timeTo,
                    tag = listOf(tagWithTaskLists?.tags)
                )
            }
        }
    }
}