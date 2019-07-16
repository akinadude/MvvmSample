package ru.akinadude.mvvmsample

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

//todo soon it will be deprecated.
fun <T : Any> LiveData<T>.observe(lifecycleOwner: LifecycleOwner, block: (T) -> Unit) =
    observe(lifecycleOwner, Observer(block))
