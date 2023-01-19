package sapegin.anton.diceandcoin.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sapegin.anton.diceandcoin.R
import sapegin.anton.diceandcoin.databinding.DiceColorBinding
import sapegin.anton.diceandcoin.models.DiceColor

class DiceColorAdapter : RecyclerView.Adapter<DiceColorAdapter.DiceColorViewHolder>() {
    private val colorsList = ArrayList<DiceColor>()

    class DiceColorViewHolder(item : View) : RecyclerView.ViewHolder(item){
        private val binding = DiceColorBinding.bind(item)

        internal fun bind(diceColor: DiceColor) = with(binding){
            color.setBackgroundResource(diceColor.color)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiceColorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dice_color, parent, false)
        return DiceColorViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiceColorViewHolder, position: Int) {
        holder.bind(colorsList[position])
    }

    override fun getItemCount(): Int {
        return colorsList.size
    }

    fun addResult(listToShow: ArrayList<DiceColor>){
        colorsList.addAll(listToShow)
        notifyDataSetChanged()
    }
}