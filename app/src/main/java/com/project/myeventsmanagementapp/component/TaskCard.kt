package com.project.myeventsmanagementapp.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.myeventsmanagementapp.data.entity.Tags
import com.project.myeventsmanagementapp.ui.theme.PrimaryColor



@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TaskCard(taskTitle: String, timeFrom: String?, timeTo: String?, tag: List<Tags?>?, onDelete: () -> Unit, onClick: () -> Unit)
{
    val dividerHeight = remember {
        mutableStateOf(50.dp)
    }
    val showMenu = rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(
                tag?.firstOrNull()?.color?.toIntOrNull() ?: PrimaryColor.toArgb()
            ).copy(0.1f)
        ),
        onClick = {
            onClick.invoke()
        }
    ) {
        Column {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(15.dp), Arrangement.SpaceBetween
            ) {

                Row {
                    Box(
                        modifier = Modifier
                            .height(dividerHeight.value)
                            .width(3.dp)
                            .background(
                                Color(tag?.firstOrNull()?.color?.toIntOrNull() ?: PrimaryColor.toArgb()),
                                RoundedCornerShape(16.dp)
                            )
                            .padding(0.dp, 40.dp)
                    )
                    Column(modifier = Modifier
                        .padding(4.dp)
                        .onGloballyPositioned {
                            dividerHeight.value = it.size.height.dp / 2
                        }) {
                        Text(
                            text = taskTitle,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(text = "$timeFrom - $timeTo", fontSize = 15.sp, color = Color.Gray)
                    }
                }
                Box {
                    Icon(
                        Icons.Default.MoreVert,
                        contentDescription = "",
                        tint = Color.Gray,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                showMenu.value = true
                            }
                    )
                    ActionsMenu(showMenu, onEdit = {
                        onClick.invoke()
                        showMenu.value=false

                    }, onDelete = {
                        onDelete.invoke()
                        showMenu.value=false
                    })
                }

            }

            FlowRow(
                Modifier
                    .fillMaxWidth()
                    .padding(25.dp, 10.dp), Arrangement.spacedBy(10.dp)
            ) {
                tag?.forEach { tag ->
                    Box(
                        Modifier
                            .background(
                                Color(
                                    tag?.color?.toIntOrNull() ?: PrimaryColor.toArgb()
                                ).copy(0.4f),
                                RoundedCornerShape(16.dp)
                            )
                    ) {
                        Text(
                            text = tag?.name.orEmpty(),
                            modifier = Modifier.padding(5.dp),
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ActionsMenu(showMenu: MutableState<Boolean>, onDelete: () -> Unit, onEdit: () -> Unit) {
    DropdownMenu(
        modifier = Modifier.padding(12.dp),
        expanded = showMenu.value,
        onDismissRequest = {
            showMenu.value = false
        }) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                onEdit.invoke()
            }) {
            Icon(Icons.Outlined.Edit, contentDescription = "")
            Text(text = "Edit", modifier = Modifier.padding(12.dp))
        }
        HorizontalDivider()
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                onDelete.invoke()
            }) {
            Icon(Icons.Outlined.Delete, contentDescription = "")
            Text(text = "Delete", modifier = Modifier.padding(12.dp))

        }

    }
}


//Box {
//    DropdownMenu(
//        expanded = showMenu,
//        onDismissRequest = { showMenu = false }
//    ) {
//        DropdownMenuItem(
//            text = { Text("Disable") },
//            onClick = { showMenu = false },
//            leadingIcon = { Icon(Icons.Default.Close, contentDescription = null)},
//        )
//        DropdownMenuItem(
//            text = { Text("Edit") },
//            onClick = { showMenu = false},
//            leadingIcon = { Icon(Icons.Default.Edit, contentDescription = null) },
//        )
//
//        DropdownMenuItem(
//            text = { Text("Delete") },
//            onClick = {
//                onDelete.invoke()
//                showMenu = false },
//            leadingIcon = { Icon(Icons.Default.Delete, contentDescription = null) },
//        )
//    }
//
//    Icon(
//        Icons.Default.MoreVert,
//        contentDescription = null,
//        tint = Color.Gray,
//        modifier = Modifier
//            .size(24.dp)
//            .clickable { showMenu = true }
//    )
//}