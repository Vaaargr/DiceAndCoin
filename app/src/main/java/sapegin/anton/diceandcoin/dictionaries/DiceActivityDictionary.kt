package sapegin.anton.diceandcoin.dictionaries

import sapegin.anton.diceandcoin.R
import sapegin.anton.diceandcoin.models.DiceColor

object DiceActivityDictionary {

    val DICE_TYPES =
        arrayListOf("coin", "D4", "D6", "D8", "D10", "D12", "D16", "D20")

    val DICE_MAP = mapOf(
        DICE_TYPES[0] to intArrayOf(0, 1),
        DICE_TYPES[1] to intArrayOf(0, 1, 2, 3),
        DICE_TYPES[2] to intArrayOf(0, 1, 2, 3, 4, 5),
        DICE_TYPES[3] to intArrayOf(0, 1, 2, 3, 4, 5, 6, 7),
        DICE_TYPES[4] to intArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9),
        DICE_TYPES[5] to intArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11),
        DICE_TYPES[6] to intArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15),
        DICE_TYPES[7] to intArrayOf(
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19
        )
    )

    val DICE_COLOR = arrayListOf(
        DiceColor("black", R.color.black, R.drawable.button_color_black, 0),
        DiceColor("white", R.color.white, R.drawable.button_color_white, 1),
        DiceColor("blue", R.color.blue, R.drawable.button_color_blue, 2),
        DiceColor("green", R.color.green, R.drawable.button_color_green, 3),
        DiceColor("red", R.color.red, R.drawable.button_color_red, 4),
        DiceColor("yellow", R.color.yellow, R.drawable.button_color_yellow, 5),
        DiceColor("none", R.color.none, R.drawable.button_color_none, 6),
        DiceColor("pink", R.color.pink, R.drawable.button_color_pink, 7),
        DiceColor("none", R.color.none, R.drawable.button_color_none, 8)
    )

    const val STYLE_SETTING_POSITION = "style setting position"
    const val NEED_TO_CLEAN = "need to clean"
    const val DICE_TYPE = "dice type"
    const val NUM_OF_DICE = "num of dice"
    const val NEED_TO_SORT = "need to sort"
    const val NEED_TO_COMBINE = "need to combine"
    const val DICE_COLOR_POSITION = "dice color position"
}