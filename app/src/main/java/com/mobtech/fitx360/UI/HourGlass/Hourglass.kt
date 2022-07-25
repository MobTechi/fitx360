@file:Suppress("PackageName")

package com.mobtech.fitx360.UI.HourGlass

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Message

@Suppress("DEPRECATION", "KDocUnresolvedReference")
abstract class Hourglass(timeInMillis: Long, intervalInMillis: Long) : HourglassListener {

    private var isRunning = false
    var isPaused = false

    private var time: Long = 0
    private var localTime: Long = 0
    private var interval: Long = 0
    private var handler: Handler? = null

    init {
        init(timeInMillis, intervalInMillis)
    }

    /**
     * Method to initialize HourGlass.
     *
     * @param time:     Time in milliseconds.
     * @param interval: in milliseconds.
     */
    private fun init(time: Long, interval: Long) {
        setTime(time)
        setInterval(interval)
        initHourglass()
    }

    @SuppressLint("HandlerLeak")
    private fun initHourglass() {
        handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (msg.what == MSG) {
                    if (!isPaused) {
                        if (localTime <= time) {
                            onTimerTick(time - localTime)
                            localTime += interval
                            sendMessageDelayed(handler!!.obtainMessage(MSG), interval)
                        } else stopTimer()
                    }
                }
            }
        }
    }

    /**
     * Method to start the timer.
     */
    fun startTimer() {
        if (isRunning) return
        isRunning = true
        isPaused = false
        localTime = 0
        handler!!.sendMessage(handler!!.obtainMessage(MSG))
    }

    /**
     * Method to stop the timer.
     */
    fun stopTimer() {
        isRunning = false
        handler!!.removeMessages(MSG)
        onTimerFinish()
    }

    /**
     * Setter for Time.
     *
     * @param timeInMillis: in milliseconds
     */
    private fun setTime(timeInMillisCount: Long) {
        var timeInMillis = timeInMillisCount
        if (isRunning) return
        if (time <= 0) if (timeInMillis < 0) timeInMillis *= -1
        time = timeInMillis
    }

    /**
     * Setter for interval.
     *
     * @param intervalInMilliSeconds: in milliseconds
     */
    private fun setInterval(intervalInMillisLong: Long) {
        var intervalInMillis = intervalInMillisLong
        if (isRunning) return
        if (interval <= 0) if (intervalInMillis < 0) intervalInMillis *= -1
        interval = intervalInMillis
    }

    companion object {
        private const val MSG = 1
    }
}