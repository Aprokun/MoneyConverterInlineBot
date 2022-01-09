package ru.mashurov.bots.monconvinlinebot.bot

data class CurrencyElement(
    val name: String,
    val code: String,
    val value: Double,
) {
    fun convert(valueForConvert: Double) = valueForConvert * value
}
