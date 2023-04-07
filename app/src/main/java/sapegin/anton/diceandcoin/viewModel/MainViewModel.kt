package sapegin.anton.diceandcoin.viewModel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import sapegin.anton.diceandcoin.controllers.SharedPreferencesController
import sapegin.anton.diceandcoin.dictionaries.DiceActivityDictionary
import sapegin.anton.diceandcoin.dictionaries.GlobalSettingsDictionary
import sapegin.anton.diceandcoin.models.StyleSettings

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val preferencesController: SharedPreferencesController
    private var styleSettings: StyleSettings
    private var autoCleanFlag: Boolean

    init {
        preferencesController = SharedPreferencesController(application)
        val styleSettingsPosition =
            preferencesController.getInt(DiceActivityDictionary.STYLE_SETTING_POSITION)
        autoCleanFlag = preferencesController.getBoolean(DiceActivityDictionary.NEED_TO_CLEAN)
        styleSettings = GlobalSettingsDictionary.themes[styleSettingsPosition]
        Log.d("MyLog", "MainViewModel create")
    }

    fun getStyleSettings(): StyleSettings {
        return styleSettings
    }

    fun getAutoCleanFlag(): Boolean {
        return autoCleanFlag
    }

    private fun saveStyleSettingsPosition() {
        preferencesController.saveInt(
            DiceActivityDictionary.STYLE_SETTING_POSITION,
            styleSettings.position
        )
    }

    private fun saveAutoCleanFlag() {
        preferencesController.saveBoolean(DiceActivityDictionary.NEED_TO_CLEAN, autoCleanFlag)
    }

    fun changeStyleSettings(styleSettings: StyleSettings) {
        this.styleSettings = styleSettings
        saveStyleSettingsPosition()
    }

    fun changeAutoCleanFlag(autoCleanFlag: Boolean) {
        this.autoCleanFlag = autoCleanFlag
        saveAutoCleanFlag()
    }
}