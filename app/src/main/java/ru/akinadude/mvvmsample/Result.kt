package ru.akinadude.mvvmsample

/*sealed class Result<out T, out F> {
    data class Success<out T>(val data: T) : Result<T, Nothing>()
    data class Failure<out F>(val b: F) : Result<Nothing, F>()
}*/

sealed class Result<out T> {
    data class Success<out T>(val value: T) : Result<T>()
    data class Failure(val failure: Throwable) : Result<Nothing>()
}