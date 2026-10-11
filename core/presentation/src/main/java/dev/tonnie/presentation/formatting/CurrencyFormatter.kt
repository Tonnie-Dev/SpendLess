package dev.tonnie.presentation.formatting

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Currency
import java.util.Locale
import kotlin.math.abs

enum class ExpenseFormat {
    MINUS,
    PARENTHESIS
}

enum class DecimalSeparator(val symbol: Char) {
    DOT('.'),
    COMMA(',')
}

enum class ThousandsSeparator(val symbol: Char) {

    COMMA(','),
    DOT('.'),
    SPACE(' ')
}

enum class CurrencyDisplayFormat {
    SYMBOL,
    CODE
}

data class CurrencyFormatterPreferences(
    val currencyCode: String = "KES",
    val currencyDisplayFormat: CurrencyDisplayFormat = CurrencyDisplayFormat.CODE,
    val expenseFormat: ExpenseFormat = ExpenseFormat.MINUS,
    val decimalSeparator: DecimalSeparator = DecimalSeparator.DOT,
    val thousandsSeparator: ThousandsSeparator = ThousandsSeparator.COMMA
)

class CurrencyFormatter {

    fun format(
        amount: Double,
        preferences: CurrencyFormatterPreferences = CurrencyFormatterPreferences()
    ): String {

        require(amount.isFinite()) {
            "Amount must be finite"
        }

        val currency = Currency.getInstance(preferences.currencyCode)

        require(preferences.decimalSeparator.symbol != preferences.thousandsSeparator.symbol) {
            "Decimal separator and thousands separator must differ"
        }

        val symbols = DecimalFormatSymbols(Locale.getDefault()).apply {
            decimalSeparator = preferences.decimalSeparator.symbol
            groupingSeparator = preferences.thousandsSeparator.symbol
        }

        val fractionDigits = currency
                .defaultFractionDigits
                .coerceAtLeast(0)

        val formatter = DecimalFormat("#,##0", symbols).apply {
            minimumFractionDigits = fractionDigits
            maximumFractionDigits = fractionDigits
            isGroupingUsed = true
        }

        val formattedAmount = formatter.format(abs(amount))

        val currencyLabel = when (preferences.currencyDisplayFormat) {
            CurrencyDisplayFormat.SYMBOL ->
                currency.getSymbol(Locale.getDefault())

            CurrencyDisplayFormat.CODE ->
                currency.currencyCode
        }

        val result = "$currencyLabel $formattedAmount"
        return if (amount<0) {
            when (preferences.expenseFormat) {
                ExpenseFormat.MINUS -> "-$result"
                ExpenseFormat.PARENTHESIS -> "($result)"
            }
        } else {
            result
        }
    }
}