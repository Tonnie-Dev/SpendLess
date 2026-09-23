package dev.tonnie.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import dev.tonnie.designsystem.theme.SpendLessTheme
import dev.tonnie.designsystem.theme.spacing

@Composable
fun AppErrorBanner(
    text: String,
    modifier: Modifier = Modifier
) {
    val spacing = MaterialTheme.spacing
    Text(
            modifier = modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.error)
                    .padding(
                            horizontal = spacing.spaceMedium,
                            vertical = spacing.spaceLarge
                    ),
            text = text,
            color = MaterialTheme.colorScheme.onError,
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {

    SpendLessTheme {
        Box(
                modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.BottomCenter
        ) {
            AppErrorBanner(text = "Something went wrong")
        }
    }
}