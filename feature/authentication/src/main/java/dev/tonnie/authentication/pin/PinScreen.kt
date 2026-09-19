package dev.tonnie.authentication.pin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.tonnie.designsystem.icon.AppIcon
import dev.tonnie.designsystem.theme.OnSurfaceStateLayer12
import dev.tonnie.designsystem.theme.SpendLessTheme
import dev.tonnie.designsystem.theme.spacing
import dev.tonnie.authentication.R

/** The caller owns PIN state and handles completion when [onPinChange] delivers five digits.
 * Apply the host's system-bar padding through [modifier], as with RegistrationScreen.
 */
@Composable
fun PinScreen(
    pin: String,
    onPinChange: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val progress = stringResource(R.string.cds_text_pin_progress, pin.length, PIN_LENGTH)

    val spacing = MaterialTheme.spacing
    Column(
            modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = spacing.spaceLarge),
            horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                                top = spacing.spaceLarge,
                                bottom = spacing.spaceTwenty
                        )
        ) {
            IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(start = 4.dp),
            ) {
                Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.cds_text_back),
                        tint = MaterialTheme.colorScheme.onBackground,
                )
            }
            AppIcon(Modifier.align(Alignment.TopCenter))
        }

        Column(
                modifier = Modifier.padding(bottom = spacing.spaceLarge),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(spacing.spaceSmall)
        ) {
            Text(
                    text = stringResource(R.string.header_text_create_pin),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
            )

            Text(
                    text = stringResource(R.string.caption_text_create_pin),
                    modifier = Modifier.padding(horizontal = 24.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
            )

        }

        Row(
                modifier = Modifier
                        .clearAndSetSemantics { contentDescription = progress }
                        .padding(bottom = spacing.spaceLarge),
                horizontalArrangement = Arrangement.spacedBy(spacing.spaceTen),
        ) {
            repeat(PIN_LENGTH) { index ->
                Box(
                        Modifier
                                .size(PIN_BOX_SIZE)
                                .background(
                                        color = if (index < pin.length)
                                            MaterialTheme.colorScheme.primary
                                        else
                                            OnSurfaceStateLayer12,
                                        shape = CircleShape,
                                )
                )
            }
        }
        Column(
                modifier = Modifier
                        .widthIn(max = 400.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 38.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            listOf(
                    listOf(1, 2, 3),
                    listOf(4, 5, 6),
                    listOf(7, 8, 9),
                    listOf(null, 0, -1)
            ).forEach { row ->
                Row(horizontalArrangement = Arrangement.spacedBy(spacing.spaceSmall)) {
                    row.forEach { digit ->
                        val keyModifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                        if (digit == null) {
                            Spacer(keyModifier)
                        } else {
                            val isDelete = digit == -1
                            Surface(
                                    modifier = keyModifier.semantics { role = Role.Button },
                                    enabled = if (isDelete) pin.isNotEmpty() else pin.length < PIN_LENGTH,
                                    shape = RoundedCornerShape(32.dp),
                                    color = if (isDelete)
                                        MaterialTheme.colorScheme.primaryFixed.copy(alpha = 0.24f)
                                    else
                                        MaterialTheme.colorScheme.primaryFixed,
                                    contentColor = MaterialTheme.colorScheme.onPrimaryFixed,
                                    onClick = {
                                        if (isDelete) onPinChange(pin.dropLast(1))
                                        else if (pin.length < PIN_LENGTH) onPinChange(pin + digit)
                                    }
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    if (isDelete) {
                                        Icon(
                                                imageVector = Icons.AutoMirrored.Filled.Backspace,
                                                contentDescription = stringResource(R.string.cds_text_delete_pin_digit),
                                                modifier = Modifier.size(28.dp),
                                        )
                                    } else {
                                        Text(
                                                text = digit.toString(),
                                                style = MaterialTheme.typography.headlineLarge
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private const val PIN_LENGTH = 5
private val PIN_BOX_SIZE = 18.dp

@Preview(showBackground = true, widthDp = 400, heightDp = 800)
@Composable
private fun PinScreenPreview() {
    SpendLessTheme {
        PinScreen(pin = "123", onPinChange = {}, onBackClick = {})
    }
}
