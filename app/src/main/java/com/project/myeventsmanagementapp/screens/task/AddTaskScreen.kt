package com.project.myeventsmanagementapp.screens.task

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.project.myeventsmanagementapp.component.AddTagsListView
import com.project.myeventsmanagementapp.component.CustomTextField
import com.project.myeventsmanagementapp.component.TaskHeaderView
import com.project.myeventsmanagementapp.component.TimePickerDialog
import com.project.myeventsmanagementapp.navigation.Screens
import com.project.myeventsmanagementapp.ui.theme.PrimaryColor

@Composable
fun AddTaskScreen(navController: NavHostController, viewModel: TaskViewModel) {

    LaunchedEffect(Unit) {
        viewModel.allTags
    }
    val allTags = viewModel.allTags.collectAsState()

    val showStartTimeTimeDialog = remember {
        mutableStateOf(false)
    }

    val showEndTimeTimeDialog = remember {
        mutableStateOf(false)
    }

    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
        item {
            TaskHeaderView("Add Task") {
                navController.popBackStack()
            }
        }
        item {
            CustomTextField(Modifier, "Title", Color.Gray, viewModel.title)
            CustomTextField(
                Modifier,
                "Date",
                Color.Gray,
                viewModel.taskDate,
                isReadOnly = true,
                trailingIcon = {
                    Icon(imageVector = Icons.Outlined.DateRange, "", modifier =
                    Modifier.clickable {
                        navController.navigate(Screens.MainApp.DateDialog.route)
                    })
                })
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CustomTextField(
                    Modifier
                        .weight(1f)
                        .clickable {
                            showStartTimeTimeDialog.value = true
                        },
                    "Time From", Color.Gray, viewModel.startTime, isReadOnly = true,
                )
                CustomTextField(
                    Modifier
                        .weight(1f)
                        .clickable {
                            showEndTimeTimeDialog.value = true
                        },
                    "Time To", Color.Gray, viewModel.endTime, isReadOnly = true,
                )
            }
            CustomTextField(Modifier, "Description", Color.Gray, viewModel.description)

        }
        item {
            AddTagsListView(allTags, navController) {
                viewModel.selectedTags.value = it
                viewModel.selectedTags.value
            }
        }

        item {
            ButtonAddTask(viewModel)
        }
    }
    if (showStartTimeTimeDialog.value) {
        TimePickerDialog(
            onBackPress = {
                showStartTimeTimeDialog.value = false
            },
            onTimeSelected = { hour, minute ->
                viewModel.startTime.value = "$hour:$minute"
            }
        )
    }
    if (showEndTimeTimeDialog.value) {
        TimePickerDialog(
            onBackPress = {
                showEndTimeTimeDialog.value = false
            },
            onTimeSelected = { hour, minute ->
                viewModel.endTime.value = "$hour:$minute"
            }
        )
    }
}

@Composable
fun ButtonAddTask(addTask: TaskViewModel) {
    Button(
        onClick = {
            addTask.addTask()
        },
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(22.dp)
            .padding(bottom = 100.dp)
            .semantics {
                testTag = "Add Task Button"
            },
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryColor
        )
    ) {
        Text(
            modifier = Modifier.padding(vertical = 8.dp), text = "Create",
            fontSize = 16.sp,
            color = Color.White
        )
    }
}