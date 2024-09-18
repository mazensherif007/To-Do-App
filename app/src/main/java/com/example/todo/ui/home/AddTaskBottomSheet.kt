package com.example.todo.ui.home

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import java.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todo.R
import com.example.todo.app.AppDatabase
import com.example.todo.app.model.Task
import com.example.todo.databinding.FragmentAddTaskBinding
import com.example.todo.formatDateOnly
import com.example.todo.formatTimeOnly
import com.example.todo.ignoreDate
import com.example.todo.ignoreTime
import com.example.todo.setCurrentDate
import com.example.todo.setCurrentTime
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddTaskBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentAddTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTaskBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.selectDateTv.setOnClickListener{
            showDataPicker()
        }

        binding.selectTimeTv.setOnClickListener{
            showTimePicker()
        }

        binding.addTaskBtn.setOnClickListener{
            createTask()
        }
    }

    private fun isValidForm():Boolean{
       var isValid = true
       if (binding.title.text.toString().isBlank()){
           isValid = false
           binding.title.error = "Please Title Required"
       }else{
           binding.title.error = null
       }

       if (binding.description.text.toString().isBlank()){
            isValid = false
            binding.descriptionTil.error = "Please Description Required"
       }else{
            binding.descriptionTil.error = null
       }

        if (binding.selectDateTv.text.toString().isBlank()){
            isValid = false
            binding.selectDateTil.error = "Please Date Required"
        }else{
            binding.selectDateTil.error = null
        }

        if (binding.selectTimeTv.text.toString().isBlank()){
            isValid = false
            binding.selectTimeTil.error = "Please Time Required"
        }else {
            binding.selectTimeTil.error = null
        }

        return isValid
    }

    private fun createTask() {
        if (!isValidForm())
            return
        AppDatabase.getInstance()
            .tasksDao()
            .createTask(
                Task(
                    title = binding.title.text.toString(),
                    description = binding.description.text.toString(),
                    date = date.timeInMillis,
                    time = time.timeInMillis,
            )   )
        showSuccessDialog()
        taskAddedListener?.taskAdded()
    }

    private fun showSuccessDialog() {
        val builder = AlertDialog.Builder(context)
            .setMessage("Task Added Successfully")
            .setPositiveButton(R.string.ok) { dialog, _ ->
                dialog.dismiss()
                dismiss()
            }
            .setCancelable(false)
        builder.show()
    }

    val date = Calendar.getInstance().apply { ignoreTime() }

    val time = Calendar.getInstance().apply { ignoreDate() }

    private fun showDataPicker() {
        val dialog = DatePickerDialog(
            requireContext(), { datePicker , year , month , day ->
                date.setCurrentDate(year,month,day)
                binding.selectDateTv.text = date.formatDateOnly()
            },
            date.get(Calendar.YEAR),
            date.get(Calendar.MONTH),
            date.get(Calendar.DAY_OF_MONTH),
        )
        dialog.show()
    }

    private fun showTimePicker() {
        val dialog = TimePickerDialog(
            requireContext(), { timePicker, minute, hour ->
                time.setCurrentTime(minute,hour)
                binding.selectTimeTv.text = time.formatTimeOnly()
            },
            time.get(Calendar.HOUR),
            time.get(Calendar.MINUTE),
            false
        )
        dialog.show()
    }

    var taskAddedListener : OnTaskAddedListener? = null

    fun interface OnTaskAddedListener{
        fun taskAdded()
    }

}