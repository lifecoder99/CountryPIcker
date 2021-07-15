package com.lifecoder.countrypicker

/**
 * Created by Arun on 07-07-2021
 */
data class Country(
    val name: String,
    val dial_code: String,
    val code: String,
    var flagId:Int =-1
){
    override fun toString(): String {
        return "$name, $dial_code, $code"
    }
}