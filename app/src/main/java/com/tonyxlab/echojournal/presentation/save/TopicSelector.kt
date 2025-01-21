package com.tonyxlab.echojournal.presentation.save

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tonyxlab.echojournal.R
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
            ),
        color = Secondary90
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

            IconButton(
                onClick = {},
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
                }
            )
        }
    }

}