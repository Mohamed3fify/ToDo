package com.example.todo.ui

import android.app.AlertDialog
import androidx.fragment.app.Fragment
import java.util.Calendar


fun Fragment.showDialog(
        message : String,
        posActionName : String? = null,
        posActionCallBack: (() -> Unit)? = null,
        negActionName : String? = null,
        negActionCallBack : (() -> Unit)? = null,
        isCancelable : Boolean = true

    ):AlertDialog {
    val alterDialogBuilder = AlertDialog.Builder(requireContext())
    alterDialogBuilder.setMessage(message)

    alterDialogBuilder.setPositiveButton(
        posActionName
    ) {
        dialog , p1 ->
        dialog?.dismiss()
        posActionCallBack?.invoke()
    }
    alterDialogBuilder.setNegativeButton(
        negActionName
    ){
        dialog , p1 ->
        dialog?.dismiss()
        negActionCallBack?.invoke()
    }
     alterDialogBuilder.setCancelable(isCancelable)
    return alterDialogBuilder.show()
     }
