package com.sfa.android.todolist.taskList

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.sfa.android.todolist.R
import com.sfa.android.todolist.database.Task
import com.sfa.android.todolist.database.TaskDatabase
import com.sfa.android.todolist.databinding.FragmentTaskListBinding
import com.sfa.android.todolist.formatTaskToString


class TaskListFragment : Fragment() {

    private lateinit var binding: FragmentTaskListBinding
    private lateinit var viewModel: TaskListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_task_list, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = TaskDatabase.getInstance(application).taskDatabaseDao

        val viewModelFactory = TaskListViewModelFactory(dataSource, application)
        viewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(TaskListViewModel::class.java)

        setHasOptionsMenu(true)

        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAddButtonOnClick()
        setObservers()
    }

    private fun setAddButtonOnClick() {
        binding.addTaskButton.setOnClickListener {
            viewModel.addTaskButtonClicked(DEFAULT_KEY_VALUE)
        }
    }

    private fun setObservers() {
        viewModel.navigateToTaskDetails.observe(viewLifecycleOwner, {
                binding.root.findNavController().navigate(
                    TaskListFragmentDirections.actionTaskListFragmentToTaskDetailsFragment(it))
        })

        viewModel.tasks.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                binding.addTaskButton.visibility = View.GONE
                setupListOfTasks()
            }
        })
    }

    private fun setupListOfTasks() {
        viewModel.getTasksList()?.forEach { task ->
            generateTextView(task)
        }
    }

    private fun generateTextView(task: Task) {
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        lp.setMargins(LEFT_RIGHT_MARGIN, TOP_BOTTOM_MARGIN, LEFT_RIGHT_MARGIN, TOP_BOTTOM_MARGIN)

        val current = TextView(this.context)
        current.text = formatTaskToString(task)
        current.layoutParams = lp
        current.setBackgroundResource(R.drawable.my_border)
        current.setTextColor(resources.getColor(R.color.black))
        current.setPadding(TEXT_VIEW_PADDING)
        current.setOnClickListener{
            viewModel.addTaskButtonClicked(task.taskId)
        }
        binding.rlMain.addView(current)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add_task_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_add_task) {
            binding.root.findNavController()
                .navigate(
                    TaskListFragmentDirections.actionTaskListFragmentToTaskDetailsFragment(
                        DEFAULT_KEY_VALUE
                    )
                )

        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val DEFAULT_KEY_VALUE = 0L
        const val TOP_BOTTOM_MARGIN = 16
        const val LEFT_RIGHT_MARGIN = 0
        const val TEXT_VIEW_PADDING = 20
    }
}
