package sapegin.anton.diceandcoin.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import sapegin.anton.diceandcoin.adapters.StyleSettingsAdapter
import sapegin.anton.diceandcoin.databinding.ActivityTableSettingBinding
import sapegin.anton.diceandcoin.dictionaries.DiceActivityDictionary
import sapegin.anton.diceandcoin.dictionaries.GlobalSettingsDictionary
import sapegin.anton.diceandcoin.models.StyleSettings
import sapegin.anton.diceandcoin.viewModel.MainViewModel

class TableSetting : AppCompatActivity(), StyleSettingsAdapter.BackgroundListener {
    lateinit var binding: ActivityTableSettingBinding
    //private lateinit var preferences: SharedPreferences
    private val adapter = StyleSettingsAdapter(this)
    private var styleSettingsPosition = 0
    private var autoCleanFlag = false

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        //Подготовка данных
        //preferences = getSharedPreferences("settings", Context.MODE_PRIVATE)

        super.onCreate(savedInstanceState)

        binding = ActivityTableSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("MyLog", "Global Setting started")

        styleSettingsPosition = intent.getIntExtra(DiceActivityDictionary.STYLE_SETTING_POSITION, 0)
        autoCleanFlag = intent.getBooleanExtra(DiceActivityDictionary.NEED_TO_CLEAN, false)
        changeStyle(GlobalSettingsDictionary.themes[styleSettingsPosition])

        binding.apply {
            autoCleanSwitch.isChecked = autoCleanFlag
            styleChoose.adapter = adapter
            styleChoose.layoutManager = GridLayoutManager(this@TableSetting, 3)
            adapter.add(GlobalSettingsDictionary.themes)
            chouseBackgroundButton.setOnClickListener {
                styleChoose.visibility = View.VISIBLE
            }
            autoCleanSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                autoCleanFlag = isChecked
                val intent = Intent()
                intent.putExtra(DiceActivityDictionary.NEED_TO_CLEAN, autoCleanFlag)
                intent.putExtra(DiceActivityDictionary.STYLE_SETTING_POSITION, styleSettingsPosition)
                setResult(RESULT_OK, intent)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(styleSettings: StyleSettings) {
        binding.styleChoose.visibility = View.GONE
        changeStyle(styleSettings)
        styleSettingsPosition = styleSettings.position
        val intent = Intent()
        intent.putExtra(DiceActivityDictionary.STYLE_SETTING_POSITION, styleSettingsPosition)
        intent.putExtra(DiceActivityDictionary.NEED_TO_CLEAN, autoCleanFlag)
        setResult(RESULT_OK, intent)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun changeStyle(styleSettings: StyleSettings) {
        binding.apply {
            settings.setBackgroundResource(styleSettings.backgroundLinc)
            settingsHeader.setTextAppearance(styleSettings.textStyle)
            styleSettingsText.setTextAppearance(styleSettings.textStyle)
            autoCleanText.setTextAppearance(styleSettings.textStyle)
            chouseBackgroundButton.setBackgroundResource(styleSettings.buttonDraw)
            chouseBackgroundButton.setTextAppearance(styleSettings.textStyle)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyLog", "Global Setting destroyed")
    }
}