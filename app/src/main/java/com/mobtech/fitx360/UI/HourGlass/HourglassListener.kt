package com.mobtech.fitx360.UI.HourGlass

interface HourglassListener {
    /**
     * Method to be called every second by the [Hourglass]
     *
     * @param timeRemaining: Time remaining in milliseconds.
     */
    fun onTimerTick(timeRemaining: Long)

    /**
     * Method to be called by [Hourglass] when the thread is getting  finished
     */
    fun onTimerFinish()
}