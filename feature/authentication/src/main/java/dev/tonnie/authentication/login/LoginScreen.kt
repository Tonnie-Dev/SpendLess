package dev.tonnie.authentication.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.tonnie.authentication.R
import dev.tonnie.authentication.login.handling.LoginUiEvent
import dev.tonnie.authentication.login.handling.LoginUiState
import dev.tonnie.designsystem.components.AppButton
import dev.tonnie.designsystem.components.AppInputField
import dev.tonnie.designsystem.icon.AppIcon
import dev.tonnie.designsystem.theme.SpendLessTheme
import dev.tonnie.designsystem.theme.spacing
import dev.tonnie.presentation.BaseContentLayout
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    onNavigateToDashboard: () -> Unit,
    onNavigateToRegistration: () -> Unit,
    viewModel: LoginViewModel = koinViewModel()
) {

    BaseContentLayout(viewModel = viewModel) {

        uiState ->

        LoginScreenContent(
                uiState = uiState,
                onEvent = viewModel::onEvent
        )
    }
}

@Composable
private fun LoginScreenContent(
    uiState: LoginUiState,
    onEvent: (LoginUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val spacing = MaterialTheme.spacing

    val usernamePlaceholderText = stringResource(R.string.placeholder_text_username)
            .replaceFirstChar(Char::uppercaseChar)

    Column(
            modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .imePadding()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = spacing.spaceMedium)
                    .padding(top = spacing.spaceLargeMedium),
            horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        AppIcon(modifier = Modifier.padding(bottom = spacing.spaceMedium))

        Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(spacing.spaceSmall)
        ) {
            Text(
                    text = stringResource(R.string.header_text_welcome_back),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
            )

            Text(
                    text = stringResource(R.string.caption_text_enter_login_details),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
            )
        }

        Spacer(Modifier.height(36.dp))

        Column(
                modifier = Modifier.padding(bottom = MaterialTheme.spacing.spaceTwelve * 2),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
        ) {

            AppInputField(
                    state = uiState.usernameTextFieldState,
                    placeholder = usernamePlaceholderText,
                    modifier = Modifier.shadow(4.dp, MaterialTheme.shapes.large),
                    textStyle = MaterialTheme.typography.bodyMedium,
                    backgroundColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                    showFocusBorder = true,
                    errorMessageRes = uiState.usernameErrorRes
            )

            AppInputField(
                    state = uiState.pinTextFieldState,
                    placeholder = stringResource(R.string.placeholder_text_pin),
                    modifier = Modifier.shadow(4.dp, MaterialTheme.shapes.large),
                    textStyle = MaterialTheme.typography.bodyMedium,
                    backgroundColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                    showFocusBorder = true,
                    keyboardType = KeyboardType.NumberPassword,
                    isPassword = true,
                    errorMessageRes = uiState.pinErrorRes
            )
        }

        AppButton(
                modifier = Modifier.padding(bottom = spacing.spaceLarge),
                buttonText = stringResource(R.string.button_text_login),
                enabled = uiState.loginButtonEnabled,
                onClick = { onEvent(LoginUiEvent.Login) },
        )

        TextButton(
                onClick = { onEvent(LoginUiEvent.Register) }
        ) {
            Text(
                    text = stringResource(R.string.text_button_new_user),
                    style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginScreenContentPreview() {
    SpendLessTheme {
        LoginScreenContent(
                modifier = Modifier,
                uiState = LoginUiState(),
                onEvent = {}
        )
    }
}
