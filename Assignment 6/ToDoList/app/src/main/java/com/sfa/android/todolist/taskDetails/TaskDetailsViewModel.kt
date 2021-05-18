package com.sfa.android.todolist.taskDetails

import android.app.Application
import androidx.lifecycle.*
import com.sfa.android.todolist.R
import com.sfa.android.todolist.database.Task
import com.sfa.android.todolist.database.TaskDatabaseDao
import kotlinx.coroutines.*

const val DEFAULT_KEY_VALUE = 0L

class TaskDetailsViewModel(
    private val taskKey: Long,
    val database: TaskDatabaseDao,
    val application: Application
) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val mutableTask = MutableLiveData<Task>()

    init {
        initializeTask()
    }

    private fun initializeTask() {
        uiScope.launch {
            mutableTask.value = getCurrentTaskIfPresent()
        }
    }

    private suspend fun getCurrentTaskIfPresent(): Task {
        if (taskKey != DEFAULT_KEY_VALUE) {
            return withContext(Dispatchers.IO) {
                val currentTask = database.get(taskKey)
                currentTask
            }
        }
        return Task()
    }

    private val _eventCompleteButtonPressed = MutableLiveData<Boolean>().apply { value = false }

    val eventCompleteButtonPressed: LiveData<Boolean>
        get() = _eventCompleteButtonPressed

    fun pressCompleteButton() {
        _eventCompleteButtonPressed.value = true
    }

    fun completeButtonDone() {
        _eventCompleteButtonPressed.value = false
    }

    private val _eventSaveButtonPressed = MutableLiveData<Boolean>().apply { value = false }

    val eventSaveButtonPressed: LiveData<Boolean>
        get() = _eventSaveButtonPressed

    fun pressSaveButton() {
        _eventSaveButtonPressed.value = true
    }

    fun saveButtonDone() {
        _eventSaveButtonPressed.value = false
    }

    private val _eventDeleteButtonPressed = MutableLiveData<Boolean>().apply { value = false }

    val eventDeleteButtonPressed: LiveData<Boolean>
        get() = _eventDeleteButtonPressed

    fun pressDeleteButton() {
        _eventDeleteButtonPressed.value = true
    }

    fun deleteButtonDone() {
        _eventDeleteButtonPressed.value = false
    }

    fun onSaveTaskPressed(text: String) {
        uiScope.launch {
            val newTask = Task()
            newTask.taskState = application.resources.getString(R.string.task_not_complete_text)
            newTask.taskDescription = text
            insertTask(newTask)
        }
    }

    fun onSaveEditTaskPressed(text: String) {
        uiScope.launch {
            val updatedTask = mutableTask.value ?: Task()
            updatedTask.taskDescription = text

            updateTask(updatedTask)
        }
    }

    private suspend fun insertTask(task: Task) {
        withContext(Dispatchers.IO) {
            database.insertTask(task)
        }
    }

    fun onCompletePressed() {
        uiScope.launch {
            val updatedTask = mutableTask.value ?: Task()
            updatedTask.taskState = application.resources.getString(R.string.task_complete_text)

            updateTask(updatedTask)
        }
    }

    private suspend fun updateTask(updatedTask: Task) {
        withContext(Dispatchers.IO) {
            database.updateTask(updatedTask)
        }
    }

    fun onDeleteTaskPressed() {
        uiScope.launch {
            val taskToDelete = mutableTask.value ?: Task()

            deleteTask(taskToDelete)
        }
    }

    private suspend fun deleteTask(taskToDelete: Task) {
        withContext(Dispatchers.IO) {
            database.deleteTask(taskToDelete)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun getCurrentTaskDescription(): String {
        return mutableTask.value?.taskDescription.toString()
    }
}
