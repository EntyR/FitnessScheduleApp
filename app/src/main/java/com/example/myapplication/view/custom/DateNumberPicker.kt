package com.example.myapplication.view.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.NumberPicker
import com.example.myapplication.R

class DateNumberPicker(context: Context, val attr: AttributeSet?) :
    NumberPicker(context, attr, R.style.AppTheme_Picker) {

    init {
        setFormatter { value -> String.format("%02d", value) }
        if (attr != null) {
            this.minValue = attr.getAttributeIntValue(null, "minValue", 0)
            this.maxValue = attr.getAttributeIntValue(null, "maxValue", 23)
        }
    }
}