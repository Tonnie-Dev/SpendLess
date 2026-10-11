package dev.tonnie.presentation.formatting

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CurrencyFormatterPreferencesTest {

    private val currencyFormatter = CurrencyFormatter()

    @Test
    fun `test default preferences`() {
        val preferences = CurrencyFormatterPreferences()
        assertEquals("KES", preferences.currencyCode)
        assertEquals(ExpenseFormat.MINUS, preferences.expenseFormat)
        assertEquals(DecimalSeparator.DOT, preferences.decimalSeparator)
        assertEquals(ThousandsSeparator.COMMA, preferences.thousandsSeparator)
    }

    @Test
    fun `test value with 3 decimal places returns 2 decimal places`() {

        val amount = 98.018
        val formattedAmount = currencyFormatter.format(amount)
        assertEquals("KES 98.02", formattedAmount)
    }

    @Test
    fun `format Euro amount with European separators`() {

        val amount = 1000.0
        val preferences = CurrencyFormatterPreferences(
                currencyCode = "EUR",
                decimalSeparator = DecimalSeparator.COMMA,
                thousandsSeparator = ThousandsSeparator.DOT
        )
        val formattedAmount = currencyFormatter.format(
                amount,
                preferences = preferences
        )
        assertEquals("€ 1.000,00", formattedAmount)
    }

    @Test
    fun `format Kenyan amount with Kenyan separators`() {

        val amount = 10000.0
        val preferences = CurrencyFormatterPreferences(
                currencyCode = "KES",
                decimalSeparator = DecimalSeparator.DOT,
                thousandsSeparator = ThousandsSeparator.COMMA
        )
        val formattedAmount = currencyFormatter.format(
                amount,
                preferences = preferences
        )
        assertEquals("KES 10,000.00", formattedAmount)
    }

    @Test
    fun `format negative amount with minus sign`() {
        val amount = -12345.67
        val preferences = CurrencyFormatterPreferences(expenseFormat = ExpenseFormat.MINUS)
        val formattedAmount = currencyFormatter.format(amount, preferences = preferences)
        assertEquals("-KES 12,345.67", formattedAmount)
    }

    @Test
    fun `format negative amount with parenthesis`() {
        val amount = -12345.67
        val preferences = CurrencyFormatterPreferences(expenseFormat = ExpenseFormat.PARENTHESIS)
        val formattedAmount = currencyFormatter.format(amount, preferences = preferences)
        assertEquals("(KES 12,345.67)", formattedAmount)
    }

    @Test
    fun `format South African Rands amount with R symbol`() {
        val amount = 12345.67
        val preferences = CurrencyFormatterPreferences(
                currencyCode = "ZAR",
                currencyDisplayFormat = CurrencyDisplayFormat.SYMBOL,
                thousandsSeparator = ThousandsSeparator.SPACE
        )
        val formattedAmount = currencyFormatter.format(amount, preferences = preferences)
        assertEquals("ZAR 12 345.67", formattedAmount)
    }


    @Test
    fun `format Japanese Yen amount with Y symbol`() {
        val firstAmount = 12345.67
        val secondAmount = 12345.07
        val preferences = CurrencyFormatterPreferences(
                currencyCode = "JPY",
                currencyDisplayFormat = CurrencyDisplayFormat.SYMBOL,
                thousandsSeparator = ThousandsSeparator.SPACE
                )
        val formattedAmount1 = currencyFormatter.format(firstAmount, preferences = preferences)
        val formattedAmount2 = currencyFormatter.format(secondAmount, preferences = preferences)

        assertEquals("¥ 12 346", formattedAmount1)

        assertEquals("¥ 12 345", formattedAmount2)
    }


    @Test
    fun `format Kuwaiti Dinar amount with three decimal places`() {
        val amount = 1234.567

        val preferences = CurrencyFormatterPreferences(
                currencyCode = "KWD",
                currencyDisplayFormat = CurrencyDisplayFormat.CODE
        )

        val formattedAmount = currencyFormatter.format(
                amount = amount,
                preferences = preferences
        )

        assertEquals("KWD 1,234.567", formattedAmount)
    }
}

