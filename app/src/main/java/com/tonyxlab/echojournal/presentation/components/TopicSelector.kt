package com.tonyxlab.echojournal.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.presentation.ui.theme.EchoJournalTheme
import com.tonyxlab.echojournal.presentation.ui.theme.NeutralVariant30
import com.tonyxlab.echojournal.presentation.ui.theme.Primary95
import com.tonyxlab.echojournal.presentation.ui.theme.buttonSmallTextStyle
import com.tonyxlab.echojournal.presentation.ui.theme.spacing
import com.tonyxlab.echojournal.utils.TextFieldValue
import com.tonyxlab.echojournal.utils.conditionalModifier

@Composable
fun TopicSelector(
    topicFieldValue: TextFieldValue<String>,
    savedTopics: List<String>,
    onSavedTopicsChange: (List<String>) -> Unit,
    currentSelectedTopics: List<String>,
    onCurrentSelectedTopicsChange: (List<String>) -> Unit,
    modifier: Modifier = Modifier
) {

    var selectedTopics = currentSelectedTopics.toSet()

    var storedTopics = savedTopics.toSet()

    var isAddingTopic by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    Column(modifier = modifier.conditionalModifier(isAddingTopic) { fillMaxSize() }) {

        TopicsListing(
            modifier = modifier,
            selectedTopics = selectedTopics,
            onSelectTopic = {
                selectedTopics = it
                onCurrentSelectedTopicsChange(it.toList())
            },
            isAddingTopic = isAddingTopic,
            onAddTopic = { isAddingTopic = it },
            topicFieldValue = topicFieldValue,
            keyboardController = keyboardController,
            focusRequester = focusRequester

        )

        if (topicFieldValue.value.isEmpty()) return

        TopicDropDown(
            modifier = modifier,
            selectedTopics = selectedTopics,
            onSelectedTopicsChange = {
                selectedTopics = it
                onCurrentSelectedTopicsChange(it.toList())
            },
            onAddTopic = { isAddingTopic = it },
            topicFieldValue = topicFieldValue,
            savedTopics = storedTopics,
            onSavedTopicsChange = {
                storedTopics = it
                onSavedTopicsChange(it.toList())
            }
        )
    }
}


@Composable
private fun TopicsListing(
    selectedTopics: Set<String>,
    onSelectTopic: (Set<String>) -> Unit,
    isAddingTopic: Boolean,
    onAddTopic: (Boolean) -> Unit,
    topicFieldValue: TextFieldValue<String>,
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier,
    keyboardController: SoftwareKeyboardController? = null
) {

    TopicsFlowRow(
        modifier = modifier,
        selectedTopics = selectedTopics,
        onSelectTopic = onSelectTopic,
        isAddingTopic = isAddingTopic,
        onAddTopic = onAddTopic,
        topicFieldValue = topicFieldValue,
        keyboardController = keyboardController,
        focusRequester = focusRequester
    )
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TopicsFlowRow(
    selectedTopics: Set<String>,
    onSelectTopic: (Set<String>) -> Unit,
    isAddingTopic: Boolean,
    onAddTopic: (Boolean) -> Unit,
    topicFieldValue: TextFieldValue<String>,
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier,
    keyboardController: SoftwareKeyboardController? = null,
) {

    LaunchedEffect(key1 = isAddingTopic) {
        // Do not show keyboard when not adding a topic 
        if (isAddingTopic.not()) return@LaunchedEffect

        //show keyboard
        focusRequester.requestFocus()
        keyboardController?.show()

    }

    FlowRow(
        modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center
    ) {

        selectedTopics.forEach { topic ->

            TopicChip(modifier = Modifier.padding(end = MaterialTheme.spacing.spaceExtraSmall),
                topic = topic,
                hasTrailingIcon = true,
                onDeleteTopic = { onSelectTopic(selectedTopics - topic) })
        }

        // Show Icon when not typing
        if (!isAddingTopic) {

            IconButton(onClick = { onAddTopic(true) },
                modifier = modifier
                    .padding(vertical = MaterialTheme.spacing.spaceSmall)
                    .background(color = Primary95, shape = CircleShape)
                    .size(32.dp),
                content = {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null,
                        tint = NeutralVariant30,
                    )
                })

        }

        // Show TextField if typing
        else {

            BasicEntryTextField(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(top = MaterialTheme.spacing.spaceMedium)
                    .focusRequester(focusRequester),
                textFieldValue = topicFieldValue,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                ),
                hint = stringResource(id = R.string.topics_text),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onSelectTopic(selectedTopics + topicFieldValue.value)
                        onAddTopic(false)
                        topicFieldValue.onValueChange?.invoke("")
                        keyboardController?.hide()
                    })
            )
        }

    }
}

@Composable
fun TopicDropDown(
    selectedTopics: Set<String>,
    onSelectedTopicsChange: (Set<String>) -> Unit,
    onAddTopic: (Boolean) -> Unit,
    topicFieldValue: TextFieldValue<String>,
    savedTopics: Set<String>,
    modifier: Modifier = Modifier,
    onSavedTopicsChange: ((Set<String>) -> Unit)? = null,
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.spaceMedium)
            .shadow(
                elevation = MaterialTheme.spacing.spaceSmall, shape = MaterialTheme.shapes.medium
            )

    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
        ) {

            val matchingSavedTopics = savedTopics.filter {

                it.startsWith(
                    prefix = topicFieldValue.value,
                    ignoreCase = true
                ) && !selectedTopics.contains(
                    it
                )
            }

            matchingSavedTopics.forEach { topic ->

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onSelectedTopicsChange(selectedTopics + topic)
                        topicFieldValue.onValueChange?.invoke("")
                        onAddTopic(false)
                    }
                    .padding(
                        horizontal = MaterialTheme.spacing.spaceMedium,
                        vertical = MaterialTheme.spacing.spaceSmall
                    ), verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = topic,
                        style = buttonSmallTextStyle,
                        color = MaterialTheme.colorScheme.secondary
                    )

                }
            }

            // Show 'Create Chip' if topic does not exist

            if (!savedTopics.any { it.equals(topicFieldValue.value, ignoreCase = true) }) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val newTopic = topicFieldValue.value.trim()
                        onSavedTopicsChange?.invoke(savedTopics + newTopic)
                        onSelectedTopicsChange(selectedTopics + newTopic)
                        topicFieldValue.onValueChange?.invoke("")
                        onAddTopic(false)
                    }
                    .padding(
                        horizontal = MaterialTheme.spacing.spaceMedium,
                        vertical = MaterialTheme.spacing.spaceSmall
                    ),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceSmall),
                    verticalAlignment = Alignment.CenterVertically) {

                    AppIcon(
                        modifier = Modifier.size(MaterialTheme.spacing.spaceDoubleDp * 6),
                        imageVector = Icons.Default.Add,
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = "Create ${topicFieldValue.value}",
                        style = buttonSmallTextStyle,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

        }

    }
}


@PreviewLightDark
@Composable
private fun Preview() {
    EchoJournalTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(top = MaterialTheme.spacing.spaceExtraLarge),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)
        ) {
            TopicSelector(
                topicFieldValue = TextFieldValue(value = ""),
                modifier = Modifier
                    .background(Color.White)
                    .border(
                        width = MaterialTheme.spacing.spaceSingleDp,
                        color = MaterialTheme.colorScheme.primary
                    ),
                currentSelectedTopics = emptyList(),
                savedTopics = emptyList(),
                onSavedTopicsChange = {},
                onCurrentSelectedTopicsChange = {}
            )
        }
    }

}



