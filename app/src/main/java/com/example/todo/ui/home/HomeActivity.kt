package com.example.todo.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.todo.R
import com.example.todo.databinding.ActivityHomeBinding
import com.example.todo.ui.home.addTask.AddTaskBottomSheet
import com.example.todo.ui.home.settings.SettingsFragment
import com.example.todo.ui.home.taskList.TaskListFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    private fun setupView() {
        binding.bottomNav.setOnItemSelectedListener { item ->
            if (item.itemId == R.id.nav_tasks_list) {
                showFragment(TaskListFragment())
            } else {
                showFragment(SettingsFragment())
            }
            return@setOnItemSelectedListener true
        }
        binding.bottomNav.selectedItemId = R.id.nav_tasks_list
        binding.fabAddTask.setOnClickListener {
            showAddTaskBottomSheet()
        }
    }

    private fun showAddTaskBottomSheet() {
        val addTask = AddTaskBottomSheet()
        addTask.show(supportFragmentManager, null)
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}