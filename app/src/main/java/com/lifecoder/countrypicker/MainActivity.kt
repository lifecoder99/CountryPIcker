package com.lifecoder.countrypicker

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val picker = findViewById<CountryCodePicker>(R.id.pickerCountry)
        picker.setOnCountryChangeListener(object:CallbackSelectCountry{
            override fun selectedCountry(country: Country) {
                Toast.makeText(this@MainActivity, country.name,Toast.LENGTH_SHORT).show()
            }

        })
        picker.selectCountryData?.let {
            Toast.makeText(this@MainActivity, it.name,Toast.LENGTH_SHORT).show()
        }


        /* val country = getJsonDataFromAsset(this, "countrycodes.json")
         val gson = Gson()
         countryArray =
             gson.fromJson(country, Array<Country>::class.java)
         for (i in countryArray.indices) {
             val drawableId =
                 if (countryArray[i].code.lowercase(Locale.ENGLISH) == "do") resources.getIdentifier(
                     countryArray[i].code.lowercase(Locale.ENGLISH) + "1",
                     "drawable",
                     packageName
                 )
                 else resources.getIdentifier(
                     countryArray[i].code.lowercase(Locale.ENGLISH),
                     "drawable",
                     packageName
                 )
             countryArray[i].flagId = drawableId
         }*/
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

}