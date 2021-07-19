package com.lifecoder.countrypicker

import com.lifecoder.countrypicker.models.Country
import com.nbt.blytics.modules.editaddress.model.CountriesStates

/**
 * Created by Arun on 19-07-2021
 */
interface CallbackSelectCountry {
    fun selectedCountry(country: Country)
}
interface CallbackCountrySelected{
    fun selectedCountry(country: CountriesStates.Country)
}

interface CallbackSelectState {
    fun selectedState(state: CountriesStates.Country.State)
}