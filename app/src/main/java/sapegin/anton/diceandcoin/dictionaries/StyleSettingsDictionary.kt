package sapegin.anton.diceandcoin.dictionaries

import sapegin.anton.diceandcoin.R
import sapegin.anton.diceandcoin.models.StyleSettings

object StyleSettingsDictionary {
    var thems: Array<StyleSettings> = arrayOf(
        StyleSettings(
            R.drawable.green_wood,
            R.style.TextForGreenWood,
            R.drawable.button_for_green_wood,
            R.style.Theme_ForGreenWood,
            0
        ),
        StyleSettings(
            R.drawable.green_grace,
            R.style.TextForGreenGrace,
            R.drawable.button_for_green_grace,
            R.style.Theme_DiceAndCoinBasic,
            1
        ),
        StyleSettings(
            R.drawable.plitka,
            R.style.TextForPlitka,
            R.drawable.button_for_plitka,
            R.style.Theme_ForPlitka,
            2
        ),
        StyleSettings(
            R.drawable.squares,
            R.style.TextForSquares,
            R.drawable.button_for_squares,
            R.style.Theme_ForSquares,
            3
        ),
        StyleSettings(
            R.drawable.textaile,
            R.style.TextForTextaile,
            R.drawable.button_for_textaile,
            R.style.Theme_ForTextaile,
            4
        ),
        StyleSettings(
            R.drawable.white_wood_a,
            R.style.TextForWhiteWoodA,
            R.drawable.button_for_whitw_wood_a,
            R.style.Theme_ForWhiteWoodA,
            5
        ),
        StyleSettings(
            R.drawable.white_wood_b,
            R.style.TextForWhiteWoodB,
            R.drawable.button_for_white_wood_b,
            R.style.Theme_ForWhiteWoodB,
            6
        ),
        StyleSettings(
            R.drawable.white_wood_c,
            R.style.TextForWhiteWoodC,
            R.drawable.button_for_white_wood_c,
            R.style.Theme_ForWhiteWoodC,
            7
        ),
        StyleSettings(
            R.drawable.wood,
            R.style.TextForWood,
            R.drawable.button_for_wood,
            R.style.Theme_ForWood,
            8
        )
    )

    val STYLE_SETTINGS = "style settings"
    val NEED_TO_RECREATE = "need to recreate"
}