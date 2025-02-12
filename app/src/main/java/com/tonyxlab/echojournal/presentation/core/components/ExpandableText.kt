package com.tonyxlab.echojournal.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.presentation.theme.EchoJournalTheme
import com.tonyxlab.echojournal.presentation.theme.LocalSpacing
import com.tonyxlab.echojournal.utils.generateLoremIpsum

@Composable
fun ExpandableText(
    echoText: String,
    modifier: Modifier = Modifier,
    textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    collapsedMaxLines: Int = 3,
    regularTextStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    showMoreText: String = stringResource(R.string.show_more_text),
    showMoreStyle: SpanStyle = SpanStyle(
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Black
    ),
    showLessText: String = stringResource(R.string.show_less_text),
    showLessStyle: SpanStyle = SpanStyle(
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.W300
    )
) {
    val spacing = LocalSpacing.current

    var isExpanded by remember { mutableStateOf(false) }
    var isOverflowing by remember { mutableStateOf(false) }
    var lastCharIndex by remember { mutableIntStateOf(0) }

    val annotatedString = buildAnnotatedString {

        if (isOverflowing) {
            // Overflowing
            if (isExpanded) {
                //Expanded
                append(echoText)
                withLink(
                        link = LinkAnnotation.Clickable(
                                tag = showLessText,
                                linkInteractionListener = { isExpanded =isExpanded.not() })
                ) {

                    withStyle(showLessStyle) {
                        append(showLessText)
                    }
                }
            } else {

                // Not Expanded
                val adjustedText = echoText.substring(startIndex = 0, endIndex = lastCharIndex)
                        //creates space for show more text ...
                        .dropLast(showMoreText.length)
                        .dropLastWhile { it.isWhitespace() or (it == '.') }

                append(adjustedText)

                withLink(
                        link = LinkAnnotation.Clickable(
                                tag = showMoreText,
                                linkInteractionListener = { isExpanded = isExpanded.not() })
                ) {

                    withStyle(showMoreStyle) {
                        append(showMoreText)
                    }
                }

            }
        } else {

            //if not overflowing return the echo text as-is
            append(echoText)
        }

    }

    Text(
            modifier = modifier.padding(spacing.spaceDoubleDp),
            text = annotatedString,
            color = textColor,
            style = regularTextStyle,
            maxLines = if (isExpanded) Int.MAX_VALUE else collapsedMaxLines,

            // callback that is executed when a new text layout is calculated
            onTextLayout = { textLayoutResult ->

                if (isExpanded.not() and textLayoutResult.hasVisualOverflow) {
                    isOverflowing = true
                    lastCharIndex = textLayoutResult.getLineEnd(collapsedMaxLines - 1)
                }

            }
    )
}


@PreviewLightDark
@Composable
private fun ExpandablePreview() {
    val spacing = LocalSpacing.current
    EchoJournalTheme {

        Column(
                modifier = Modifier
                        .padding(spacing.spaceMedium)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
        ) {

            ExpandableText(echoText = generateLoremIpsum(15))

            ExpandableText(echoText = generateLoremIpsum(135))

            ExpandableText(echoText = generateLoremIpsum(135))

            ExpandableText(echoText = generateLoremIpsum(199))

            ExpandableText(echoText = generateLoremIpsum(0))
        }

    }

}