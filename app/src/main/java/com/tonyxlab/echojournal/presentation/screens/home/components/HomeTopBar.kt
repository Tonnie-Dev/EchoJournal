package com.tonyxlab.echojournal.presentation.screens.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.presentation.core.utils.spacing

@Composable
fun HomeTopBar(title:String,  onSettingsClick:()-> Unit,modifier: Modifier = Modifier) {

    Row(modifier = modifier
        .fillMaxWidth()
        .height(MaterialTheme.spacing.spaceMedium * 3)
        .padding(horizontal = MaterialTheme.spacing.spaceMedium),
        verticalAlignment = Alignment.CenterVertically) {

        Text(text = title,
            style = MaterialTheme.typography.titleMedium)

        IconButton(onClick = onSettingsClick) {

            Icon(
                painter = painterResource(R.drawable.ic_settings),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.secondary
            )
        }
    }
}