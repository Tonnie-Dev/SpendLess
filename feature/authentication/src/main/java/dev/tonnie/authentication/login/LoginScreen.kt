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
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
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
import dev.tonnie.designsystem.components.AppButton
import dev.tonnie.designsystem.components.AppInputField
import dev.tonnie.designsystem.icon.AppIcon
import dev.tonnie.designsystem.theme.SpendLessTheme
import dev.tonnie.designsystem.theme.spacing
import dev.tonnie.authentication.R

/** Apply system-bar insets through [modifier], as with the other onboarding screens. */
@Composable
fun LoginScreen(
    usernameState: TextFieldState,
    pinState: TextFieldState,
    onLoginClick: (username: String, pin: String) -> Unit,
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val spacing = MaterialTheme.spacing
    Column(
            modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .imePadding()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = spacing.spaceMedium),
            horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AppIcon()
        Spacer(Modifier.height(20.dp))
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
                    state = usernameState,
                    placeholder = stringResource(R.string.placeholder_text_username),
                    modifier = Modifier.shadow(4.dp, MaterialTheme.shapes.large),
                    textStyle = MaterialTheme.typography.bodyMedium,
                    backgroundColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                    showFocusBorder = true,
            )

            AppInputField(
                    state = pinState,
                    placeholder = stringResource(R.string.placeholder_text_pin),
                    modifier = Modifier.shadow(4.dp, MaterialTheme.shapes.large),
                    textStyle = MaterialTheme.typography.bodyMedium,
                    backgroundColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                    showFocusBorder = true,
                    keyboardType = KeyboardType.NumberPassword,
                    isPassword = true,
            )
        }

            AppButton(
                    modifier = Modifier.padding(bottom = spacing.spaceLarge),
                    buttonText = stringResource(R.string.button_text_login),
                    onClick = {
                        onLoginClick(
                                usernameState.text.toString()
                                        .trim(), pinState.text.toString()
                        )
                    },
            )

        TextButton(onClick = onSignUpClick) {
            Text(
                    text = stringResource(R.string.text_button_new_user),
                    style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    SpendLessTheme {
        LoginScreen(
                usernameState = rememberTextFieldState(),
                pinState = rememberTextFieldState(),
                onLoginClick = { _, _ -> },
                onSignUpClick = {},
        )
    }
}
