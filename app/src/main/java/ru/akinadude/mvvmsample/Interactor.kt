package ru.akinadude.mvvmsample

import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

abstract class Interactor<out Type>(private val coroutineContext: CoroutineContext) {

    suspend fun <Type> requestData(block: suspend () -> Result<Type>): Result<Type> =
        withContext(coroutineContext) {
            block()
        }
}