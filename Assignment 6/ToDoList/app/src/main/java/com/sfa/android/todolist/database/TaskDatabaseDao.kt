package com.sfa.android.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDatabaseDao {

    @Insert
    fun insertTask(task: Task)

    @Update
    fun updateTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Query("SELECT * FROM tasks_table ORDER BY taskId DESC")
    fun getAllTasks(): LiveData<List<Task>>

    @Query("SELECT * from tasks_table WHERE taskId = :key")
    fun get(key: Long): Task
}
