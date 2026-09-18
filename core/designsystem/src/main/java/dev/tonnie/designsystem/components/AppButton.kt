package dev.tonnie.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.tonnie.designsystem.theme.OnSurfaceStateLayer12
import dev.tonnie.designsystem.theme.SpendLessTheme
import dev.tonnie.designsystem.theme.spacing

@Composable
fun AppButton(
    buttonText: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    trailingIcon: ImageVector? = null,
    buttonContainerColor: Color = MaterialTheme.colorScheme.primary,
    buttonTextColor: Color = MaterialTheme.colorScheme.onPrimary,
    buttonTextStyle: TextStyle = MaterialTheme.typography.titleMedium,
    contentDescription: String? = null,
    onClick: () -> Unit,
) {

    val (containerColor, contentColor) = if (enabled) {
        buttonContainerColor to buttonTextColor
    } else {
        OnSurfaceStateLayer12 to MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
    }
    Box(
            modifier = modifier
                    .clip(shape = MaterialTheme.shapes.large)
                    .background(containerColor)
                    .clickable(enabled = enabled, onClick = onClick)
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.spaceTwelve * 2)
                    .padding(vertical = MaterialTheme.spacing.spaceTwelve),
            contentAlignment = Alignment.Center
    ) {
        Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                    text = buttonText,
                    style = buttonTextStyle,
                    color = contentColor,
                    maxLines = 1
            )

            if (trailingIcon != null) {
                Icon(
                        modifier = Modifier,
                        imageVector = trailingIcon,
                        tint = contentColor,
                        contentDescription = contentDescription
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppButton_Preview() {
    SpendLessTheme {

        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(MaterialTheme.spacing.spaceMedium),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)
        ) {

            AppButton(
                    buttonText = "Continue",
                    trailingIcon = Icons.AutoMirrored.Filled.ArrowForward,
                    onClick = {}
            )

            AppButton(
                    buttonText = "Log in",
                    onClick = {}
            )

            AppButton(
                    buttonText = "Next",
                    trailingIcon = Icons.AutoMirrored.Filled.ArrowForward,
                    enabled = false,
                    onClick = {}
            )
        }
    }
}

