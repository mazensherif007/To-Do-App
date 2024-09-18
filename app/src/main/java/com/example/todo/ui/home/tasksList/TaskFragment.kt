package com.example.todo.ui.home.tasksList

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todo.app.AppDatabase
import com.example.todo.databinding.FragmentTasksBinding
import com.example.todo.ignoreTime
import com.example.todo.setCurrentDate
import com.example.todo.taskDetails.EditTaskActivity
import com.example.todo.utlis.Constants
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.util.Calendar

class TaskFragment : Fragment() {

    private lateinit var binding: FragmentTasksBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val adapter = TasksAdapter()
    private val selectedDate = Calendar.getInstance().apply { ignoreTime() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRV()
        binding.calendarView.setDateSelected(
            CalendarDay.today(),
            true
        )

        binding.calendarView.setOnDateChangedListener { widget, date, selected ->
            selectedDate.setCurrentDate(
                date.year,
                date.month - 1,
                date.day
            )
            getTasksFromDataBase()
        }
    }

    private fun initRV() {
        binding.rvTasks.adapter = adapter
        adapter.onDoneBtnClickListener =
            TasksAdapter.OnItemClickListener{ position, task ->
                task.status = !task.status
                AppDatabase
                    .getInstance()
                    .tasksDao()
                    .updateTask(task)
                adapter.notifyItemChanged(position)
            }
        adapter.onClick = { position, task ->
            val intent = Intent(requireContext(), EditTaskActivity::class.java)
            intent.putExtra(Constants.TASK_KEY, task)
            startActivity(intent)
        }

        adapter.onDeleteClick = { position, task ->
            AppDatabase
                .getInstance()
                .tasksDao()
                .deleteTask(task)
            adapter.deleteTask(position, task)
        }
    }

    override fun onResume() {
        super.onResume()
        getTasksFromDataBase()
    }

    fun getTasksFromDataBase() {
        if (isDetached)
            return
        val tasks = AppDatabase
            .getInstance()
            .tasksDao()
            .getTasksByDate(selectedDate.timeInMillis)
        adapter.submitNewData(tasks.toMutableList())
    }
}