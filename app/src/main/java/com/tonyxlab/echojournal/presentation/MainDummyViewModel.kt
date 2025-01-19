package com.tonyxlab.echojournal.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainDummyViewModel @Inject constructor() : ViewModel() {
   private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()
    init {

        viewModelScope.launch {

            delay(3_000)
            _isReady.update { true }
        }
    }
}
