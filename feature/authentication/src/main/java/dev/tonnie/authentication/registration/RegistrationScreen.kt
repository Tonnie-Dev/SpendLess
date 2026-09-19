package dev.tonnie.authentication.registration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.tonnie.designsystem.components.AppButton
import dev.tonnie.designsystem.components.AppInputField
import dev.tonnie.designsystem.icon.AppIcon
import dev.tonnie.designsystem.theme.OnBackgroundStateLayer08
import dev.tonnie.designsystem.theme.SpendLessTheme
import dev.tonnie.designsystem.theme.spacing
import dev.tonnie.authentication.R

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    usernameState: TextFieldState,
    onNextClick: (String) -> Unit,
    onSignInClick: () -> Unit,
) {
    val spacing = MaterialTheme.spacing
    val username = usernameState.text.toString()
            .trim()

    Column(
            modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    //  .safeDrawingPadding()
                    // .imePadding()
                    .padding(horizontal = HORIZONTAL_PADDING),

            horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        AppIcon(modifier = Modifier.padding(top = spacing.spaceLarge, bottom = spacing.spaceTwenty))

        Column(
                modifier = Modifier.padding(bottom = spacing.spaceLarge),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(spacing.spaceSmall)
        ) {
            Text(
                    text = stringResource(R.string.header_text_welcome),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
            )

            Text(
                    text = stringResource(R.string.caption_text_create_name),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
            )
        }

        Column(
                modifier = Modifier.padding(bottom = spacing.spaceLarge),
                verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
        ) {
            AppInputField(
                    state = usernameState,
                    placeholder = stringResource(R.string.placeholder_text_username),
                    textStyle = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    backgroundColor = OnBackgroundStateLayer08,
                    height = spacing.spaceExtraLarge,
            )

            AppButton(
                    buttonText = stringResource(R.string.button_text_next),
                    enabled = username.isNotBlank(),
                    trailingIcon = Icons.AutoMirrored.Filled.ArrowRight,
                    onClick = { onNextClick(username) },
            )
        }

        TextButton(onClick = onSignInClick) {
            Text(
                    text = stringResource(R.string.text_button_account_ready),
                    style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

private val HORIZONTAL_PADDING = 26.dp

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RegistrationScreenPreview() {
    SpendLessTheme {
        RegistrationScreen(
                usernameState = rememberTextFieldState(),
                onNextClick = {},
                onSignInClick = {}
        )
    }
}
