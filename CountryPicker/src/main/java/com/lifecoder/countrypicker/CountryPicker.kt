package com.lifecoder.countrypicker

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.gson.Gson
import com.lifecoder.countrypicker.databinding.CountryCodePickerBinding
import com.nbt.blytics.modules.editaddress.model.CountriesStates
import java.io.IOException

/**
 * Created by Arun on 07-07-2021
 */
class CountryPicker(val _context: Context, val attrs: AttributeSet) :
    LinearLayout(_context, attrs) {
    private val TAG = CountryPicker::class.java.simpleName

    private lateinit var mBinding: CountryCodePickerBinding

    private lateinit var mainLytCountryPicker: LinearLayout
    private lateinit var arrow_imv: ImageView
    private lateinit var selectedCountryTv: TextView
    private lateinit var dialog: CountryDialog
    private var _callbackSelectCountry: CallbackCountrySelected? = null
    var selectCountryStateData: CountriesStates.Country? = null
    private var isShowCountryCode: Boolean = false

    init {
        init()
    }

    private fun init() {
        val view = View.inflate(_context, R.layout.country_picker, this)
        mainLytCountryPicker = view.findViewById(R.id.main_lyt_country_picker)
        arrow_imv = view.findViewById<ImageView>(R.id.arrow_imv)
        selectedCountryTv = view.findViewById<TextView>(R.id.selected_country_tv)
        mainLytCountryPicker.setOnClickListener {
            dialog = CountryDialog(_context) { country ->
                selectedCountryTv.text = country.countryName
                dialog.dismiss()
                _callbackSelectCountry?.let {
                    it.selectedCountry(country)
                    selectCountryStateData = country
                }

            }
            dialog.show()
        }
        fillData()
        applyCustomProperty(attrs)

    }

    fun setOnCountryChangeListener(callbackCountrySelected: CallbackCountrySelected) {
        _callbackSelectCountry = callbackCountrySelected
    }


    private fun fillData() {
        val gson = Gson()
        val countryStateJson = getJsonDataFromAsset(_context, "countries_states_city.json")
        countryState =
            gson.fromJson(countryStateJson, CountriesStates::class.java)
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
        val theme = _context.theme
        val a = theme.obtainStyledAttributes(attrs, R.styleable.CountryCodePicker, 0, 0)
        try {
            val defaultCountryNameCode =
                a.getString(R.styleable.CountryCodePicker_ccp_defaultNameCode)
            isShowCountryCode =
                a.getBoolean(R.styleable.CountryCodePicker_ccp_showCountryCode, false)
            val isHideDropDown = a.getBoolean(R.styleable.CountryCodePicker_ccp_hideDropDown, false)
            val isHideFlag = a.getBoolean(R.styleable.CountryCodePicker_ccp_hideFlag, false)
            if (isHideDropDown) {
                arrow_imv.visibility = View.GONE
            } else {
                arrow_imv.visibility = View.VISIBLE
            }

            /*     for(i in countryState.countries.indices){
                     if(countryState.countries[i].c.contains(defaultCountryNameCode.toString(), true)){
                         countryArray[i].apply{
                             selectCountryData = this
                             flagImg.setImageDrawable(ContextCompat.getDrawable(_context,flagId))
                             if(isShowCountryCode){
                                 selectedCountryTv.text="$dial_code"
                             }else{
                                 selectedCountryTv.text=name
                             }


                         }

                     }
                 }*/
        } catch (ex: Exception) {

        }
    }

    companion object {
        lateinit var countryState: CountriesStates
    }
}
