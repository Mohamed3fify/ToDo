package com.example.todo.ui

import java.text.SimpleDateFormat
import java.util.Calendar


     fun Calendar.getDateOnly():Long{
        val calnder = Calendar.getInstance()
        calnder.time = time
        calnder.set(
            get(Calendar.YEAR),
            get(Calendar.MONTH),
            get(Calendar.DATE),
            0,0,0,)
        calnder.set(Calendar.MILLISECOND,0)
        return time.time
    }
     fun Calendar.getTimeOnly():Long{
        val calnder = Calendar.getInstance()
        calnder.time = time
        calnder.set(
            0,
            0,
            0,
            get(Calendar.HOUR_OF_DAY),
            get(Calendar.MINUTE),
            0)
        calnder.set(Calendar.MILLISECOND,0)
        return time.time
    }
     fun Calendar.formatTime():String{
        val formatter = SimpleDateFormat("hh:mm a")
        return formatter.format(time)
    }
     fun Calendar.formatDate():String{
        val formatter = SimpleDateFormat("dd/mm/yyyy")
        return formatter.format(time)
    }
