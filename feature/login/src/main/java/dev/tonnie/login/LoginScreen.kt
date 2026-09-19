package dev.tonnie.login

import androidx.compose.foundation.background
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

/** Apply system-bar insets through [modifier], as with the other onboarding screens. */
@Composable
fun LoginScreen(
    usernameState: TextFieldState,
    pinState: TextFieldState,
    onLoginClick: (username: String, pin: String) -> Unit,
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.error)
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
            .padding(top = 32.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AppIcon()
        Spacer(Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.header_text_welcome_back),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.caption_text_enter_login_details),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(36.dp))
        AppInputField(
            state = usernameState,
            placeholder = stringResource(R.string.placeholder_text_username),
            modifier = Modifier.shadow(4.dp, MaterialTheme.shapes.large),
            textStyle = MaterialTheme.typography.bodyMedium,
            backgroundColor = MaterialTheme.colorScheme.surfaceContainerLowest,
            showFocusBorder = true,
        )
        Spacer(Modifier.height(16.dp))
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
        Spacer(Modifier.height(24.dp))
        AppButton(
            buttonText = stringResource(R.string.button_text_login),
            onClick = { onLoginClick(usernameState.text.toString().trim(), pinState.text.toString()) },
        )
        Spacer(Modifier.height(32.dp))
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
