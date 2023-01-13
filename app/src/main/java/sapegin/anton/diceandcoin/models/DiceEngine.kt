package sapegin.anton.diceandcoin.models


import sapegin.anton.diceandcoin.dictionaries.DiceActivityDictionary.DICE_COLOR
import sapegin.anton.diceandcoin.dictionaries.DiceActivityDictionary.DICE_MAP
import sapegin.anton.diceandcoin.dictionaries.DiceActivityDictionary.DICE_TYPES
import kotlin.random.Random


class DiceEngine(
    private val diceType: Int,
    private val numOfDice: Int,
    private val needToSort: Boolean,
    private val color: Int
) {

    private fun makeResult(): IntArray {
        val result = IntArray(numOfDice)
        val currentDice = DICE_MAP[DICE_TYPES[diceType]]!!.clone()
        for (i in result.indices) {
            currentDice.shuffle()
            result[i] = currentDice[Random.nextInt(currentDice.size - 1)]
        }
        if (needToSort) result.sort()
        return result
    }

    private fun makeDice(diceResult: Int, count: Int): Dice {
        val path = if (diceType == 0) {
            DICE_COLOR[color] + "/" + diceResult.toString() + ".png"
        } else {
            DICE_COLOR[color] + "/" + DICE_TYPES[diceType] + "/" + diceResult.toString() + ".png"
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