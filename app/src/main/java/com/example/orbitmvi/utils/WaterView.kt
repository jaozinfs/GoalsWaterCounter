package com.example.orbitmvi.utils

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.orbitmvi.R
import kotlin.math.sin

class WaterView(
    private val ctx: Context,
    private val attributeSet: AttributeSet
) : View(ctx, attributeSet) {
    private val frequency = 0.5
    private val weaveSize = 15

    private val colorComplete = Color.GREEN
    private val colorBlue = ContextCompat.getColor(context, R.color.colorBlue)
    private val colorCyan= ContextCompat.getColor(context, R.color.colorBlueFront)


    private val paint = Paint().apply {
        color = colorBlue
    }
    private val paintFront = Paint().apply {
        color = colorCyan
    }

    private var timer = 0
    private var percent = 0.1

    private var isComplete = false


    fun setPercent(percent: Double) {
        setProgress(percent)
        checkComplete()
        ValueAnimator.ofFloat(this@WaterView.percent.toFloat(), percent.toFloat()).apply {
            duration = 200
            addUpdateListener {
                this@WaterView.percent = it.animatedValue as Float * 1.0
            }
        }.start()
    }

    private fun setProgress(progressPercent: Double) {
        isComplete = (progressPercent == 1.0)
    }

    private fun checkComplete() {
        if(isComplete) {
            setWaterProgressCompletedColors()
        } else {
            setWaterProgressColors()
        }
    }

    private fun setWaterProgressCompletedColors() {
        paint.color = colorComplete
        paintFront.color = colorComplete
    }

    private fun setWaterProgressColors () {
        paint.color = colorBlue
        paintFront.color = colorCyan
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            for(i in 0 until width step 1) {
                val angle = (i + timer * - 0.3) * frequency
                val rad = angle / 180f * Math.PI
                val y = weaveSize * ( sin(rad) - 1 ) + height * ( 1 - percent ) - 30
                drawLine(i.toFloat(), y.toFloat(), i.toFloat(), height.toFloat(), paint)
            }
            for(i in 0 until width step 1) {
                val angle = (i + timer + 45)  * frequency
                val rad = angle / 180f * Math.PI
                val y = weaveSize * ( sin(rad) - 1 ) + height * ( 1 - percent )
                drawLine(i.toFloat(), y.toFloat(), i.toFloat(), height.toFloat(), paintFront)
            }
        }
        timer += 4
        invalidate()
    }

}