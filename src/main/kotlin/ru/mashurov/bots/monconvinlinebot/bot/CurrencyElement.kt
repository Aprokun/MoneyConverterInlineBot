package ru.mashurov.bots.monconvinlinebot.bot

import java.io.Serializable

data class CurrencyElement(
    val name: String,
    val code: String,
    val value: Double,
) : Serializable {
    fun convert(valueForConvert: Double) = valueForConvert * value
}
