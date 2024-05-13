package com.project.myeventsmanagementapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.google.android.gms.tasks.Tasks

@Entity("tags_table")
data class Tags (
    @PrimaryKey
    @ColumnInfo(name = "tag_name")
    val name: String,
    @ColumnInfo(name = "tag_color")
    val color: String,
    @ColumnInfo(name = "tag_border")
    val borderColor: String

)

data class TaskWithTagsList(
    @Embedded val tag: Tags,
    @Relation(
        parentColumn =  "tag_name",
        entityColumn = "task_tag_name"
    )
    var tasks: List<Tasks>

)