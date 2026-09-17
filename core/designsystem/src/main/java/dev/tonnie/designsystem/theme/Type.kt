package dev.tonnie.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.tonnie.designsystem.R

val FigtreeFontFamily = FontFamily(
        Font(
                resId = R.font.figtree_regular,
                weight = FontWeight.Normal,
        ),
        Font(
                resId = R.font.figtree_medium,
                weight = FontWeight.Medium,
        ),
        Font(
                resId = R.font.figtree_semibold,
                weight = FontWeight.SemiBold,
        ),
)

val Typography = Typography(

        // Display
        displayLarge = TextStyle(
                fontFamily = FigtreeFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 45.sp,
                lineHeight = 52.sp,
        ),
        displayMedium = TextStyle(
                fontFamily = FigtreeFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 36.sp,
                lineHeight = 44.sp,
        ),

        // Headline
        headlineLarge = TextStyle(
                fontFamily = FigtreeFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 32.sp,
                lineHeight = 40.sp,
        ),
        headlineMedium = TextStyle(
                fontFamily = FigtreeFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp,
                lineHeight = 34.sp,
        ),

        // Title
        titleLarge = TextStyle(
                fontFamily = FigtreeFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                lineHeight = 26.sp,
        ),
        titleMedium = TextStyle(
                fontFamily = FigtreeFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                lineHeight = 24.sp,
        ),
        titleSmall = TextStyle(
                fontFamily = FigtreeFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 20.sp,
        ),

        // Label
        labelMedium = TextStyle(
                fontFamily = FigtreeFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                lineHeight = 24.sp,
        ),
        labelSmall = TextStyle(
                fontFamily = FigtreeFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 20.sp,
        ),

        // Body
        bodyMedium = TextStyle(
                fontFamily = FigtreeFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
        ),
        bodySmall = TextStyle(
                fontFamily = FigtreeFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 20.sp
        )
)

object ExtendedTypography {
    val BodyXSmall = TextStyle(
            fontFamily = FigtreeFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 16.sp,
    )
}

val Typography.bodyXSmall: TextStyle
    get() = ExtendedTypography.BodyXSmall
