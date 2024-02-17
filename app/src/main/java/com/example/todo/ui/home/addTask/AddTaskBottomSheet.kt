package com.example.todo.ui.home.addTask

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todo.database.MyDatabase
import com.example.todo.database.model.Task
import com.example.todo.databinding.FragmentAddTaskBinding
import com.example.todo.ui.formatDate
import com.example.todo.ui.formatTime
import com.example.todo.ui.getDateOnly
import com.example.todo.ui.getTimeOnly
import com.example.todo.ui.showDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar

class AddTaskBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAddTaskBinding
     private val calendar: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTaskBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        binding.addTaskBtn.setOnClickListener {
            addTask()
        }

    }

    private fun setUpViews() {
        binding.selectDateTil.setOnClickListener {
            showDatePicker()
        }
        binding.selectTimeTil.setOnClickListener {
            showTimePicker()
        }
        binding.addTaskBtn.setOnClickListener {
            addTask()
        }

    }

    private fun showTimePicker() {
        val timePicker = TimePickerDialog(requireContext(),
            {
            dialog, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY,hourOfDay)
            calendar.set(Calendar.MINUTE,minute)
            binding.selectTimeTv.text= calendar.formatTime()
            binding.selectTimeTil.error = null
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false
        )
        timePicker.show()

    }


    private fun showDatePicker() {
        val dataPicker = DatePickerDialog(requireContext())
        dataPicker.setOnDateSetListener { dialog, year, month, day ->

            calendar.set(Calendar.DAY_OF_MONTH,day)
            calendar.set(Calendar.MONTH,month)
            calendar.set(Calendar.YEAR,year)
            binding.selectDateTv.text = calendar.formatDate()
            binding.selectDateTil.error = null
        }
        dataPicker.show()
    }

    private fun isValidTask(): Boolean {
        var isValid = true
        val title = binding.title.text.toString()
        val description = binding.description.text.toString()

        if (title.isBlank()){
            binding.titleTil.error = "Please entre task title"
            isValid = false
        }
        else binding.titleTil.error = null
        
        if (description.isBlank()){
            binding.descriptionTil.error = "Please entre task description"
            isValid = false
        } else { binding.descriptionTil.error = null}

        if (binding.selectTimeTv.text.isBlank()){
            binding.selectTimeTil.error = "please select time"
            isValid= false
        }

        if (binding.selectDateTv.text.isBlank()){
            binding.selectDateTil.error = "please select date"
            isValid= false
        }

        return isValid
    }

    private fun addTask() {
        if (!isValidTask())
            return
        MyDatabase.getInstance(requireContext())
            .getTaskDao().insertTask(Task(
                title = binding.title.text.toString(),
                contact = binding.description.text.toString(),
                date =  calendar.getDateOnly(),
                time = calendar.getTimeOnly()

            ))
        showDialog("Task inserted Successfully"
        , posActionName = "ok" ,
            posActionCallBack = {
            dismiss()
            },
            isCancelable = false
        )
    }


}