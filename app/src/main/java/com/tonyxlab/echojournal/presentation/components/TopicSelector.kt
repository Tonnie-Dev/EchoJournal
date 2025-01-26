package com.tonyxlab.echojournal.presentation.components

import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.presentation.ui.theme.NeutralVariant10
import com.tonyxlab.echojournal.presentation.ui.theme.NeutralVariant30
import com.tonyxlab.echojournal.presentation.ui.theme.Primary95
import com.tonyxlab.echojournal.presentation.ui.theme.Secondary90
import com.tonyxlab.echojournal.presentation.ui.theme.buttonSmallTextStyle
import com.tonyxlab.echojournal.presentation.ui.theme.spacing

@Composable
fun TopicSelector(modifier: Modifier = Modifier) {
    var selectedTopics by remember { mutableStateOf(setOf<String>()) }
    var savedTopics by remember {
        mutableStateOf(
            setOf(
                "Work",
                "Hobby",
                "Personal",
                "Office",
                "Workout"
            )
        )
    }
    var isAddingTopic by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    Column(modifier = Modifier.fillMaxSize()) {

        TopicsListing(
            modifier = modifier,
            selectedTopics = selectedTopics,
            onSelectTopic = { selectedTopics = it },
            isAddingTopic = isAddingTopic,
            onAddTopic = { isAddingTopic = it },
            searchQuery = searchQuery,
            onSearch = { searchQuery = it },
            keyboardController = keyboardController,
            focusRequester = focusRequester

        )

        if (searchQuery.isBlank()) return

        TopicDropDown(modifier = modifier,
            selectedTopics = selectedTopics,
            onSelectedTopicsChange = { selectedTopics = it },
            onAddTopic = { isAddingTopic = it },
            searchQuery = searchQuery,
            onSearchQueryChange = { searchQuery = it },
            savedTopics = savedTopics,
            onSavedTopicsChange = { savedTopics = it })
    }
}


@Composable
private fun TopicsListing(
    selectedTopics: Set<String>,
    onSelectTopic: (Set<String>) -> Unit,
    isAddingTopic: Boolean,
    onAddTopic: (Boolean) -> Unit,
    searchQuery: String,
    onSearch: (String) -> Unit,
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier,
    keyboardController: SoftwareKeyboardController? = null
) {

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = MaterialTheme.spacing.spaceSmall,
                shape = MaterialTheme.shapes.medium,
            ), color = Secondary90
    ) {

        Column(
            Modifier.padding(MaterialTheme.spacing.spaceMedium),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)
        ) {

            Text(
                text = stringResource(R.string.topics_text),
                style = MaterialTheme.typography.titleMedium,
                color = NeutralVariant10,
            )

            TopicsFlowRow(
                selectedTopics = selectedTopics,
                onSelectTopic = onSelectTopic,
                isAddingTopic = isAddingTopic,
                onAddTopic = onAddTopic,
                searchQuery = searchQuery,
                onSearch = onSearch,
                keyboardController = keyboardController,
                focusRequester = focusRequester
            )
        }
    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TopicsFlowRow(
    selectedTopics: Set<String>,
    onSelectTopic: (Set<String>) -> Unit,
    isAddingTopic: Boolean,
    onAddTopic: (Boolean) -> Unit,
    searchQuery: String,
    onSearch: (String) -> Unit,
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
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {

        selectedTopics.forEach { topic ->

            TopicChip(modifier = Modifier.padding(end = MaterialTheme.spacing.spaceExtraSmall),
                topic = topic,
                onCancel = { onSelectTopic(selectedTopics - topic) })


        }

        // Show Icon when not typing
        if (!isAddingTopic) {

            IconButton(
                onClick = { onAddTopic(true) },
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

            BasicTextField(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(top = MaterialTheme.spacing.spaceMedium)
                    .focusRequester(focusRequester),
                value = searchQuery,
                onValueChange = onSearch,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    onSelectTopic(selectedTopics + searchQuery)
                    onAddTopic(false)
                    onSearch("")
                    keyboardController?.hide()
                })
            )
        }
    }

}


@Composable
fun TopicChip(
    topic: String,
    modifier: Modifier = Modifier,
    onCancel: (() -> Unit)? = null
) {

    FilterChip(
        modifier = modifier,
        selected = true,
        onClick = {},
        shape = MaterialTheme.shapes.large,
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = Primary95
        ),
        leadingIcon = {
            AppIcon(
                modifier = Modifier.size(MaterialTheme.spacing.spaceSmall),
                imageVector = Icons.Outlined.Info,
                tint = MaterialTheme.colorScheme.inverseOnSurface
            )
        },
        trailingIcon = {

            AppIcon(
                modifier = Modifier
                    .size(MaterialTheme.spacing.spaceSmall)
                    .clickable { onCancel?.invoke() },
                imageVector = Icons.Outlined.Info,
                contentDescription = stringResource(R.string.text_cancel),
                tint = MaterialTheme.colorScheme.inverseOnSurface
            )

        },

        label = {

            Text(
                text = topic,
                style = buttonSmallTextStyle,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        })
}


@Composable
fun TopicDropDown(
    selectedTopics: Set<String>,
    onSelectedTopicsChange: (Set<String>) -> Unit,
    onAddTopic: (Boolean) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    savedTopics: Set<String>,
    modifier: Modifier = Modifier,
    onSavedTopicsChange: ((Set<String>) -> Unit)? = null,
) {
    Box(
        modifier = modifier

            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.spaceMedium)
            .shadow(
                elevation = MaterialTheme.spacing.spaceSmall,
                shape = MaterialTheme.shapes.medium
            )

    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
        ) {

            val matchingSavedTopics = savedTopics.filter {

                it.startsWith(prefix = searchQuery, ignoreCase = true)
                        && !selectedTopics.contains(it)
            }

            matchingSavedTopics.forEach { topic ->

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onSelectedTopicsChange(selectedTopics + topic)
                        onSearchQueryChange("")
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

            if (!savedTopics.any { it.equals(searchQuery, ignoreCase = true) }) {

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {

                        val newTopic = searchQuery.trim()
                        onSavedTopicsChange?.invoke(savedTopics + newTopic)
                        onSelectedTopicsChange(selectedTopics + newTopic)
                        onSearchQueryChange("")
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
                        text = "Create $searchQuery",
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

    TopicSelector()


}



