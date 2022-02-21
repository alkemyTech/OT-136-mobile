package com.melvin.ongandroid.model.repository

import com.melvin.ongandroid.model.DataSourceBase.Serializable
import retrofit2.Response

abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): ResourceBase<T> {
        try {
            val response = call()
            val body = response.body()
            return ResourceBase.success(body)
        } catch (e: Exception) {
            return ResourceBase.error(e.message ?: "Generic error")
        }
    }
}

/**
 * Clase de asistencia que permite encapsular las respuestas del repositorio
 * según su estados (mientras esta cargando, cuando se cargo con éxito y cuando ocurrió algún error)
 */
data class ResourceBase<out T>(val status: Status, val data: T?, val message: String?) :
    Serializable {

    enum class Status {

        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T?): ResourceBase<T> {
            return ResourceBase(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(message: String, data: T? = null): ResourceBase<T> {
            return ResourceBase(
                Status.ERROR,
                data,
                message
            )
        }

        fun <T> loading(data: T? = null): ResourceBase<T> {
            return ResourceBase(
                Status.LOADING,
                data,
                null
            )
        }
    }

    fun isSuccessful() = status == Status.SUCCESS

    fun isError() = status == Status.ERROR

    fun isLoading() = status == Status.LOADING
}