package sapegin.anton.diceandcoin.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import sapegin.anton.diceandcoin.R
import sapegin.anton.diceandcoin.dictionaries.DiceActivityDictionary
import sapegin.anton.diceandcoin.adapters.DiceAdapter
import sapegin.anton.diceandcoin.adapters.DiceColorAdapter
import sapegin.anton.diceandcoin.databinding.ActivityMainBinding
import sapegin.anton.diceandcoin.dictionaries.GlobalSettingsDictionary
import sapegin.anton.diceandcoin.models.Dice
import sapegin.anton.diceandcoin.models.DiceColor
import sapegin.anton.diceandcoin.models.DiceEngine
import sapegin.anton.diceandcoin.models.StyleSettings

class MainActivity : AppCompatActivity(), DiceColorAdapter.DiceColorListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var styleSettings: StyleSettings
    private val adapter = DiceAdapter()
    private val colorAdapter = DiceColorAdapter(this)
    private var diceTypeNum = 2
    private var needToCombine = false
    private var needToSort = false
    private var styleSettingsPosition = 3
    private var needToAutoClean = false
    private var columns = 1
    private var currentDiceColor = DiceActivityDictionary.DICE_COLOR[0]

    override fun onCreate(savedInstanceState: Bundle?) {
        //Подготовка данных
        preferences = getSharedPreferences("settings", Context.MODE_PRIVATE)

        styleSettingsPosition = preferences.getInt(GlobalSettingsDictionary.STYLE_SETTINGS, 0)
        styleSettings = GlobalSettingsDictionary.themes[styleSettingsPosition]
        setTheme(styleSettings.theme)
        needToAutoClean = preferences.getBoolean(GlobalSettingsDictionary.NEED_TO_CLEAN, false)

        //Заполнение активити
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            basicLayout.setBackgroundResource(styleSettings.backgroundLinc)
            leftMenu.setBackgroundResource(styleSettings.backgroundLinc)
            if (needToAutoClean) clearResult.visibility = View.GONE else clearResult.visibility =
                View.VISIBLE

            result.adapter = adapter

            colorChouse.adapter = colorAdapter
            colorChouse.layoutManager = GridLayoutManager(
                this@MainActivity,
                getColumnNumbs(DiceActivityDictionary.DICE_COLOR.size)
            )
            colorAdapter.addResult(DiceActivityDictionary.DICE_COLOR)

            val needToLoad = intent.getBooleanExtra(DiceActivityDictionary.NEED_TO_LOAD, false)
            if (needToLoad) {
                columns = intent.getIntExtra(DiceActivityDictionary.COLUMNS, 1)
                result.layoutManager = GridLayoutManager(this@MainActivity, columns)
                adapter.addResults(intent.getSerializableExtra(DiceActivityDictionary.SAVED_RESULT) as ArrayList<Dice>)
            }

            sort.setOnCheckedChangeListener { buttonView, isChecked -> needToSort = isChecked }
            combine.setOnCheckedChangeListener { buttonView, isChecked ->
                needToCombine = isChecked
            }
            diceType.setSelection(2)
            diceType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    diceTypeNum = position
                }
            }
            settingsButton.setOnClickListener {
                drawer.openDrawer(GravityCompat.START)
            }
            clearResult.setOnClickListener {
                adapter.clearResult()
            }
            globalSettingsButton.setOnClickListener {
                val intent = Intent(this@MainActivity, TableSetting::class.java)
                intent.putExtra(GlobalSettingsDictionary.STYLE_SETTINGS, styleSettingsPosition)
                intent.putExtra(GlobalSettingsDictionary.NEED_TO_CLEAN, needToAutoClean)
                startActivity(intent)
            }
            throwDice.setOnClickListener {
                onClickThrow()
            }
            showColorsButton.setOnClickListener {
                colorChouse.visibility = View.VISIBLE
            }
        }
    }

    override fun onResume() {
        if (preferences.getInt(
                GlobalSettingsDictionary.STYLE_SETTINGS,
                0
            ) != styleSettingsPosition
        ) {
            val i = Intent(this, MainActivity::class.java)
            i.putExtra(DiceActivityDictionary.SAVED_RESULT, adapter.getDiceList())
            i.putExtra(DiceActivityDictionary.COLUMNS, columns)
            i.putExtra(DiceActivityDictionary.NEED_TO_LOAD, true)
            startActivity(i)
        }
        if (preferences.getBoolean(
                GlobalSettingsDictionary.NEED_TO_CLEAN,
                false
            ) != needToAutoClean
        ) {
            needToAutoClean = preferences.getBoolean(GlobalSettingsDictionary.NEED_TO_CLEAN, false)
            binding.clearResult.visibility = if (needToAutoClean) {
                View.GONE
            } else View.VISIBLE
        }
        super.onResume()
    }

    private fun onClickThrow() {
        if (needToAutoClean) adapter.clearResult()
        val diceNumber =
            if (binding.numOfDice.text.toString().isNotEmpty()) binding.numOfDice.text.toString()
                .toInt() else 1
        val engine = DiceEngine(diceTypeNum, diceNumber, needToSort, currentDiceColor.colorName)
        val resultToShow = if (!needToCombine) {
            engine.makeDiceResultWithoutCombine()
        } else {
            engine.makeDiceResultCombine()
        }
        if (adapter.getDiceList().size < 1) {
            binding.result.layoutManager =
                GridLayoutManager(this, getColumnNumbs(resultToShow.size))
        } else {
            binding.result.layoutManager =
                GridLayoutManager(
                    this,
                    getColumnNumbs(resultToShow.size + adapter.getDiceList().size)
                )
        }
        adapter.addResults(resultToShow)
    }

    private fun getColumnNumbs(i: Int): Int {
        val columnNumbs = when (i) {
            in 0..1 -> 1
            in 2..4 -> 2
            in 5..9 -> 3
            in 10..16 -> 4
            in 16..25 -> 5
            else -> 6
        }
        columns = columnNumbs
        return columnNumbs
    }

    override fun onClick(diceColor: DiceColor) {
        if (diceColor.colorName != "none") {
            binding.colorChouse.visibility = View.GONE
            currentDiceColor = diceColor
            binding.showColorsButton.setBackgroundResource(diceColor.button)
        }
    }
}
