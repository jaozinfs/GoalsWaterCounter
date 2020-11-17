package com.example.orbitmvi.extensions

import android.view.View.OnTouchListener
import com.google.android.material.textfield.TextInputEditText


fun TextInputEditText.setEndIconClickListener(clickListener: ()->Unit ) {
    setOnTouchListener { _, event ->
        if (event.rawX <= compoundDrawables[2].bounds.width()) {
            clickListener.invoke()
            true
        } else false
    }
}