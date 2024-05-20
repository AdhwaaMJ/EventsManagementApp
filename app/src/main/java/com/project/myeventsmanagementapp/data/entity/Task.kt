package com.project.myeventsmanagementapp.data.entity

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Done
import androidx.compose.ui.graphics.toArgb
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.project.myeventsmanagementapp.getIconName
import com.project.myeventsmanagementapp.ui.theme.LightBlue
import com.project.myeventsmanagementapp.ui.theme.LightGreen
import com.project.myeventsmanagementapp.ui.theme.LightPurple
import com.project.myeventsmanagementapp.ui.theme.LightRed

@Entity(tableName = "task_table")
data class Task (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_Id" )
    var taskId: Long? = null,
    @ColumnInfo(name = "task_title" )
    val title: String,
    @ColumnInfo(name = "task_description" )
    val description: String,
    @ColumnInfo(name = "date")
    val date:String,
    @ColumnInfo(name = "time_from")
    val timeFrom: String,
    @ColumnInfo(name = "time_to")
    val timeTo: String,
    @ColumnInfo(name = "task_type")
    val taskType: String,
    @ColumnInfo(name ="task_tag_name")
    val tagName: String =""

)

enum class TaskType( val type :String, val color:String,val icon:String){
    Pending("Pending", LightPurple.toArgb().toString(), getIconName(Icons.Outlined.DateRange)),
    OnGoing("On Going", LightGreen.toArgb().toString(), getIconName(Icons.Outlined.Build)),
    Cancelled("Cancelled", LightRed.toArgb().toString(), getIconName(Icons.Outlined.Delete)),
    Completed("Completed", LightBlue.toArgb().toString(), getIconName(Icons.Outlined.Done))
}