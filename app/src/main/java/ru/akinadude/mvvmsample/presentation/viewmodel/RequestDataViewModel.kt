package ru.akinadude.mvvmsample.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

open class RequestDataViewModel : ViewModel() {
    protected fun requestData(block: suspend () -> Unit): Job = viewModelScope.launch { block() }
}