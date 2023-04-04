package sapegin.anton.diceandcoin.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import sapegin.anton.diceandcoin.dictionaries.DiceActivityDictionary
import sapegin.anton.diceandcoin.adapters.DiceAdapter
import sapegin.anton.diceandcoin.adapters.DiceColorAdapter
import sapegin.anton.diceandcoin.databinding.ActivityMainBinding
import sapegin.anton.diceandcoin.dictionaries.GlobalSettingsDictionary
import sapegin.anton.diceandcoin.models.Dice
import sapegin.anton.diceandcoin.models.DiceColor
import sapegin.anton.diceandcoin.logic.DiceFactory
import sapegin.anton.diceandcoin.viewModel.MainViewModel
import sapegin.anton.diceandcoin.viewModel.MainViewModelFactory

class MainActivity : AppCompatActivity(), DiceColorAdapter.DiceColorListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var viewModel: MainViewModel
    private val diceAdapter = DiceAdapter()
    private val colorAdapter = DiceColorAdapter(this)
    private var diceTypeNum = 2
    private var needToCombine = false
    private var needToSort = false
    private var columns = 1
    private var currentDiceColor = DiceActivityDictionary.DICE_COLOR[0]

    private val globalSettingStartActivityForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        Log.d("MyLog", "Return to Main activity")
        val result = it.data
        val newStyleSettingsPosition =
            result?.getIntExtra(DiceActivityDictionary.STYLE_SETTING_POSITION, 0) ?: 0
        val newAutoCleanFlag =
            result?.getBooleanExtra(DiceActivityDictionary.NEED_TO_CLEAN, false) ?: false
        if (newStyleSettingsPosition != viewModel.getStyleSettings().position) {
            updateStyleSettings(newStyleSettingsPosition)
        }
        if (newAutoCleanFlag != viewModel.getAutoCleanFlag()){
            updateAutoCleanFlag(newAutoCleanFlag)
        }

        Log.d("MyLog", "Main activity get $newStyleSettingsPosition")
        Log.d("MyLog", "Main activity get $newAutoCleanFlag")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //Подготовка данных
        preferences = getSharedPreferences("settings", Context.MODE_PRIVATE)
        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(preferences)
        ).get(MainViewModel::class.java)

        setTheme(viewModel.getStyleSettings().theme)

        //Заполнение активити
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            //Обновление бэкграундов
            basicLayout.setBackgroundResource(viewModel.getStyleSettings().backgroundLinc)
            leftMenu.setBackgroundResource(viewModel.getStyleSettings().backgroundLinc)

            setCleanVisibility(viewModel.getAutoCleanFlag())

            result.adapter = diceAdapter
            diceType.setSelection(2)

            //Обработка выбора цвета
            colorChouse.adapter = colorAdapter
            colorChouse.layoutManager = GridLayoutManager(
                this@MainActivity,
                getColumnNumbs(DiceActivityDictionary.DICE_COLOR.size)
            )
            colorAdapter.addResult(DiceActivityDictionary.DICE_COLOR)

            //Выгрузка результатов броска из интента при смене стиля
            val needToLoad = intent.getBooleanExtra(DiceActivityDictionary.NEED_TO_LOAD, false)
            if (needToLoad) {
                columns = intent.getIntExtra(DiceActivityDictionary.COLUMNS, 1)
                result.layoutManager = GridLayoutManager(this@MainActivity, columns)
                diceAdapter.addResults(intent.getSerializableExtra(DiceActivityDictionary.SAVED_RESULT) as ArrayList<Dice>)
            }

            //Создание слушателей
            sort.setOnCheckedChangeListener { buttonView, isChecked -> needToSort = isChecked }

            combine.setOnCheckedChangeListener { buttonView, isChecked ->
                needToCombine = isChecked
            }

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
                diceAdapter.clearResult()
            }

            globalSettingsButton.setOnClickListener {
                onClickGlobalSettingsButton()
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
/*if (preferences.getInt(
        GlobalSettingsDictionary.STYLE_SETTINGS,
        0
    ) != styleSettingsPosition
    viewModel.needToChangeStyle
) {
    val i = Intent(this, MainActivity::class.java)
    i.putExtra(DiceActivityDictionary.SAVED_RESULT, diceAdapter.getDiceList())
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
        }*/
        super.onResume()
    }

    private fun onClickThrow() {
        if (viewModel.getAutoCleanFlag()) diceAdapter.clearResult()
        val diceNumber =
            if (binding.numOfDice.text.toString()
                    .isNotEmpty()
            ) binding.numOfDice.text.toString()
                .toInt() else 1
        val engine =
            DiceFactory(diceTypeNum, diceNumber, needToSort, currentDiceColor.colorName)
        val resultToShow = if (!needToCombine) {
            engine.makeDiceResultWithoutCombine()
        } else {
            engine.makeDiceResultCombine()
        }
        if (diceAdapter.getDiceList().size < 1) {
            binding.result.layoutManager =
                GridLayoutManager(this, getColumnNumbs(resultToShow.size))
        } else {
            binding.result.layoutManager =
                GridLayoutManager(
                    this,
                    getColumnNumbs(resultToShow.size + diceAdapter.getDiceList().size)
                )
        }
        diceAdapter.addResults(resultToShow)
    }

    private fun onClickGlobalSettingsButton() {
        Log.d("MyLog", "Started Global Setting")
        val intent = Intent(this@MainActivity, TableSetting::class.java)
        intent.putExtra(
            DiceActivityDictionary.STYLE_SETTING_POSITION,
            viewModel.getStyleSettings().position
        )
        intent.putExtra(DiceActivityDictionary.NEED_TO_CLEAN, viewModel.getAutoCleanFlag())
        globalSettingStartActivityForResult.launch(intent)
    }

    private fun getColumnNumbs(i: Int): Int {
        val columnNumbs = when (i) {
            in -1..1 -> 1
            in 2..4 -> 2
            in 5..9 -> 3
            in 10..16 -> 4
            else -> 5
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

    private fun setCleanVisibility(autoCleanFlag: Boolean) {
        if (autoCleanFlag) binding.clearResult.visibility =
            View.GONE else binding.clearResult.visibility =
            View.VISIBLE
    }

    private fun updateStyleSettings(styleSettingsPosition: Int) {
        viewModel.changeStyleSettings(GlobalSettingsDictionary.themes[styleSettingsPosition])
        finish()
        startActivity(intent)
    }

    private fun updateAutoCleanFlag(newAutoCleanFlag: Boolean) {
        viewModel.changeAutoCleanFlag(newAutoCleanFlag)
        setCleanVisibility(newAutoCleanFlag)
    }
}
