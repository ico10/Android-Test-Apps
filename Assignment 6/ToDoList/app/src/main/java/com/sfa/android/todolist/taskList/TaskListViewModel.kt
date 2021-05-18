package com.sfa.android.todolist.taskList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sfa.android.todolist.database.Task
import com.sfa.android.todolist.database.TaskDatabaseDao
import kotlinx.coroutines.Job

class TaskListViewModel(
    database: TaskDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private val _navigateToTaskDetails = MutableLiveData<Long>()

    val navigateToTaskDetails: LiveData<Long>
        get() = _navigateToTaskDetails

    fun addTaskButtonClicked(tag: Long) {
        _navigateToTaskDetails.value = tag
    }

    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    val tasks = database.getAllTasks()

    fun getTasksList(): List<Task>? {
        return tasks.value
    }
}
