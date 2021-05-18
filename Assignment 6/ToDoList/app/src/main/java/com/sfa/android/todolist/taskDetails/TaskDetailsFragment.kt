package com.sfa.android.todolist.taskDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.sfa.android.todolist.R
import com.sfa.android.todolist.database.TaskDatabase
import com.sfa.android.todolist.databinding.FragmentTaskDetailsBinding

class TaskDetailsFragment : Fragment() {

    private lateinit var binding: FragmentTaskDetailsBinding
    private lateinit var viewModel: TaskDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_task_details, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = TaskDatabase.getInstance(application).taskDatabaseDao

        val taskDetailsFragmentArgs by navArgs<TaskDetailsFragmentArgs>()

        val viewModelFactory =
            TaskDetailsViewModelFactory(taskDetailsFragmentArgs.taskKey, dataSource, application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(TaskDetailsViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButtonListeners()
        setObservers()
        setVisibilityObservers()
    }

    private fun setButtonListeners() {
        binding.completeButton.setOnClickListener {
            viewModel.pressCompleteButton()
        }

        binding.saveButton.setOnClickListener {
            viewModel.pressSaveButton()
        }

        binding.deleteButton.setOnClickListener {
            viewModel.pressDeleteButton()
        }
    }

    private fun setObservers() {
        viewModel.eventCompleteButtonPressed.observe(viewLifecycleOwner, {
            if (it) {
                viewModel.onCompletePressed()
                binding.root.findNavController()
                    .navigate(R.id.action_taskDetailsFragment_to_taskListFragment)
                viewModel.completeButtonDone()
            }
        })

        viewModel.eventSaveButtonPressed.observe(viewLifecycleOwner, {
            if (it) {
                if (binding.taskText.text.toString().isEmpty()) {
                    Toast.makeText(this.context, "Task description is empty!", Toast.LENGTH_LONG)
                        .show()
                } else {
                    if (viewModel.mutableTask.value?.taskId == DEFAULT_KEY_VALUE) {
                        viewModel.onSaveTaskPressed(binding.taskText.text.toString())
                    } else {
                        viewModel.onSaveEditTaskPressed(binding.taskText.text.toString())
                    }
                    binding.root.findNavController()
                        .navigate(R.id.action_taskDetailsFragment_to_taskListFragment)
                    viewModel.saveButtonDone()
                }
            }
        })

        viewModel.eventDeleteButtonPressed.observe(viewLifecycleOwner, {
            if (it) {
                viewModel.onDeleteTaskPressed()
                binding.root.findNavController()
                    .navigate(R.id.action_taskDetailsFragment_to_taskListFragment)
                viewModel.deleteButtonDone()
            }
        })
    }

    private fun setVisibilityObservers() {
        viewModel.mutableTask.observe(viewLifecycleOwner, {
            when {
                it.taskId == DEFAULT_KEY_VALUE -> {
                    binding.completeButton.isEnabled = false
                    binding.deleteButton.isEnabled = false
                }
                it.taskState == resources.getString(R.string.complete_button_text) -> {
                    binding.taskText.hint = viewModel.getCurrentTaskDescription()
                    binding.completeButton.isEnabled = false
                }
                else -> {
                    binding.taskText.hint = viewModel.getCurrentTaskDescription()
                }
            }
        })
    }

    companion object{
        const val DEFAULT_KEY_VALUE = 0L
    }
}
