package ru.mashurov.bots.monconvinlinebot.bot

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle
import java.util.*

@Component
class CurrencyChanger {
    @Autowired
    private lateinit var currencyParser: CurrencyParser

    fun change(valFrom: Double, curFrom: String): List<InlineQueryResultArticle> {
        val allDollarCurrencies = currencyParser.getListAllCurrencyValues()
        val dollarToCurFrom =
            allDollarCurrencies.find { element -> element.code == curFrom.uppercase(Locale.ENGLISH) }!!.value
        val result = mutableListOf<InlineQueryResultArticle>()

        for ((id, cur) in allDollarCurrencies.withIndex()) {
            val dollarToCurTo = cur.value
            val crossCurrency = dollarToCurTo / dollarToCurFrom
            result.add(InlineQueryResultArticle(
                id.toString(), cur.name, InputTextMessageContent((valFrom * crossCurrency).toString())
            ))
        }

        return result
    }

    fun change(valFrom: Double): List<InlineQueryResultArticle> {
        val allDollarCurrencies = currencyParser.getListAllCurrencyValues()

        val result = mutableListOf<InlineQueryResultArticle>()
        for ((id, cur) in allDollarCurrencies.withIndex()) {
            result.add(InlineQueryResultArticle(
                id.toString(), cur.name, InputTextMessageContent(cur.convert(valFrom).toString())
            ))
        }

        return result
    }
}