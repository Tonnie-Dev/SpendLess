package dev.tonnie.exceptions

sealed interface DataError {

    data object UsernameAlreadyExists : DataError

    data object InvalidUsernameFormat : DataError

    data object InvalidUsernameLength : DataError

    data object AccountNotFound : DataError

    data object DatabaseError : DataError

    data object Unknown : DataError
}