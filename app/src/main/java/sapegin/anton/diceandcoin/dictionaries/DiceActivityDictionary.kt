package sapegin.anton.diceandcoin.dictionaries

object DiceActivityDictionary {
    val COIN = intArrayOf(1, 2)
    val D4 = intArrayOf(1, 2, 3, 4)
    val D6 = intArrayOf(1, 2, 3, 4, 5, 6)

    val DICE_TYPES =
        arrayListOf("Coin", "D4", "D6", "D8", "D10", "D12", "D16", "D20", "D100")

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
        ),
        DICE_TYPES[8] to intArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9),
    )


    val SAVED_RESULT = "saved result"
    val COLUMNS = "number of columns"
    val NEED_TO_LOAD = "need to load"

}