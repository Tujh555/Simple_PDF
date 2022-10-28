package ru.a1Estate.simplepdf.domain.pdfCreating.settings

import android.text.TextPaint

/**
 * Class describes content settings of one page
 *
 * @property pageWidth width in PT
 * @property pageHeight height in PT
 */
data class PageSettings(
    val pageWidth: Int,
    val pageHeight: Int,
    val paint: TextPaint,
    val background: Int?,
    val titleSettings: TextSettings.TitleSettings,
    val footerSettings: TextSettings.FooterSettings,
    val mainContentSettings: TextSettings.MainContentSettings
)