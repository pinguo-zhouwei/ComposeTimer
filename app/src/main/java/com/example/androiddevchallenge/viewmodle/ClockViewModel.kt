package com.example.androiddevchallenge.viewmodle


import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class ClockViewModel : ViewModel() {
    private val _time = MutableLiveData(0L)
    private val _progress = MutableLiveData(0f)
    private lateinit var countDownTimer: CountDownTimer
    val time: LiveData<Long> = _time
    val progress: LiveData<Float> = _progress

    /**
     * start countdown
     */
    fun start(time: Long) {
        countDownTimer = object : CountDownTimer(time * 1000, 1000) {
            override fun onFinish() {

            }

            override fun onTick(millisUntilFinished: Long) {
                _time.value = millisUntilFinished / 1000
                _progress.value =  (1- (millisUntilFinished / 1000 * 1.0f / time ))
            }
        }
        countDownTimer.start()
    }

    fun cancel() {
        countDownTimer?.cancel()
    }
}