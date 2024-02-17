package com.example.todo.ui.home.taskList

import android.app.AlertDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todo.database.MyDatabase
import com.example.todo.database.model.Constants
import com.example.todo.database.model.Task
import com.example.todo.databinding.FragmentTasksBinding
import com.example.todo.ui.editTask.EditTaskActivity
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.example.todo.ui.getDateOnly

class TaskListFragment : Fragment() {
    private lateinit var binding: FragmentTasksBinding
    private val currentDate: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasksBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpViews()
        editTask()
        onDeleteTask()
        taskDone()

    }
    private fun taskDone() {
        adapter.onIsDoneClickListener = TaskAdapter
            .OnItemClickListener { item, position ->
                item.isDone = !item.isDone!!
                MyDatabase.getInstance(requireContext())
                    .getTaskDao()
                    .updateTask(item)
                adapter.notifyItemChanged(position)
            }
    }

    private fun onDeleteTask() {
        adapter.onDeleteClickListener = TaskAdapter
            .OnItemClickListener { item, _ ->
                showDialog {
                    onDelete(item)
                }
            }
    }

    private fun onDelete(item: Task) {
        MyDatabase.getInstance(requireContext())
            .getTaskDao()
            .deleteTask(item)
        retreiveTasksList()
    }
    private fun showDialog(
        posActionCallBack: (() -> Unit)? = null,
    ) {
        val onDeleteDialog = AlertDialog.Builder(requireContext())
        onDeleteDialog.setMessage("Do you want to delete this task?")
            .setPositiveButton(
                "Delete",
            ) { dialog, _ ->
                dialog.dismiss()
                posActionCallBack?.invoke()
            }
            .setNegativeButton(
                "Cancel",
            ) { dialog, _ ->
                dialog.dismiss()
            }
        onDeleteDialog.show()
    }

    private fun editTask() {
        adapter.onTaskClickListener = TaskAdapter.OnItemClickListener { item, _ ->
            openEditActivity(item)
        }
    }

    private fun openEditActivity(task: Task) {
        val intent = Intent(activity, EditTaskActivity::class.java)
       // intent.putExtra(Constants.TASK_KAY, task)
        startActivity(intent)
    }
    override fun onResume() {
        super.onResume()
        retreiveTasksList()
    }

     fun retreiveTasksList() {
        val allTasks = MyDatabase.getInstance(requireContext())
            .getTaskDao()
            .getAllTasks()
        adapter.changeData(allTasks)
    }

    val adapter = TaskAdapter()
    private fun setUpViews() {
        binding.rvTasks.adapter = adapter
        binding.calendarView.setOnDateChangedListener { widget, date, selected ->
            if (selected){
                currentDate.set(date.year,date.month ,date.day)
                retreiveTasksList()
            }
        }
        binding.calendarView.selectedDate = CalendarDay.today()
    }
}


