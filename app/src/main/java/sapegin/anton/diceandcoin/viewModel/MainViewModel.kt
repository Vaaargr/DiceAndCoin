package sapegin.anton.diceandcoin.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import sapegin.anton.diceandcoin.controllers.SharedPreferencesController
import sapegin.anton.diceandcoin.dictionaries.DiceActivityDictionary
import sapegin.anton.diceandcoin.dictionaries.GlobalSettingsDictionary
import sapegin.anton.diceandcoin.logic.DiceFactory
import sapegin.anton.diceandcoin.models.Dice
import sapegin.anton.diceandcoin.models.StyleSettings
import sapegin.anton.diceandcoin.models.ThrowSettings

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val preferencesController: SharedPreferencesController
    private var throwSettings: ThrowSettings
    private var styleSettings: StyleSettings
    private var autoCleanFlag: Boolean

    init {
        preferencesController = SharedPreferencesController(application)
        val styleSettingsPosition =
            preferencesController.getInt(DiceActivityDictionary.STYLE_SETTING_POSITION)
        autoCleanFlag = preferencesController.getBoolean(DiceActivityDictionary.NEED_TO_CLEAN)
        styleSettings = GlobalSettingsDictionary.themes[styleSettingsPosition]
        Log.d("MyLog", "MainViewModel create")
        throwSettings = ThrowSettings(
            preferencesController.getInt(DiceActivityDictionary.DICE_TYPE),
            preferencesController.getInt(DiceActivityDictionary.NUM_OF_DICE),
            preferencesController.getInt(DiceActivityDictionary.DICE_COLOR_POSITION),
            preferencesController.getBoolean(DiceActivityDictionary.NEED_TO_SORT),
            preferencesController.getBoolean(DiceActivityDictionary.NEED_TO_COMBINE)
        )
        DiceFactory.setThrowSettings(throwSettings)
    }

    fun getStyleSettings() = styleSettings

    fun getAutoCleanFlag() = autoCleanFlag

    fun getDiceType() = throwSettings.diceType

    fun getNumOfDice() = throwSettings.numOfDice

    fun getDiceColorPosition() = throwSettings.diceColorPosition

    fun getNeedToSort() = throwSettings.needToSort

    fun getNeedToCombine() = throwSettings.needToCombine

    fun getDiceColorButton() =
        DiceActivityDictionary.DICE_COLOR[throwSettings.diceColorPosition].button

    private fun saveStyleSettingsPosition() = preferencesController.saveInt(
        DiceActivityDictionary.STYLE_SETTING_POSITION,
        styleSettings.position
    )

    private fun saveAutoCleanFlag() =
        preferencesController.saveBoolean(DiceActivityDictionary.NEED_TO_CLEAN, autoCleanFlag)

    private fun saveDiceType() =
        preferencesController.saveInt(DiceActivityDictionary.DICE_TYPE, throwSettings.diceType)

    private fun saveNumOfDice() =
        preferencesController.saveInt(DiceActivityDictionary.NUM_OF_DICE, throwSettings.numOfDice)

    private fun saveDiceColorPosition() = preferencesController.saveInt(
        DiceActivityDictionary.DICE_COLOR_POSITION,
        throwSettings.diceColorPosition
    )

    private fun saveNeedToSort() = preferencesController.saveBoolean(
        DiceActivityDictionary.NEED_TO_SORT,
        throwSettings.needToSort
    )

    private fun saveNeedToCombine() = preferencesController.saveBoolean(
        DiceActivityDictionary.NEED_TO_COMBINE,
        throwSettings.needToCombine
    )

    fun changeStyleSettings(styleSettings: StyleSettings) {
        this.styleSettings = styleSettings
        saveStyleSettingsPosition()
    }

    fun changeAutoCleanFlag(autoCleanFlag: Boolean) {
        this.autoCleanFlag = autoCleanFlag
        saveAutoCleanFlag()
    }

    fun changeDiceType(diceType: Int) {
        throwSettings.diceType = diceType
        saveDiceType()
    }

    fun changeNumOfDice(numOfDice: Int) {
        throwSettings.numOfDice = numOfDice
        saveNumOfDice()
    }

    fun changeDiceColorPosition(diceColorPosition: Int) {
        throwSettings.diceColorPosition = diceColorPosition
        saveDiceColorPosition()
    }

    fun changeNeedToSort(needToSort: Boolean) {
        throwSettings.needToSort = needToSort
        saveNeedToSort()
    }

    fun changeNeedToCombine(needToCombine: Boolean) {
        throwSettings.needToCombine = needToCombine
        saveNeedToCombine()
    }

    fun getResult(): ArrayList<Dice>{
        return if(throwSettings.needToCombine){
            DiceFactory.makeDiceResultCombine()
        } else{
            DiceFactory.makeDiceResultWithoutCombine()
        }
    }
}