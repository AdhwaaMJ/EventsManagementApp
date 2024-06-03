package com.project.myeventsmanagementapp.room

import androidx.test.filters.SmallTest
import com.project.myeventsmanagementapp.data.dao.TaskDao
import com.project.myeventsmanagementapp.data.database.EventsDatabase
import com.project.myeventsmanagementapp.data.entity.Tags
import com.project.myeventsmanagementapp.data.entity.Task
import com.project.myeventsmanagementapp.data.entity.TaskType
//import com.project.myeventsmanagementapp.data.entity.TaskWithTagsLists
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named


@HiltAndroidTest
@SmallTest
class TaskDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: EventsDatabase

    private lateinit var taskDao: TaskDao

    val task = Task(
        taskId = 1,
        title = "title",
        description = "description",
        date = java.time.LocalDate.now().toString(),
        taskType = TaskType.Cancelled.type,
        timeFrom = "11:11",
        timeTo = "12:12",
        tagName = "Work"

    )

    @Before
    fun setup(){
        hiltRule.inject()
        taskDao = database.taskDao()
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun insertTask() = runTest {
        taskDao.addTask(task)
        val allTasks = taskDao.getAllTasks().first()
        assert(allTasks.contains(task))
    }

    @Test
    fun deleteTask() = runTest {
        taskDao.addTask(task)
        taskDao.deleteTask(task)
        val allTasks = taskDao.getAllTasks().first()
        assert(!allTasks.contains(task))
    }

    val task2 = Task(
        taskId = 2,
        title = "title",
        description = "description",
        date = java.time.LocalDate.now().toString(),
        taskType = TaskType.Cancelled.type,
        timeFrom = "10:11",
        timeTo = "12:10",
        tagName = "Work"
    )

    @Test
    fun upsertTags() = runTest {
        val tag = Tags(
            "Personal",
            "color",
            "",
            ""

        )
        taskDao.upsertTag(tag)
        val allTags = taskDao.getAllTags().first()
        assert(allTags.contains(tag))
    }
    @Test
    fun deleteTag() = runTest {
        val tag = Tags(
            "Personal",
            "color",
            "",
            ""
        )
        taskDao.upsertTag(tag)
        taskDao.deleteTag(tag)
        val allTags = taskDao.getAllTags().first()
        assert(allTags.isEmpty())
    }

    @Test
    fun getAllTags() = runTest {
        val tag = Tags(
            "Personal",
            "color",
            "",
            ""
        )
        val tag2 = Tags(
            "Work",
            "color",
            "",
            ""
        )
        taskDao.upsertTag(tag)
        taskDao.upsertTag(tag2)
        val allTags = taskDao.getAllTags().first()
        assert(allTags == listOf(tag,tag2))
    }
    @Test
    fun getTagsWithTask() = runTest {
        val tag = Tags(
            "Personal",
            "color",
            "",
            ""
        )
        val tag2 = Tags(
            "Work",
            "color",
            "",
            ""
        )

        val task = Task(
            taskId = 1,
            title = "title",
            description = "description",
            date = java.time.LocalDate.now().toString(),
            taskType = TaskType.Cancelled.type,
            timeFrom = "11:11",
            timeTo = "12:12",
            tagName = "Work"

        )

        val task2 = Task(
            taskId = 2,
            title = "title",
            description = "description",
            date = java.time.LocalDate.now().toString(),
            taskType = TaskType.Cancelled.type,
            timeFrom = "10:11",
            timeTo = "12:10",
            tagName = "Work"
        )

        taskDao.upsertTag(tag)
        taskDao.upsertTag(tag2)
        taskDao.addTask(task)
        taskDao.addTask(task2)

        val getTagsWithTask = taskDao.getTagsWithTask("Work").first()
//        val expected = listOf(TaskWithTagsLists(tag2, listOf(task,task2)))
//        assert(getTagsWithTask == expected)

    }

}