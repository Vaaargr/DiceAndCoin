package sapegin.anton.diceandcoin.viewModel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import sapegin.anton.diceandcoin.dictionaries.DiceActivityDictionary
import sapegin.anton.diceandcoin.dictionaries.GlobalSettingsDictionary
import sapegin.anton.diceandcoin.models.StyleSettings

class MainViewModel(application: Application): AndroidViewModel(application) {

    private var preference: SharedPreferences =
        getApplication<Application>().getSharedPreferences("settings", Context.MODE_PRIVATE)
    private var styleSettings: StyleSettings
    private var autoCleanFlag: Boolean

    init {
        val styleSettingsPosition =
            preference.getInt(DiceActivityDictionary.STYLE_SETTING_POSITION, 0)
        autoCleanFlag = preference.getBoolean(DiceActivityDictionary.NEED_TO_CLEAN, false)
        styleSettings = GlobalSettingsDictionary.themes[styleSettingsPosition]
        Log.d("MyLog", "MainViewModel create")
    }

    fun getStyleSettings(): StyleSettings {
        return styleSettings
    }

    fun getAutoCleanFlag(): Boolean{
        return autoCleanFlag
    }

    private fun saveStyleSettingsPosition(styleSettingsPosition: Int) {
        preference.edit()
            .putInt(DiceActivityDictionary.STYLE_SETTING_POSITION, styleSettingsPosition).apply()
    }

    private fun saveAutoCleanFlag(){
        preference.edit()
            .putBoolean(DiceActivityDictionary.NEED_TO_CLEAN, autoCleanFlag).apply()
    }

    fun changeStyleSettings(styleSettings: StyleSettings) {
        this.styleSettings = styleSettings
        saveStyleSettingsPosition(styleSettings.position)
    }

    fun changeAutoCleanFlag(autoCleanFlag: Boolean){
        this.autoCleanFlag = autoCleanFlag
        saveAutoCleanFlag()
    }
}