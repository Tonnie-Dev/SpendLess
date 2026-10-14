package dev.tonnie.dashboard.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.tonnie.dashboard.R
import dev.tonnie.dashboard.screen.handling.DashboardActionEvent
import dev.tonnie.dashboard.screen.handling.DashboardUiEvent
import dev.tonnie.dashboard.screen.handling.DashboardUiState
import dev.tonnie.designsystem.theme.HorizontalRoundedCornerShape16
import dev.tonnie.designsystem.theme.RoundedCornerShape20
import dev.tonnie.designsystem.theme.SpendLessTheme
import dev.tonnie.designsystem.theme.spacing
import dev.tonnie.designsystem.utils.SetStatusBarIconsColor
import dev.tonnie.presentation.base.BaseContentLayout
import dev.tonnie.presentation.formatting.CurrencyFormatter
import org.koin.androidx.compose.koinViewModel

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = koinViewModel(),
    onNavigateToPin: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToAddTransaction: () -> Unit,
    onNavigateToExport: () -> Unit
) {

    SetStatusBarIconsColor(darkIcons = false)

    BaseContentLayout(
            viewModel = viewModel,
            actionEventHandler = { context, actionEvent ->

                when (actionEvent) {
                    DashboardActionEvent.NavigateToSetting -> onNavigateToSettings()
                    DashboardActionEvent.NavigateToAddTransaction -> onNavigateToAddTransaction()
                    DashboardActionEvent.NavigateToExport -> onNavigateToExport()
                    DashboardActionEvent.NavigateToPin -> onNavigateToPin()
                    DashboardActionEvent.ShowToast -> {
                        Toast.makeText(context, "Session Active", Toast.LENGTH_SHORT).show()
                    }

                }
            },
            floatingActionButton = {

                FloatingActionButton(
                        modifier = Modifier.navigationBarsPadding(),
                        onClick = { viewModel.onEvent(DashboardUiEvent.AddTransaction) },
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        shape = MaterialTheme.shapes.RoundedCornerShape20,
                        content = {
                            Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = stringResource(R.string.cds_text_add_transaction)
                            )
                        }
                )
            }) { uiState ->
        DashboardScreenContent(
                modifier = Modifier,
                uiState = uiState,
                onEvent = viewModel::onEvent
        )
    }
}

@Composable
fun DashboardScreenContent(
    modifier: Modifier = Modifier,
    uiState: DashboardUiState,
    onEvent: (DashboardUiEvent) -> Unit
) {
    val spacing = MaterialTheme.spacing
    val colors = MaterialTheme.colorScheme
    val currencyFormatter = CurrencyFormatter()
    BoxWithConstraints(
            modifier = modifier
                    .fillMaxSize()
                    .drawBehind {
                        drawRect(
                                Brush.radialGradient(
                                        colors = listOf(colors.primary, colors.onPrimaryFixed),
                                        center = Offset.Zero,
                                        radius = size.width * 1.15f,
                                )
                        )
                    },
    ) {

        val overviewHeight = (maxHeight * 0.5f)
                .coerceAtLeast(minimumValue = MINIMUM_SECTION_HEIGHT)

        val transactionsHeight = (maxHeight - overviewHeight)
                .coerceAtLeast(minimumValue = MINIMUM_SECTION_HEIGHT)

        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
        ) {
            Column(
                    modifier = Modifier
                            .fillMaxWidth()
                            .height(overviewHeight)
                            .statusBarsPadding()
                            .padding(all = spacing.spaceMedium),
            ) {
                Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                            modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 0.dp),
                            text = uiState.username,
                            style = MaterialTheme.typography.titleLarge,
                            color = colors.onPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(spacing.spaceSmall)) {
                        IconButton(
                                modifier = Modifier
                                        .size(DASHBOARD_ICON_SIZE)
                                        .background(
                                                colors.onPrimary.copy(alpha = 0.12f),
                                                shape = MaterialTheme.shapes.large
                                        ),
                                onClick = { onEvent(DashboardUiEvent.ExportData) }
                        ) {
                            Icon(
                                    painter = painterResource(R.drawable.ic_export),
                                    contentDescription = stringResource(R.string.cds_text_export),
                                    tint = colors.onPrimary
                            )
                        }
                        IconButton(
                                modifier = Modifier
                                        .size(DASHBOARD_ICON_SIZE)
                                        .background(
                                                colors.onPrimary.copy(alpha = 0.12f),
                                                shape = MaterialTheme.shapes.large
                                        ),
                                onClick = { onEvent(DashboardUiEvent.OpenSettings) }
                        ) {
                            Icon(
                                    painter = painterResource(R.drawable.ic_settings),
                                    contentDescription = stringResource(R.string.cds_text_settings),
                                    tint = colors.onPrimary
                            )
                        }
                    }
                }

                Column(
                        modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                            text = currencyFormatter.format(uiState.accountBalance),
                            style = MaterialTheme.typography.displayLarge,
                            color = colors.onPrimary,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                            text = stringResource(R.string.caption_text_account_balance),
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.onPrimary.copy(alpha = 0.7f),
                    )
                }

                DashboardSummary(
                        previousWeekAmount = currencyFormatter
                                .format(uiState.previousWeekAmount)
                )
            }
            Box(
                    modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = transactionsHeight)
                            .background(
                                    color = colors.background,
                                    shape = MaterialTheme.shapes.HorizontalRoundedCornerShape16
                            ),
               //     .navigationBarsPadding(),
                    contentAlignment = Alignment.Center,
            ) {
                Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                            painter = painterResource(R.drawable.ic_winged_notes),
                            contentDescription = null,
                            modifier = Modifier.size(100.dp),
                    )

                    Spacer(Modifier.height(16.dp))

                    Text(
                            text = stringResource(R.string.caption_text_no_transaction),
                            style = MaterialTheme.typography.titleLarge,
                            color = colors.onBackground,
                            textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

private val MINIMUM_SECTION_HEIGHT = 300.dp
private val DASHBOARD_ICON_SIZE = 48.dp

@Composable
private fun DashboardSummary(previousWeekAmount: String) {

    val spacing = MaterialTheme.spacing
    val colors = MaterialTheme.colorScheme

    Row(
            modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(spacing.spaceSmall),
    ) {
        Box(
                modifier = Modifier
                        .weight(.65f)
                        .fillMaxHeight()
                        .background(
                                color = colors.primaryFixed,
                                shape = MaterialTheme.shapes.large
                        )
                        .padding(all = spacing.spaceTwelve),
                contentAlignment = Alignment.Center,
        ) {
            Text(
                    text = stringResource(R.string.caption_text_largest_transaction),
                    style = MaterialTheme.typography.titleMedium,
                    color = colors.onSurface,
                    textAlign = TextAlign.Center,
            )
        }
        Column(
                modifier = Modifier
                        .weight(.35f)
                        .fillMaxHeight()
                        .background(
                                color = colors.secondaryFixed,
                                shape = MaterialTheme.shapes.large
                        )
                        .padding(all = spacing.spaceTwelve),
                verticalArrangement = Arrangement.Center,
        ) {
            Text(
                    text = previousWeekAmount,
                    style = MaterialTheme.typography.titleLarge,
                    color = colors.onSurface,
            )
            Text(
                    text = stringResource(R.string.caption_text_previous_week),
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.onSurface,
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 892)
@Composable
private fun DashboardScreenPreview() {
    SpendLessTheme {
        DashboardScreenContent(
                uiState = DashboardUiState(
                        username = "Ein",
                        accountBalance = 1000.286,
                        previousWeekAmount = 10245.0
                ),
                onEvent = {}
        )
    }
}
