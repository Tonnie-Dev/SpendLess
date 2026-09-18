package dev.tonnie.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.tonnie.designsystem.theme.OnBackgroundStateLayer08
import dev.tonnie.designsystem.theme.SpendLessTheme
import dev.tonnie.designsystem.theme.spacing

@Composable
fun AppInputField(
    state: TextFieldState,
    placeholder: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    textAlign: TextAlign = TextAlign.Start,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    height: Dp = HEIGHT_SMALL,
    showFocusBorder: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    var isFocused by remember { mutableStateOf(false) }

    val isTextFieldEmpty = state.text.isEmpty()

    BasicTextField(
            modifier = modifier
                    .fillMaxWidth()
                    .height(height)
                    .onFocusChanged { isFocused = it.isFocused },
            state = state,
            textStyle = textStyle.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = textAlign
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            decorator = { innerTextField ->
                TextFieldDecorator(
                        isFocused = isFocused,
                        innerTextField = innerTextField,
                        placeholder = placeholder,
                        isTextFieldEmpty = isTextFieldEmpty,
                        showFocusBorder = showFocusBorder,
                        textAlign = textAlign,
                        textStyle = textStyle,
                        backgroundColor = backgroundColor,
                        modifier = modifier
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}

@Composable
private fun TextFieldDecorator(
    innerTextField: @Composable () -> Unit,
    isFocused: Boolean,
    showFocusBorder: Boolean,
    placeholder: String,
    isTextFieldEmpty: Boolean,
    backgroundColor: Color,
    textAlign: TextAlign,
    textStyle: TextStyle,
    modifier: Modifier = Modifier
) {
    Box(
            modifier = modifier
                    .fillMaxWidth()
                    .background(
                            color = backgroundColor,
                            shape = MaterialTheme.shapes.large
                    )
                    .then(

                            if (showFocusBorder && isFocused) {
                                Modifier.border(
                                        width = MaterialTheme.spacing.spaceSingleDp,
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = MaterialTheme.shapes.large
                                )
                            } else {
                                Modifier
                            }
                    )

                    .padding(horizontal = MaterialTheme.spacing.spaceMedium)
                    .padding(vertical = MaterialTheme.spacing.spaceTwelve),
            contentAlignment = when (textAlign) {

                TextAlign.Center -> Alignment.Center
                TextAlign.End -> Alignment.CenterEnd
                else -> Alignment.CenterStart
            }
    ) {
        if (isTextFieldEmpty/* && !isFocused*/) {
            Text(
                    text = placeholder,
                    style = textStyle.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = textAlign
                    )
            )
        } else {
            innerTextField()
        }
    }
}

private val HEIGHT_SMALL = 48.dp
private val HEIGHT_LARGE = 64.dp

@Preview(
        name = "App Input Field Variants",
        showBackground = true
)
@Composable
private fun AppInputFieldPreview() {

    SpendLessTheme {
        Column(
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            // Login
            AppInputField(
                    state = rememberTextFieldState(),
                    placeholder = "Username",
                    showFocusBorder = true,
                    height = HEIGHT_SMALL
            )

            // Registration
            AppInputField(
                    state = rememberTextFieldState(),
                    placeholder = "username",
                    textAlign = TextAlign.Center,
                    textStyle = MaterialTheme.typography.headlineMedium,
                    height = HEIGHT_LARGE,
                    backgroundColor = OnBackgroundStateLayer08
            )

            // Transaction type
            AppInputField(
                    state = rememberTextFieldState(),
                    placeholder = "Receiver",
                    textAlign = TextAlign.Center,
                    backgroundColor = Color.Transparent,
                    height = HEIGHT_SMALL
            )
        }
    }
}