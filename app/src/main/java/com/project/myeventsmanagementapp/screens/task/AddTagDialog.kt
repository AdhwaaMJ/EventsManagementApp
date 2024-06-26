package com.project.myeventsmanagementapp.screens.task

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.project.myeventsmanagementapp.component.CustomTextField
import com.project.myeventsmanagementapp.getAllColors
import com.project.myeventsmanagementapp.getAllSystemIcons
import com.project.myeventsmanagementapp.getIconName
import com.project.myeventsmanagementapp.ui.theme.PrimaryColor

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddTagDialog(navController: NavHostController, taskViewModel: TaskViewModel) {
    Box(
        Modifier
            .padding(16.dp)
            .background(Color.Transparent)
    ) {
//        val value = remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(vertical = 16.dp, horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "New Tag",
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            CustomTextField(
                label = "Tag Name",
                textColor = PrimaryColor,
                value = taskViewModel.tagName
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                getAllColors().forEach {
                    Canvas(modifier = Modifier
                        .size(50.dp)
                        .clickable {
                            taskViewModel.tagColor.value = it
                                .toArgb()
                                .toString()
                        }) {
                        drawCircle(it)
                    }
                }
            }
            Spacer(modifier = Modifier.size(22.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                getAllSystemIcons().forEach {
                    Icon(
                        it,
                        contentDescription = it.name,
                        modifier = Modifier
                            .size(50.dp)
                            .clickable {
                                taskViewModel.tagIcon.value = getIconName(it)
                            })
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        navController.popBackStack()
                    }, shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = "Cancel")

                }

                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        taskViewModel.addTag()
                        navController.popBackStack()
                    }, shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = "Save", color = Color.White)
                }
            }
        }
    }
}