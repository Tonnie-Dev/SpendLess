package dev.tonnie.designsystem.icon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.tonnie.designsystem.R
import dev.tonnie.designsystem.theme.SpendLessTheme
import dev.tonnie.designsystem.theme.spacing

@Composable
fun AppIcon(
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    Box(
            modifier = modifier
                    .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(percent = 32)
                    )
                    .padding(all = MaterialTheme.spacing.spaceMedium),
            contentAlignment = Alignment.Center,
    ) {
        Image(

                modifier = Modifier.size(ICON_SIZE),
                painter = painterResource(R.drawable.wallet_money),
                contentDescription = contentDescription
        )
    }
}

private val ICON_SIZE = 30.dp

@Preview(showBackground = true, backgroundColor = 0xFFFEF7FF)
@Composable
private fun AppIconPreview() {
    SpendLessTheme {
        AppIcon()
    }
}

