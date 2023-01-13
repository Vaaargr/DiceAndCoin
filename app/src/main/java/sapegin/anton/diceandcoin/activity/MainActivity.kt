package sapegin.anton.diceandcoin.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import sapegin.anton.diceandcoin.databinding.ActivityMainBinding
import sapegin.anton.diceandcoin.dictionaries.GlobalSettingsDictionary
import sapegin.anton.diceandcoin.models.Dice
import sapegin.anton.diceandcoin.models.DiceEngine
import sapegin.anton.diceandcoin.models.StyleSettings

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var styleSettings: StyleSettings
    private val adapter = DiceAdapter()
    private var diceTypeNum = 2
    private var needToCombine = false
    private var needToSort = false
    private var styleSettingsPosition = 3
    private var needToAutoClean = false
    private var columns = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        preferences = getSharedPreferences("settings", Context.MODE_PRIVATE)

        styleSettingsPosition = preferences.getInt(GlobalSettingsDictionary.STYLE_SETTINGS, 0)
        styleSettings = GlobalSettingsDictionary.themes[styleSettingsPosition]
        setTheme(styleSettings.theme)
        needToAutoClean = preferences.getBoolean(GlobalSettingsDictionary.NEED_TO_CLEAN, false)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            if (needToAutoClean) clearResult.visibility = View.GONE else clearResult.visibility =
                View.VISIBLE
            result.adapter = adapter

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
        val engine = DiceEngine(diceTypeNum, diceNumber, needToSort, 1)
        val resultToShow = if (!needToCombine) {
            engine.makeDiceResultWithoutCombine()
        } else {
            engine.makeDiceResultCombine()
        }
        binding.result.layoutManager = GridLayoutManager(this, getColumnNumbs(resultToShow.size))
        adapter.addResults(resultToShow)
    }

    /*private fun makeResult(diceTypeArray: IntArray): IntArray {
        val currentDice = diceTypeArray.clone()
        val result = IntArray(diceNum)
        for (i in result.indices) {
            currentDice.shuffle()
            result[i] = currentDice[random.nextInt(0, currentDice.size - 1)]
        }
        return result
    }*/

    /* private fun makeDiceResultWithoutCombine(currentResult: IntArray): ArrayList<Dice> {
         val diceEngine = DiceEngine(3, 2, false, 2)
         val finResult = ArrayList<Dice>()
         if (needToSort) currentResult.sort()
         currentResult.forEach {
             finResult.add(diceEngine.makeDice(it,0))
         }
         return finResult
     }*/

    /* private fun makeDiceResultCombine(currentResult: IntArray, size: Int): ArrayList<Dice> {
         val finResult = ArrayList<Dice>()
         val res = IntArray(size)
         currentResult.forEach { res[it - 1]++ }
         for (i in res.indices) {
             if (res[i] != 0) {
                 finResult.add(makeDice(i + 1, res[i]))
             }
         }
         return finResult
     }*/

    /*private fun makeDice(diceResult: Int, count: Int): Dice {
        val diceImage = Drawable.createFromStream(assets.open("black/D6/3.png"), null)
        *//*when (diceTypeNum) {
        0 -> {
            when (diceResult) {
                1 -> R.drawable.avers
                2 -> R.drawable.revers
                else -> R.drawable.ic_android_black_24dp
            }
        }
        1 -> {
            when (diceResult) {
                1 -> R.drawable.one
                2 -> R.drawable.two
                3 -> R.drawable.three
                else -> R.drawable.four
            }
        }
        else -> {
            when (diceResult) {
                1 -> R.drawable.one
                2 -> R.drawable.two
                3 -> R.drawable.three
                4 -> R.drawable.four
                5 -> R.drawable.five
                else -> R.drawable.six
            }
        }
    }*//*

        return Dice(diceImage, count)
    }*/

    private fun getColumnNumbs(i: Int): Int {
        val columnNumbs = when (i) {
            1 -> 1
            in 2..4 -> 2
            in 5..9 -> 3
            in 10..16 -> 4
            in 16..25 -> 5
            else -> 6
        }
        columns = columnNumbs
        return columnNumbs
    }
}
