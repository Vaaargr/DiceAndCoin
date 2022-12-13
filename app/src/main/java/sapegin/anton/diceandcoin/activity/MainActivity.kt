package sapegin.anton.diceandcoin.activity

import android.content.Intent
import android.content.res.Resources.Theme
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.annotation.RequiresApi
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import sapegin.anton.diceandcoin.dictionaries.DiceActivityDictionary
import sapegin.anton.diceandcoin.R
import sapegin.anton.diceandcoin.adapters.DiceAdapter
import sapegin.anton.diceandcoin.databinding.ActivityMainBinding
import sapegin.anton.diceandcoin.dictionaries.StyleSettingsDictionary
import sapegin.anton.diceandcoin.models.Dice
import sapegin.anton.diceandcoin.models.StyleSettings
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val random = Random
    private val adapter = DiceAdapter()
    private var diceTypeNum = 2
    private var diceNum = 1
    private var needToCombine = false
    private var needToSort = false
    private var style = R.style.Theme_DiceAndCoin


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //changeStyle(style)
        binding.apply {
            result.adapter = adapter
            sort.setOnCheckedChangeListener { buttonView, isChecked -> needToSort = isChecked }
            combine.setOnCheckedChangeListener { buttonView, isChecked ->
                needToCombine = isChecked
            }
            diceType.setSelection(2)
            diceType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
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
            toolbar.findViewById<View>(R.id.global_settings_button).setOnClickListener {
                val intent = Intent(this@MainActivity, TableSetting::class.java)
                startActivity(intent)
            }
        }
    }

    fun onClickThrow(view: View) {
        style = R.style.Theme_DiceAndCoin2
        recreate()
        diceNum =
            if (binding.numOfDice.text.toString().isNotEmpty()) binding.numOfDice.text.toString()
                .toInt() else 1
        val diceTypeArray = when (diceTypeNum) {
            0 -> DiceActivityDictionary.COIN
            1 -> DiceActivityDictionary.D4
            2 -> DiceActivityDictionary.D6
            else -> DiceActivityDictionary.D6
        }
        val resultToShow = if (!needToCombine) {
            makeDiceResultWithoutCombine(makeResult(diceTypeArray))
        } else {
            makeDiceResultCombine(makeResult(diceTypeArray), diceTypeArray.size)
        }
        binding.result.layoutManager = GridLayoutManager(this, getColumnNumbs(resultToShow.size))
        adapter.addResults(resultToShow)
    }

    private fun makeResult(diceTypeArray: IntArray): IntArray {
        val currentDice = diceTypeArray.clone()
        val result = IntArray(diceNum)
        for (i in result.indices) {
            currentDice.shuffle()
            result[i] = currentDice[random.nextInt(0, currentDice.size - 1)]
        }
        return result
    }

    private fun makeDiceResultWithoutCombine(currentResult: IntArray): ArrayList<Dice> {
        val finResult = ArrayList<Dice>()
        if (needToSort) currentResult.sort()
        currentResult.forEach {
            finResult.add(makeDice(it, 0))
        }
        return finResult
    }

    private fun makeDiceResultCombine(currentResult: IntArray, size: Int): ArrayList<Dice> {
        val finResult = ArrayList<Dice>()
        val res = IntArray(size)
        currentResult.forEach { res[it - 1]++ }
        for (i in res.indices) {
            if (res[i] != 0) {
                finResult.add(makeDice(i + 1, res[i]))
            }
        }
        return finResult
    }

    private fun makeDice(diceResult: Int, count: Int): Dice {
        val diceImage = when (diceTypeNum) {
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
        }

        return Dice(diceImage, count)
    }

    private fun getColumnNumbs(i: Int): Int {
        val columnNumbs =
            when (i) {
                1 -> 1
                in 2..4 -> 2
                in 5..9 -> 3
                in 10..16 -> 4
                in 16..25 -> 5
                else -> 6
            }
        return columnNumbs
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun changeStyle(styleSettings: StyleSettings) {
        binding.apply {
            basicLayout.setBackgroundResource(styleSettings.backgroundLinc)
            leftMenu.setBackgroundResource(styleSettings.backgroundLinc)
            throwDice.setBackgroundResource(styleSettings.buttonDraw)
            throwDice.setTextAppearance(styleSettings.textStyle)
            settingsButton.setBackgroundResource(R.drawable.settings_button_for_green_grace)
            toolbar.setBackgroundColor(getColor(R.color.for_button_for_white_wood_a))
        }
    }
}

