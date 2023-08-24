package com.example.myapplication.view.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

import android.text.InputType
import android.util.Log

import android.widget.EditText


object WorkoutDialog {
    fun showEnterWorkoutNameDialog(ctx: Context, onAccept: (str: String) -> Unit) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(ctx)
        builder.setTitle("Name your routine")

        val input = EditText(ctx)

        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        builder.setPositiveButton("OK"
        ) { _, _ ->
            onAccept(input.text.toString())
        }
        builder.setNegativeButton("Cancel"
        ) { dialog, _ -> dialog.cancel() }

        builder.show()
    }
}