package ru.a1Estate.simplepdf

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.text.Layout
import android.text.TextPaint
import ru.a1Estate.simplepdf.domain.pdfCreating.settings.DescriptionLocation
import ru.a1Estate.simplepdf.domain.pdfCreating.settings.Padding
import ru.a1Estate.simplepdf.domain.pdfCreating.settings.PageSettings
import ru.a1Estate.simplepdf.domain.pdfCreating.settings.TextSettings.*

object Test {

    val settings = PageSettings(
        960,
        580,
        TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            typeface = Typeface.MONOSPACE
        },
        R.drawable.background,
        TitleSettings(
            Layout.Alignment.ALIGN_NORMAL,
            Padding(30, 10, 10, 15),
            Color.WHITE,
            36f
        ),
        FooterSettings(
            Layout.Alignment.ALIGN_CENTER,
            Padding(0, 0, 0, 0),
            Color.GRAY,
            10f
        ),
        MainContentSettings(
            Layout.Alignment.ALIGN_NORMAL,
            Padding(30, 20, 30, 10),
            Color.BLACK,
            16f,
            Layout.Alignment.ALIGN_NORMAL,
            1,
            DescriptionLocation.RIGHT,
            21f
        )
    )
}