@file:OptIn(ExperimentalMaterial3Api::class)

package com.tonyxlab.echojournal.presentation.screens.editor.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.domain.model.Mood
import com.tonyxlab.echojournal.presentation.core.components.MoodRow
import com.tonyxlab.echojournal.presentation.core.utils.spacing
import com.tonyxlab.echojournal.presentation.screens.editor.handling.EditorUiEvent
import com.tonyxlab.echojournal.presentation.screens.editor.handling.EditorUiState

@Composable
fun EditorBottomSheet(
    editorSheetState: EditorUiState.EditorSheetState,
    onEvent: (EditorUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState()
    val isPrimaryButtonEnabled by remember {
        derivedStateOf {
            editorSheetState.activeMood != Mood.Undefined
        }
    }

    if (editorSheetState.isSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = { onEvent(EditorUiEvent.BottomSheetClosed) },
            sheetState = sheetState
        ) {

            Column(
                modifier = modifier.padding(MaterialTheme.spacing.spaceMedium),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceDoubleDp * 14)
            ) {
                // Sheet Title
                Text(
                    text = stringResource(id = R.string.text_how_are_you_doing),
                    style = MaterialTheme.typography.titleMedium
                )

                // Mood Row
                MoodRow(
                    moods = editorSheetState.moods,
                    activeMood = editorSheetState.activeMood,
                    onMoodClick = { onEvent(EditorUiEvent.MoodSelected(it)) }
                )

                // Buttons
                EditorBottomButtons(
                    primaryButtonText = stringResource(R.string.text_confirm),
                    onCancelClick = { onEvent(EditorUiEvent.BottomSheetClosed) },
                    onConfirmClick = { onEvent(EditorUiEvent.SheetConfirmClicked(editorSheetState.activeMood)) },
                    primaryButtonEnabled = isPrimaryButtonEnabled,
                    primaryLeadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = if (isPrimaryButtonEnabled)
                                MaterialTheme.colorScheme.onPrimary
                            else
                                MaterialTheme.colorScheme.outline
                        )
                    }
                )
            }
        }
    }
}