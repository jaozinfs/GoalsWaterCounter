package com.example.orbitmvi

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.orbitmvi.domain.UserRepository
import com.example.orbitmvi.utils.WaterCupCallback
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_current_wather.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.zip
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainActivity : AppCompatActivity(), KoinComponent {

    private val userRepositoryLocalImpl by inject<UserRepository>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_current_wather)


        waterView.setWaterCupCallback(waterViewCallback)
        add_more.setOnClickListener {
            waterView.addMoreCup()
        }

        input_layout_daily_goal_edit_text.setEndIconOnClickListener {
            lifecycleScope.launch {
                userRepositoryLocalImpl.setDailyWaterCount(daily_goal_edit_text.text.toString().toInt())
            }
        }

        lifecycleScope.launch {
            userRepositoryLocalImpl.getCurrentDailyWaterCount().collect {
                val (f1, f2) = it
                daily_goal_edit_text.setText(f1.toString())
                daily_goal_edit_text.setSelection(f1.toString().length)
                waterView.setWaterCupsDaily(f1)
                waterView.setCurrentWaterCups(f2)
            }
        }
    }

    private val waterViewCallback = object : WaterCupCallback() {
        override fun onDailyComplete() {
            Snackbar.make(main_layout, getString(R.string.complete_cups), Snackbar.LENGTH_LONG).show()
        }

        override fun onIncrease(count: Int) {
            lifecycleScope.launch {
                userRepositoryLocalImpl.setCurrentCupsDaily(count)
            }
            Snackbar.make(main_layout, getString(R.string.add_cup, count.toString()), Snackbar.LENGTH_LONG).show()
        }
    }
}