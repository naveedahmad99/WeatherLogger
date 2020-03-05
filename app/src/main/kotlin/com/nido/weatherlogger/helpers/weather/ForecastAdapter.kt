package com.nido.weatherlogger.helpers.weather

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nido.weatherlogger.R
import com.nido.weatherlogger.databinding.ItemCityCardBinding

class ForecastAdapter(private val itemList: Collection<Forecast>) :
    RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    companion object {
        private lateinit var recyclerView: RecyclerView
    }

    override fun onAttachedToRecyclerView(_recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(_recyclerView)
        recyclerView = _recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ForecastViewHolder.from(parent)

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) =
        holder.bind(itemList.elementAt(position))

    class ForecastViewHolder private constructor(private val binding: ItemCityCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Forecast) {
            val iconTint = ContextCompat.getColor(binding.cityImage.context, R.color.grayIconTint)
            binding.cityImage.setColorFilter(iconTint)
            binding.cityImage.setImageResource(item.cityIcon)
            binding.dataCityName = item.cityName

            binding.container.setOnClickListener {
                recyclerView.smoothScrollToPosition(adapterPosition)
            }

            /**
             * causes the properties updates to execute immediately.
             * since I'm calling [bind] from [onBindViewHolder]
             * having the bindings execute immediately. as a practice
             * can prevent the recycler view from having to perform
             * extra calculations when it figures out how to display
             * the list.
             */
            binding.executePendingBindings()
        }

        fun showItem() {
            val parentHeight = (binding.cityImage.parent as View).height
            val scale = (parentHeight - binding.cityName.height) / binding.cityImage.height.toFloat()
            binding.cityImage.pivotX = binding.cityImage.width * 0.5f
            binding.cityImage.pivotY = 0f
            binding.cityImage.animate().scaleX(scale)
                .withEndAction {
                    binding.cityName.visibility = View.VISIBLE
                    binding.cityImage.setColorFilter(Color.BLACK)
                }
                .scaleY(scale).setDuration(200)
                .start()
        }

        fun hideItem() {
            binding.cityImage.setColorFilter(ContextCompat.getColor(binding.cityImage.context, R.color.grayIconTint))
            binding.cityName.visibility = View.INVISIBLE
            binding.cityImage.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(200)
                .start()
        }

        companion object {
            fun from(parent: ViewGroup): ForecastViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCityCardBinding.inflate(layoutInflater, parent, false)

                return ForecastViewHolder(binding)
            }
        }
    }
}
