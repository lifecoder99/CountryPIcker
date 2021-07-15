package com.lifecoder.countrypicker.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.lifecoder.countrypicker.R
import com.lifecoder.countrypicker.databinding.CountryCodePickerBinding

/**
 * Created by Arun on 07-07-2021
 */
class CountryCodePicker(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    private val TAG = CountryCodePicker::class.java.simpleName

    private lateinit var mBinding: CountryCodePickerBinding

    private lateinit var mainLytCountryPicker: LinearLayout
    private lateinit var flagImg: ImageView
    private lateinit var selectedCountryTv: TextView
    private lateinit var dialog: CountryCodeDialog

    init {
        init()
    }

    private fun init() {
        val view = View.inflate(context, R.layout.country_code_picker, this)
        mainLytCountryPicker = view.findViewById(R.id.main_lyt_country_picker)
        flagImg = view.findViewById<ImageView>(R.id.flag_imv)
        selectedCountryTv = view.findViewById<TextView>(R.id.selected_country_tv)
        mainLytCountryPicker.setOnClickListener {
             dialog = CountryCodeDialog(context) { country ->
                selectedCountryTv.text = "${country.name}"
                flagImg.setImageDrawable(ContextCompat.getDrawable(context, country.flagId))
                dialog.dismiss()
            }
            dialog.show()
        }

    }

}
