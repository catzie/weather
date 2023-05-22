package net.catzie.weather.model

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 *
 */
sealed class ApiResult<out T : Any> {

    data class Success<out T : Any>(val data: T) : ApiResult<T>()
    data class Error(val errorResId: Int, val exception: Exception? = null) : ApiResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[errorResId=$errorResId, exception=$exception]"
        }
    }
}