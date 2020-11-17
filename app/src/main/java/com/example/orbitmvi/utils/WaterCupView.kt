package com.example.orbitmvi.utils

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import com.example.orbitmvi.R
import com.example.orbitmvi.extensions.dp
import com.example.orbitmvi.extensions.px
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlin.coroutines.coroutineContext


class WaterCupView(private val c: Context, private val attributeSet: AttributeSet) : ConstraintLayout(
    c,
    attributeSet
) {

    private val view: View = View.inflate(context, R.layout.water_view, this)
    private var waterCupCallback: WaterCupCallback? = null
    private var jobAnimation: Job? = null

    private val waterCupView =
        view.findViewById<ConstraintLayout>(R.id.water_cup)


    private val waterView : WaterView by lazy {
        view.findViewById<WaterView>(R.id.water)
    }

    private val currentProgressView by lazy {
        view.findViewById<TextView>(R.id.current_progress)
    }


    private var waterCupsDaily = 0
    private var currentWaterCups = 0
    private var currentProgress: Int = 0



    fun setCurrentWaterCups(waterCups: Int) {
        currentWaterCups = waterCups
        updateLayoutWater()
    }
    fun addMoreCup() {
        currentWaterCups += 1
        updateLayoutWater()
    }

    fun setWaterCupsDaily(cups: Int) {
        waterCupsDaily = cups
    }

    fun setWaterCupCallback(waterCupCallback: WaterCupCallback) {
        this@WaterCupView.waterCupCallback = waterCupCallback
    }

    private fun updateLayoutWater() {
        val percentPerCup: Int = (currentWaterCups / (waterCupsDaily.toDouble() / 100.toDouble())).toInt()
        callListeners()
        ValueAnimator.ofInt(currentProgress, percentPerCup).apply {
            duration = 200
            addUpdateListener {
                val currentValue = (it.animatedValue as Int)
                if(currentValue == percentPerCup)
                    currentProgress = currentValue
               currentProgressView.text = "$currentValue %"
            }
        }.start()


        waterView.setPercent(currentWaterCups / (waterCupsDaily * 1.0))
        callListeners()
    }



    private fun callListeners() {
        if (isCupComplete())
            waterCupCallback?.onDailyComplete()
        else
            waterCupCallback?.onIncrease(currentWaterCups)
            waterCupCallback?.onIncreasePercent(currentProgress)
    }

    private fun initCup() {
        currentProgressView.text = "0 %"
        waterView.layoutParams = waterView.layoutParams.apply {
            height = 1
        }
    }


    private fun isCupComplete() = currentWaterCups >= waterCupsDaily


}

open class WaterCupCallback {
    open fun onIncrease(count: Int) {}
    open fun onIncreasePercent(percent: Int) {}
    open fun onDailyComplete() {}
}