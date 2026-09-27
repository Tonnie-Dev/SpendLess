package dev.tonnie.authentication.utils

import androidx.annotation.StringRes
import dev.tonnie.authentication.R
import dev.tonnie.domain.constants.AccountConstants.PIN_LENGTH
import dev.tonnie.exceptions.DataError

@StringRes
fun DataError.toErrorMessage(input: String): Int? {
    return when (this) {
        DataError.InvalidUsernameLength -> {
            if (input.length > 14) {
                R.string.supporting_text_username_error_length
            } else {
                null
            }
        }

        DataError.InvalidUsernameFormat -> R.string.supporting_text_username_error_format

        DataError.InvalidPinLength -> {
            if (input.length > PIN_LENGTH) {
                R.string.supporting_text_error_pin_length
            } else {
                null
            }
        }

        DataError.InvalidPinFormat -> R.string.supporting_text_error_pin_format
        else -> null
    }
}
