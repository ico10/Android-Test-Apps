package com.sfa.android.todolist.taskDetails

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sfa.android.todolist.database.TaskDatabaseDao

class TaskDetailsViewModelFactory(
    private val taskKey: Long,
    private val dataSource: TaskDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskDetailsViewModel::class.java)) {
            return TaskDetailsViewModel(taskKey, dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
