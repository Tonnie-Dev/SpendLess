package dev.tonnie.authentication.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.tonnie.authentication.R
import dev.tonnie.authentication.login.handling.LoginActionEvent
import dev.tonnie.authentication.login.handling.LoginUiEvent
import dev.tonnie.authentication.login.handling.LoginUiState
import dev.tonnie.designsystem.components.AppButton
import dev.tonnie.designsystem.components.AppErrorBanner
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

    BaseContentLayout(
            viewModel = viewModel,
            actionEventHandler = { _, actionEvent ->

                when (actionEvent) {
                    LoginActionEvent.NavigateToDashboard -> {
                        onNavigateToDashboard()
                    }

                    LoginActionEvent.NavigateToRegistration -> {
                        onNavigateToRegistration()
                    }
                }

            }
    ) { uiState ->
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

    val loginErrorState = uiState.loginErrorState
    val showErrorBanner = with(loginErrorState) {
        unknownError || accountNotFoundError || invalidCredentialsError
    }

    val usernameFocusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        usernameFocusRequester.requestFocus()
        keyboardController?.show()
    }

    Box(
            modifier = modifier

                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .safeDrawingPadding()
                    .imePadding()
                    .verticalScroll(rememberScrollState())

    ) {
        Column(
                modifier = Modifier
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
                        modifier = Modifier
                                .focusRequester(usernameFocusRequester)
                                .shadow(4.dp, MaterialTheme.shapes.large),
                        state = uiState.usernameTextFieldState,
                        placeholder = usernamePlaceholderText,
                        textStyle = MaterialTheme.typography.bodyMedium,
                        backgroundColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                        showFocusBorder = true,
                        errorMessageRes = uiState.usernameErrorRes,
                        imeAction = ImeAction.Next
                )

                AppInputField(
                        modifier = Modifier.shadow(4.dp, MaterialTheme.shapes.large),
                        state = uiState.pinTextFieldState,
                        placeholder = stringResource(R.string.placeholder_text_pin),
                        textStyle = MaterialTheme.typography.bodyMedium,
                        backgroundColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                        showFocusBorder = true,
                        keyboardType = KeyboardType.NumberPassword,
                        isPassword = true,
                        errorMessageRes = uiState.pinErrorRes,
                        imeAction = ImeAction.Done,
                        onKeyboardAction = { onEvent(LoginUiEvent.Login) }
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

        AnimatedVisibility(
                modifier = Modifier.align(Alignment.BottomCenter),
                visible = showErrorBanner,
                enter = slideInVertically { it },
                exit = slideOutVertically { it }
        ) {

            AppErrorBanner(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    text = when {
                        loginErrorState.invalidCredentialsError -> stringResource(R.string.banner_text_invalid_credentials)
                        loginErrorState.accountNotFoundError -> stringResource(R.string.banner_text_no_account_found)
                        else -> stringResource(id = R.string.banner_text_generic_error)
                    }
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
