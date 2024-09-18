package com.example.todo.taskDetails

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import com.example.todo.app.AppDatabase
import com.example.todo.app.model.Task
import com.example.todo.clearDate
import com.example.todo.clearTime
import com.example.todo.databinding.ActivityTaskDetailsBinding
import com.example.todo.utlis.Constants
import com.example.todo.utlis.getFormattedTime
import java.util.Calendar

class EditTaskActivity: AppCompatActivity() {

    private lateinit var binding: ActivityTaskDetailsBinding
    private lateinit var intentTask: Task
    private lateinit var newTask: Task
    private var dateCalender = Calendar.getInstance()
    private var timeCalender = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intentTask = IntentCompat.getParcelableExtra(intent,Constants.TASK_KEY,Task::class.java) as Task
        initView()
        initNewTask()
        setUpToolbar()
        onSelectDateTV()
        onSelectTimeTV()
        onSaveClick()
    }

    private fun onSaveClick() {
        binding.content.btnSave.setOnClickListener{
            updateTask()
            finish()
        }
    }

    private fun updateTask() {
        if (!isValidForm()){
            return
        }
        newTask.apply {
            title = binding.content.title.text.toString()
            description = binding.content.description.text.toString()
        }
        AppDatabase
            .getInstance()
            .tasksDao()
            .updateTask(newTask)
    }

    private fun isValidForm(): Boolean {
        var isValid = true
        if (binding.content.title.text.toString().isBlank()){
            isValid = false
            binding.content.title.error = "Please Title Required"
        }else {
            binding.content.title.error = null
        }
        return isValid
    }

    private fun onSelectDateTV() {
        binding.content.selectDateTil.setOnClickListener{
            val dialog = DatePickerDialog(this)
            dialog.setOnDateSetListener { datePicker, year, month, day ->
                binding.content.selectDateTv.text = "$day/${month + 1}/$year"
                dateCalender.clearTime()
                dateCalender.set(Calendar.YEAR,year)
                dateCalender.set(Calendar.MONTH,month)
                dateCalender.set(Calendar.DAY_OF_MONTH,day)
                newTask.date = dateCalender.timeInMillis
            }
            dialog.show()
        }
    }

    private fun onSelectTimeTV() {
        binding.content.selectTimeTil.setOnClickListener{
            val dialog = TimePickerDialog(this,
                { view, hourOfDay, minute ->
                    binding.content.selectTimeTv.text = getFormattedTime(hourOfDay,minute)
                    timeCalender.clearTime()
                    timeCalender.clearDate()
                    timeCalender.set(Calendar.HOUR_OF_DAY,hourOfDay)
                    timeCalender.set(Calendar.MINUTE,minute)
                    newTask.time = timeCalender.timeInMillis
                }
                ,timeCalender.get(Calendar.HOUR_OF_DAY)
                ,timeCalender.get(Calendar.MINUTE)
                ,false)
            dialog.show()
        }
    }

    private fun initNewTask() {
        newTask = intentTask.copy()
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener{
            finish()
        }
    }

    private fun initView() {
        binding.content.title.setText(intentTask.title)
        binding.content.description.setText(intentTask.description)

        val dateCalender = Calendar.getInstance()
        dateCalender.timeInMillis = intentTask.date!!
        val year = dateCalender.get(Calendar.YEAR)
        val month = dateCalender.get(Calendar.MONTH)
        val day = dateCalender.get(Calendar.DAY_OF_MONTH)
        binding.content.selectTimeTv.text = "$day/${month+1}/$year"

        dateCalender.timeInMillis = intentTask.time!!
        val hour = dateCalender.get(Calendar.HOUR_OF_DAY)
        val minute = dateCalender.get(Calendar.MINUTE)
        binding.content.selectTimeTv.text = getFormattedTime(hour,minute)
    }
}