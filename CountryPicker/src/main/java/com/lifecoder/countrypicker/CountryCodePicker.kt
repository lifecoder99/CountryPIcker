package com.lifecoder.countrypicker

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.lifecoder.countrypicker.databinding.CountryCodePickerBinding
import java.io.IOException
import java.lang.Exception
import java.util.*

/**
 * Created by Arun on 07-07-2021
 */
class CountryCodePicker(context: Context, val attrs: AttributeSet) : LinearLayout(context, attrs) {
    private val TAG = CountryCodePicker::class.java.simpleName

    private lateinit var mBinding: CountryCodePickerBinding

    private lateinit var mainLytCountryPicker: LinearLayout
    private lateinit var flagImg: ImageView
    private lateinit var selectedCountryTv: TextView
    private lateinit var dialog: CountryCodeDialog
    private var _callbackSelectCountry: CallbackSelectCountry? = null
    var selectCountryData:Country? =null

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
                _callbackSelectCountry?.let {
                    it.selectedCountry(country)
                    selectCountryData= country
                }

            }
            dialog.show()
        }
        fillData()
        applyCustomProperty(attrs)

    }

    fun setOnCountryChangeListener(callbackSelectCountry: CallbackSelectCountry) {
        _callbackSelectCountry = callbackSelectCountry
    }


    private fun fillData() {
        val country = getJsonDataFromAsset(context, "countrycodes.json")
        val gson = Gson()
        countryArray =
            gson.fromJson(country, Array<Country>::class.java)
        for (i in countryArray.indices) {
            val drawableId =
                if (countryArray[i].code.lowercase(Locale.ENGLISH) == "do") resources.getIdentifier(
                    countryArray[i].code.lowercase(Locale.ENGLISH) + "1",
                    "drawable",
                    context.packageName
                )
                else resources.getIdentifier(
                    countryArray[i].code.lowercase(Locale.ENGLISH),
                    "drawable",
                    context.packageName
                )
            countryArray[i].flagId = drawableId
        }
    }

    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    private fun applyCustomProperty(attrs: AttributeSet) {
        val theme = context.theme
        val a = theme.obtainStyledAttributes(attrs, R.styleable.CountryCodePicker, 0, 0)
        try {
           val defaultCountryNameCode = a.getString(R.styleable.CountryCodePicker_ccp_defaultNameCode)
            for(i in countryArray.indices){
                if(countryArray[i].code.contains(defaultCountryNameCode.toString(), true)){
                    countryArray[i].apply{
                        selectCountryData = this
                        selectedCountryTv.text=name
                        flagImg.setImageDrawable(ContextCompat.getDrawable(context,flagId))
                    }

                }
            }
        }catch (ex:Exception){

        }
    }

    companion object {
        lateinit var countryArray: Array<Country>
    }
}
