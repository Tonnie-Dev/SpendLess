package dev.tonnie.common.utils

import dev.tonnie.exceptions.DataError
import dev.tonnie.exceptions.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> safeIoCall(
    block: suspend () -> T,
): Resource<T> {
    return try {
        withContext(Dispatchers.IO) {
            Resource.Success(block())
        }
    } catch (e: Exception) {
        Resource.Error(DataError.Unknown)
    }
}