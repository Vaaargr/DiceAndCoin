package sapegin.anton.diceandcoin.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import sapegin.anton.diceandcoin.adapters.StyleSettingsAdapter
import sapegin.anton.diceandcoin.databinding.ActivityTableSettingBinding
import sapegin.anton.diceandcoin.dictionaries.GlobalSettingsDictionary
import sapegin.anton.diceandcoin.models.StyleSettings

class TableSetting : AppCompatActivity(), StyleSettingsAdapter.BackgroundListener {
    lateinit var binding: ActivityTableSettingBinding
    private lateinit var preferences: SharedPreferences
    private val adapter = StyleSettingsAdapter(this)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        preferences = getSharedPreferences("settings", Context.MODE_PRIVATE)
        super.onCreate(savedInstanceState)
        binding = ActivityTableSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val position = intent.getIntExtra(GlobalSettingsDictionary.STYLE_SETTINGS, 0)
        changeStyle(GlobalSettingsDictionary.themes[position])
        binding.apply {
            autoCleanSwitch.isChecked =
                intent.getBooleanExtra(GlobalSettingsDictionary.NEED_TO_CLEAN, false)
            styleChoose.adapter = adapter
            styleChoose.layoutManager = GridLayoutManager(this@TableSetting, 3)
            adapter.add(GlobalSettingsDictionary.themes)
            chouseBackgroundButton.setOnClickListener {
                styleChoose.visibility = View.VISIBLE
            }
            autoCleanSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                preferences.edit().putBoolean(GlobalSettingsDictionary.NEED_TO_CLEAN, isChecked)
                    .apply()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(styleSettings: StyleSettings) {
        binding.styleChoose.visibility = View.GONE
        changeStyle(styleSettings)
        preferences.edit()
            .putInt(GlobalSettingsDictionary.STYLE_SETTINGS, styleSettings.position)
            .apply()
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
}