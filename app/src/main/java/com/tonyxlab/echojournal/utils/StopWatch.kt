package com.tonyxlab.echojournal.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class StopWatch {


    private val _formattedTime = MutableStateFlow(Constants.DEFAULT_FORMATTED_TIME)
    val formattedTime: StateFlow<String> = _formattedTime

    private var coroutineScope = CoroutineScope(Dispatchers.Main)
    private var isRunning = false

    private var timeMillis = 0L
    private var lastTimestamp = 0L

    fun start() {

        if (isRunning) return

        coroutineScope.launch {
            lastTimestamp = System.currentTimeMillis()
            isRunning = true
            while (isRunning) {
                delay(10L)
                timeMillis += System.currentTimeMillis() - lastTimestamp
                lastTimestamp = System.currentTimeMillis()
                _formattedTime.value = timeMillis.formatMillisToTime()
            }
        }
    }


    fun pause() {

        isRunning = false
    }


    fun stop() {

        coroutineScope.cancel()
//        coroutineScope = CoroutineScope(Dispatchers.Main)
        timeMillis = 0L
        lastTimestamp = 0L
        _formattedTime.value = Constants.DEFAULT_FORMATTED_TIME
        isRunning = false
    }

    fun reset() {

        coroutineScope.cancel()
        //       coroutineScope = CoroutineScope(appCoroutineDispatchers.main)
        timeMillis = 0L
        lastTimestamp = 0L
        _formattedTime.value = Constants.DEFAULT_FORMATTED_TIME
        isRunning = false
    }

}