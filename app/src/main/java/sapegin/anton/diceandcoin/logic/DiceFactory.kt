package sapegin.anton.diceandcoin.logic


import sapegin.anton.diceandcoin.dictionaries.DiceActivityDictionary
import sapegin.anton.diceandcoin.dictionaries.DiceActivityDictionary.DICE_MAP
import sapegin.anton.diceandcoin.dictionaries.DiceActivityDictionary.DICE_TYPES
import sapegin.anton.diceandcoin.models.Dice
import sapegin.anton.diceandcoin.models.ThrowSettings


object DiceFactory {

    private lateinit var throwSettings: ThrowSettings

    fun setThrowSettings(throwSettings: ThrowSettings){
        this.throwSettings = throwSettings
    }

    private fun makeResult(): IntArray {
        val result = IntArray(throwSettings.numOfDice)
        val currentDice = DICE_MAP[DICE_TYPES[throwSettings.diceType]]!!.clone()
        for (i in result.indices) {
            result[i] = currentDice[(Math.random() * currentDice.size).toInt()]
        }
        if (throwSettings.needToSort) result.sort()
        return result
    }

    private fun makeDice(diceResult: Int, count: Int): Dice {
        val path = if (throwSettings.diceType == 0) {
            "file:///android_asset/${DICE_TYPES[0]}/$diceResult.webp"
        } else {
            val color = DiceActivityDictionary.DICE_COLOR[throwSettings.diceColorPosition].colorName
            "file:///android_asset/" + color + "/" + DICE_TYPES[throwSettings.diceType] + "/" +
                    diceResult.toString() + ".png"
        }
        return Dice(path, count)
    }

    fun makeDiceResultWithoutCombine(): ArrayList<Dice> {
        val result = ArrayList<Dice>(throwSettings.numOfDice)
        val currentResult = makeResult()
        currentResult.forEach {
            result.add(makeDice(it, 0))
        }
        return result
    }

    fun makeDiceResultCombine(): ArrayList<Dice> {
        val result = ArrayList<Dice>()
        val currentResult = makeResult()
        val res = Array(DICE_MAP[DICE_TYPES[throwSettings.diceType]]!!.size) { 0 }
        currentResult.forEach { res[it]++ }
        for (i in res.indices) {
            if (res[i] != 0) {
                result.add(makeDice(i, res[i]))
            }
        }
        return result
    }
}