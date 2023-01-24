package sapegin.anton.diceandcoin.adapters

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import sapegin.anton.diceandcoin.R
import sapegin.anton.diceandcoin.databinding.BackgroundBinding
import sapegin.anton.diceandcoin.models.StyleSettings

class StyleSettingsAdapter(private val listener: BackgroundListener) : RecyclerView.Adapter<StyleSettingsAdapter.StyleSettingsHolder>() {
    private val styleSettingsList = ArrayList<StyleSettings>()

    class StyleSettingsHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = BackgroundBinding.bind(item)

        @SuppressLint("ResourceAsColor")
        @RequiresApi(Build.VERSION_CODES.M)
        fun bind(styleSettings: StyleSettings, listener: BackgroundListener) = with(binding) {
            backgroundView.setImageResource(styleSettings.backgroundLinc)
            textExample.setTextAppearance(styleSettings.textStyle)
            buttonExample.setBackgroundResource(styleSettings.buttonDraw)
            buttonExample.setTextAppearance(styleSettings.textStyle)
            itemView.setOnClickListener {
                listener.onClick(styleSettings)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StyleSettingsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.background, parent, false)
        return StyleSettingsHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: StyleSettingsHolder, position: Int) {
        holder.bind(styleSettingsList[position], listener)
    }

    override fun getItemCount(): Int {
        return styleSettingsList.size
    }

    fun add(list: Array<StyleSettings>) {
        styleSettingsList.addAll(list)
        notifyDataSetChanged()
    }

    interface BackgroundListener{
        fun onClick(styleSettings: StyleSettings)
    }
}