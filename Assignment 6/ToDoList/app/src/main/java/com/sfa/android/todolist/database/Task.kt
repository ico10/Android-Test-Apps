package com.sfa.android.todolist.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks_table")
data class Task(

        @PrimaryKey(autoGenerate = true)
        var taskId: Long = 0L,

        @ColumnInfo(name = "task_description")
        var taskDescription: String = "",

        @ColumnInfo(name = "task_state")
        var taskState: String = ""
)
