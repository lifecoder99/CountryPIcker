package com.lifecoder.countrypicker.adpters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.lifecoder.countrypicker.models.Country
import com.lifecoder.countrypicker.R
import com.lifecoder.countrypicker.databinding.RowCountryAdpterBinding
import com.lifecoder.countrypicker.databinding.RowCountryStateAdpterBinding
import com.nbt.blytics.modules.editaddress.model.CountriesStates

/**
 * Created by Arun on 07-07-2021
 */
class CountryStateAdapter (val context:Context,  val  list:MutableList<CountriesStates.Country>, val callback:(Int)->Unit):RecyclerView.Adapter<CountryStateAdapter.CountryViewHolder>(){
   inner class CountryViewHolder(val binding: RowCountryStateAdpterBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<RowCountryStateAdpterBinding>(
            inflater, R.layout.row_country_state_adpter, parent, false
        )
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val data = list[position]

        holder.binding.apply {

            selectedCountryTv.text = data.countryName


        }
        holder.itemView.setOnClickListener {
            callback(position)
        }

    }

    override fun getItemCount(): Int =list.size
}