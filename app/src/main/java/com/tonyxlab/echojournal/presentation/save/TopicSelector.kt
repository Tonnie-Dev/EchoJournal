package com.tonyxlab.echojournal.presentation.save

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.presentation.components.AppIcon
import com.tonyxlab.echojournal.presentation.ui.theme.NeutralVariant10
import com.tonyxlab.echojournal.presentation.ui.theme.NeutralVariant30
import com.tonyxlab.echojournal.presentation.ui.theme.Primary95
import com.tonyxlab.echojournal.presentation.ui.theme.Secondary90
import com.tonyxlab.echojournal.presentation.ui.theme.spacing

@Composable
private fun TopicsListing(modifier: Modifier = Modifier) {

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

            IconButton(onClick = {},
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
    }

}

@Composable
fun ChipFlowRow(
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
        focusRequester.requestFocus()
        keyboardController?.show()

    }

    // Show Icon when not typing

    if (!isAddingTopic) {

        IconButton(onClick = {},
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
                onAddTopic(false)
                keyboardController?.hide()
            })
        )
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
            selectedContainerColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        leadingIcon = {
            AppIcon(
                modifier = Modifier.size(MaterialTheme.spacing.spaceSmall),
                imageVector = Icons.Outlined.Info,
                tint = MaterialTheme.colorScheme.inverseOnSurface
            )
        }, label = {

            Text(text = topic, style = m)
        }
    )
}


