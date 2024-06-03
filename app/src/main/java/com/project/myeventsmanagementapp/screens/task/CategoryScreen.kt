package com.project.myeventsmanagementapp.screens.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseUser
import com.project.myeventsmanagementapp.component.TagCard
import com.project.myeventsmanagementapp.component.UserImageWithEmail
import com.project.myeventsmanagementapp.iconByName
import com.project.myeventsmanagementapp.navigation.Screens
import com.project.myeventsmanagementapp.ui.theme.PrimaryColor

@Composable
fun CategoryScreen(user: FirebaseUser?, viewModel: TaskViewModel, navController: NavHostController, logout: () -> Unit, )
{
    val tagsWithTasksList = viewModel.tagWithTasks

    Column {
        UserImageWithEmail(user = user, navController, logout)
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 100.dp),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            columns = GridCells.Fixed(2)
        ) {
            items(tagsWithTasksList.value) {
                TagCard(
                    Color(it.tags.color.toIntOrNull() ?: PrimaryColor.toArgb()),
                    iconByName(it.tags.iconName),
                    it.tags.name,
                    "${it.task.size} Task"
                ) {
                    navController.navigate("${Screens.MainApp.TaskByCategory.route}/${it.tags.name}")
                }
            }
        }

    }
}