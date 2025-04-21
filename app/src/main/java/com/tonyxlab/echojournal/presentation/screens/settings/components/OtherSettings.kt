package com.tonyxlab.echojournal.presentation.screens.settings.components


import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.PhonelinkSetup
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.net.toUri
import com.tonyxlab.echojournal.BuildConfig
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.presentation.core.utils.LocalSpacing
import com.tonyxlab.echojournal.utils.Constants.APP_CONTACT_EMAIL_ADDRESS
import com.tonyxlab.echojournal.utils.Constants.APP_SHARE_MESSAGE
import com.tonyxlab.echojournal.utils.Constants.APP_URL
import com.tonyxlab.echojournal.utils.Constants.ISSUE_EMAIL_BODY
import com.tonyxlab.echojournal.utils.Constants.ISSUE_EMAIL_SUBJECT

@Composable
fun OtherSettings(modifier: Modifier = Modifier) {

    val context = LocalContext.current

    SettingsContainer(
        modifier = modifier,
        title = stringResource(R.string.header_other_settings)
    ) {

        SettingsItem(
            title = stringResource(id = R.string.text_share_app),
            subTitle = stringResource(id = R.string.text_invite_friends),
            icon = Icons.Default.Share
        ) {
            context.startActivity(
                Intent.createChooser(
                    Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, APP_SHARE_MESSAGE)
                        type = "text/plain"
                    }, null
                )
            )

        }

        SettingsItem(
            title = stringResource(id = R.string.text_report_issue),
            subTitle = stringResource(id = R.string.text_help_us),
            icon = Icons.Default.BugReport
        ) {

            val uri = ("mailto:$APP_CONTACT_EMAIL_ADDRESS" +
                    "?subject=${Uri.encode(ISSUE_EMAIL_SUBJECT)}" +
                    "&body=${Uri.encode(ISSUE_EMAIL_BODY)}").toUri()

            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = uri
            }

            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            } else {
                Toast.makeText(context, "No email app found.", Toast.LENGTH_SHORT).show()
            }
        }

        SettingsItem(
            title = stringResource(id = R.string.text_rate_us),
            subTitle = stringResource(id = R.string.text_give_feedback),
            icon = Icons.Default.StarRate
        ) {
            context.startActivity(
                Intent.createChooser(
                    Intent(
                        Intent.ACTION_VIEW, APP_URL.toUri()
                    ),
                    null
                )
            )
        }

        SettingsItem(
            title = stringResource(id = R.string.text_version_name, BuildConfig.VERSION_NAME),
            subTitle = stringResource(id = R.string.text_version_code, BuildConfig.VERSION_CODE),
            icon = Icons.Default.PhonelinkSetup
        )

    }

}

@Composable
private fun SettingsContainer(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    val spacing = LocalSpacing.current

    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier

                .padding(spacing.spaceMedium)
        )

        content()
    }


}