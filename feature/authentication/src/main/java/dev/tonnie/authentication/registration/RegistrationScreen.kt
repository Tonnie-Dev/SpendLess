package dev.tonnie.authentication.registration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
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
import dev.tonnie.authentication.R
import dev.tonnie.authentication.registration.handling.RegistrationUiEvent
import dev.tonnie.authentication.registration.handling.RegistrationUiState
import dev.tonnie.designsystem.components.AppButton
import dev.tonnie.designsystem.components.AppInputField
import dev.tonnie.designsystem.icon.AppIcon
import dev.tonnie.designsystem.theme.OnBackgroundStateLayer08
import dev.tonnie.designsystem.theme.SpendLessTheme
import dev.tonnie.designsystem.theme.spacing
import dev.tonnie.presentation.BaseContentLayout
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = koinViewModel()
) {
    BaseContentLayout(viewModel = viewModel) {
        RegistrationScreenContent(
                uiState = it,
                uiEvent = viewModel::onEvent
        )
    }
}

@Composable
fun RegistrationScreenContent(
    modifier: Modifier = Modifier,
    uiState: RegistrationUiState,
    uiEvent: (RegistrationUiEvent) -> Unit,
) {
    val spacing = MaterialTheme.spacing

    Column(
            modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .safeDrawingPadding()
                    .imePadding()
                    .padding(horizontal = HORIZONTAL_PADDING)
                    .padding(top = spacing.spaceLarge),

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
                    state = uiState.usernameTextFieldState,
                    placeholder = stringResource(R.string.placeholder_text_username),
                    textStyle = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    backgroundColor = OnBackgroundStateLayer08,
                    height = spacing.spaceExtraLarge,
            )

            AppButton(
                    buttonText = stringResource(R.string.button_text_next),
                    enabled = uiState.nextButtonEnabled,
                    trailingIcon = Icons.AutoMirrored.Filled.ArrowRight,
                    onClick = { uiEvent(RegistrationUiEvent.NextClicked) },
            )
        }

        TextButton(onClick = { uiEvent(RegistrationUiEvent.SignInClicked) }) {
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
private fun RegistrationScreenContentPreview() {
    SpendLessTheme {
        RegistrationScreenContent(
                uiState = RegistrationUiState(),
                uiEvent = {}
        )
    }
}
