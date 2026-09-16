package dev.tonnie.designsystem.theme

import androidx.compose.ui.graphics.Color

// Light palette transcribed from scheme.png.
val Primary = Color(0xFF5A00C8)
val OnPrimary = Color(0xFFFFFFFF)
val PrimaryContainer = Color(0xFF8138FF)
val OnPrimaryContainer = Color(0xFFFFFFFF)
val PrimaryFixed = Color(0xFFEADDFF)
val OnPrimaryFixed = Color(0xFF24005A)
val OnPrimaryFixedVariant = Color(0xFF5900C7)
val InversePrimary = Color(0xFFD2BCFF)

val Secondary = Color(0xFF5F6200)
val SecondaryContainer = Color(0xFFD2E750)
val OnSecondaryContainer = Color(0xFF414300)
val SecondaryFixed = Color(0xFFE5EA58)
val SecondaryFixedDim = Color(0xFFC9CE3E)
val TertiaryContainer = Color(0xFFC4E0F9)

val Error = Color(0xFFA40019)
val OnError = Color(0xFFFFFFFF)
val Success = Color(0xFF29AC08)

val Surface = Color(0xFFFCF9F9)
val SurfaceContainerLowest = Color(0xFFFFFFFF)
val SurfaceContainerLow = Color(0xFFF6F3F3)
val SurfaceContainer = Color(0xFFF0EDED)
val SurfaceContainerHighest = Color(0xFFE4E2E2)
val OnSurface = Color(0xFF1B1B1C)
val OnSurfaceVariant = Color(0xFF44474B)
val Outline = Color(0xFF75777B)
val InverseSurface = Color(0xFF303031)
val InverseOnSurface = Color(0xFFF3F0F0)
val Background = Color(0xFFFEF7FF)
val OnBackground = Color(0xFF1D1A25)

// State layers retain alpha so they can be composited over the actual surface.
// The first primary label says 0.12, but its printed percentage says 8%.
val PrimaryStateLayer08 = Primary.copy(alpha = 0.08f)
val PrimaryStateLayer16 = Primary.copy(alpha = 0.16f)
val OnPrimaryStateLayer12 = OnPrimary.copy(alpha = 0.12f)
val PrimaryContainerStateLayer08 = PrimaryContainer.copy(alpha = 0.08f)
val OnPrimaryContainerStateLayer12 = OnPrimaryContainer.copy(alpha = 0.12f)
val OnSecondaryContainerStateLayer08 = OnSecondaryContainer.copy(alpha = 0.08f)
val OnSecondaryContainerStateLayer12 = OnSecondaryContainer.copy(alpha = 0.12f)
val ErrorStateLayer08 = Error.copy(alpha = 0.08f)
val ErrorStateLayer12 = Error.copy(alpha = 0.12f)
val OnBackgroundStateLayer08 = OnBackground.copy(alpha = 0.08f)
val OnBackgroundStateLayer12 = OnBackground.copy(alpha = 0.12f)
val OnSurfaceStateLayer12 = OnSurface.copy(alpha = 0.12f)
val OnSurfaceVariantStateLayer12 = OnSurfaceVariant.copy(alpha = 0.12f)

// Existing dark palette: the reference image does not specify a dark scheme.
val DarkPrimary = Color(0xFFD0BCFF)
val DarkSecondary = Color(0xFFCCC2DC)
val DarkTertiary = Color(0xFFEFB8C8)
