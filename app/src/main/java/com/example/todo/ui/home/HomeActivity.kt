package com.example.todo.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.todo.R
import com.example.todo.databinding.ActivityHomeBinding
import com.example.todo.ui.home.settings.SettingsFragment
import com.example.todo.ui.home.tasksList.TaskFragment

class HomeActivity : AppCompatActivity() {

    private var taskFragment : TaskFragment? = null
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.navigation_tasks -> {
                    taskFragment = TaskFragment()
                    showFragment(taskFragment!!)
                }
                R.id.navigation_settings -> {
                    showFragment(SettingsFragment())
                }
                else -> {}
            }
            return@setOnItemSelectedListener true
        }
        binding.bottomNavigation.selectedItemId = R.id.navigation_tasks
        binding.fabAddTask.setOnClickListener{
            showAddTaskBottomSheet()
        }
    }

    private fun showAddTaskBottomSheet() {
        val addTaskSheet = AddTaskBottomSheet()
        addTaskSheet.taskAddedListener = AddTaskBottomSheet.OnTaskAddedListener{
            taskFragment?.getTasksFromDataBase()
        }
        addTaskSheet.show(supportFragmentManager, "")
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}