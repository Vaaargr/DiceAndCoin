package sapegin.anton.diceandcoin.logic


import sapegin.anton.diceandcoin.dictionaries.DiceActivityDictionary.DICE_MAP
import sapegin.anton.diceandcoin.dictionaries.DiceActivityDictionary.DICE_TYPES
import sapegin.anton.diceandcoin.models.Dice


class DiceFactory(
    private val diceType: Int,
    private val numOfDice: Int,
    private val needToSort: Boolean,
    private val color: String
) {

    private fun makeResult(): IntArray {
        val result = IntArray(numOfDice)
        val currentDice = DICE_MAP[DICE_TYPES[diceType]]!!.clone()
        for (i in result.indices) {
            result[i] = currentDice[(Math.random() * currentDice.size).toInt()]
        }
        if (needToSort) result.sort()
        return result
    }

    private fun makeDice(diceResult: Int, count: Int): Dice {
        val path = if (diceType == 0) {
            "file:///android_asset/${DICE_TYPES[diceType]}/$diceResult.webp"
        } else {
            "file:///android_asset/" + color + "/" + DICE_TYPES[diceType] + "/" +
                    diceResult.toString() + ".png"
        }
        return Dice(path, count)
    }

    fun makeDiceResultWithoutCombine(): ArrayList<Dice> {
        val result = ArrayList<Dice>(numOfDice)
        val currentResult = makeResult()
        currentResult.forEach {
            result.add(makeDice(it, 0))
        }
        return result
    }

    fun makeDiceResultCombine(): ArrayList<Dice> {
        val result = ArrayList<Dice>()
        val currentResult = makeResult()
        val res = Array(DICE_MAP[DICE_TYPES[diceType]]!!.size) { 0 }
        currentResult.forEach { res[it]++ }
        for (i in res.indices) {
            if (res[i] != 0) {
                result.add(makeDice(i, res[i]))
            }
        }
        return result
    }
}