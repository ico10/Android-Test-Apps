package com.sfa.android.todolist

import com.sfa.android.todolist.database.Task

fun formatTaskToString(task: Task): String {
    val sb = StringBuilder()
    sb.append("Task: ${task.taskDescription}\n")
    sb.append("State: ${task.taskState}")
    return sb.toString()
}
