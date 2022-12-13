package sapegin.anton.diceandcoin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sapegin.anton.diceandcoin.R
import sapegin.anton.diceandcoin.databinding.DiceResultBinding
import sapegin.anton.diceandcoin.models.Dice

class DiceAdapter : RecyclerView.Adapter<DiceAdapter.DiceHolder>() {
    private val diceList = ArrayList<Dice>()

    class DiceHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = DiceResultBinding.bind(item)
        fun bind(dice: Dice) = with(binding) {
            diceResult.setImageResource(dice.diceImage)
            when (dice.count) {
                0 -> diceCount.text = ""
                else -> diceCount.text = dice.count.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiceHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dice_result, parent, false)
        return DiceHolder(view)
    }

    override fun onBindViewHolder(holder: DiceHolder, position: Int) {
        holder.bind(diceList[position])
    }

    override fun getItemCount(): Int {
        return diceList.size
    }

    fun addResults(resultList: ArrayList<Dice>) {
        diceList.addAll(resultList)
        notifyDataSetChanged()
    }

    fun clearResult() {
        diceList.clear()
        notifyDataSetChanged()
    }
}