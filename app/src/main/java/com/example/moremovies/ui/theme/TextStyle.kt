package com.example.moremovies.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.unit.sp

val styleTextBodyBig = TextStyle(
    fontWeight = FontWeight.SemiBold,
    fontSize = 20.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.15.sp
)

val styleTextBodyNormal = TextStyle(
    fontWeight = FontWeight.Normal,
    fontFamily = FontFamily.SansSerif,
    fontStyle = FontStyle.Normal,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.15.sp,
    baselineShift = BaselineShift.Subscript
)
