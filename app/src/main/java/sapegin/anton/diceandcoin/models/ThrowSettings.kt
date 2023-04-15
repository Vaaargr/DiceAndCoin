package sapegin.anton.diceandcoin.models

data class ThrowSettings(
    var diceType: Int,
    var numOfDice: Int,
    var diceColorPosition: Int,
    var needToSort: Boolean,
    var needToCombine: Boolean
)
