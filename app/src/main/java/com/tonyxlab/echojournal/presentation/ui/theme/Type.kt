package com.tonyxlab.echojournal.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.tonyxlab.echojournal.R


val InterFontFamily = FontFamily(
        Font(R.font.inter_regular, FontWeight.Normal)

)
val Typography = Typography(
        headlineLarge = TextStyle(
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 26.sp,
                lineHeight = 32.sp,
                letterSpacing = 0.sp,
        ),
        headlineMedium = TextStyle(
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 22.sp,
                lineHeight = 26.sp,
                letterSpacing = 0.sp,
        ),
        headlineSmall = TextStyle(
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.sp,
        ),

        bodyMedium = TextStyle(
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.25.sp,
        ),
        bodySmall = TextStyle(
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.4.sp,
        ),

        labelMedium = TextStyle(
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                lineHeight = TextUnit.Unspecified,
                letterSpacing = 0.5.sp,
        ),

        //Improvised Button Text Style - Button Medium
        labelLarge = TextStyle(
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                lineHeight = 24.sp,

                ),


        //Improvised Button Text Style - Button Small
        labelSmall = TextStyle(
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 20.sp,

                ),

        )

val buttonMediumTextStyle = Typography.labelLarge
val buttonSmallTextStyle = Typography.labelSmall
