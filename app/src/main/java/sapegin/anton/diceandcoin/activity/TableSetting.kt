package sapegin.anton.diceandcoin.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import sapegin.anton.diceandcoin.adapters.StyleSettingsAdapter
import sapegin.anton.diceandcoin.databinding.ActivityTableSettingBinding
import sapegin.anton.diceandcoin.dictionaries.StyleSettingsDictionary
import sapegin.anton.diceandcoin.models.StyleSettings

class TableSetting : AppCompatActivity(), StyleSettingsAdapter.BackgroundListener {
    lateinit var binding: ActivityTableSettingBinding
    private val adapter = StyleSettingsAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTableSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            styleChoose.adapter = adapter
            styleChoose.layoutManager = GridLayoutManager(this@TableSetting, 3)
            adapter.add(StyleSettingsDictionary.thems)
            chouseBackgroundButton.setOnClickListener {
                styleChoose.visibility = View.VISIBLE
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(styleSettings: StyleSettings) {
        binding.styleChoose.visibility = View.GONE
        changeStyle(styleSettings)
    }

    @RequiresApi(Build.VERSION_CODES.M)
   private fun changeStyle(styleSettings: StyleSettings){
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