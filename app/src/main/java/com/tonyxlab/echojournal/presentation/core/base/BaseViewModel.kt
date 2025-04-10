package com.tonyxlab.echojournal.presentation.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonyxlab.echojournal.presentation.core.base.handling.ActionEvent
import com.tonyxlab.echojournal.presentation.core.base.handling.UiEvent
import com.tonyxlab.echojournal.presentation.core.base.handling.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseViewModel<S : UiState, E : UiEvent, A : ActionEvent> : ViewModel() {
    protected abstract val initialState: S

    private val _uiState: MutableStateFlow<S> by lazy { MutableStateFlow(initialState) }
    val uiState = _uiState.asStateFlow()

    protected val currentState: S
        get() = _uiState.value

    private val _actionEvent = Channel<A>()
    val actionEvent = _actionEvent.receiveAsFlow()

    protected fun updateState(block: (currentState: S) -> S) {
        _uiState.update(block)
    }

    protected fun sendActionEvent(actionEvent: A) {
        viewModelScope.launch { _actionEvent.send(actionEvent) }
    }

    protected fun launch(
        context: CoroutineContext = EmptyCoroutineContext,
        block: suspend CoroutineScope.() -> Unit,
    ) = viewModelScope.launch(context = context, block = block)

    abstract fun onEvent(event: E)
}