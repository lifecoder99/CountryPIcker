package com.lifecoder.countrypicker

import android.content.Context
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.lifecoder.countrypicker.ui.Country
import java.io.IOException
import java.util.*


class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var countryArray: Array<Country>
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val country = getJsonDataFromAsset(this, "countrycodes.json")
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

}