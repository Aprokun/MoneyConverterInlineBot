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

        val curFromUppercase = curFrom.uppercase(Locale.ENGLISH)
        val dollarToCurFrom = allDollarCurrencies[curFromUppercase]!!.value
        val result = mutableListOf<InlineQueryResultArticle>()

        for ((id, key) in allDollarCurrencies.keys.minus(curFromUppercase).withIndex()) {
            val currency = allDollarCurrencies[key]!!
            val dollarToCurTo = currency.value
            val crossCurrency = dollarToCurTo / dollarToCurFrom
            result.add(InlineQueryResultArticle(
                id.toString(),
                currency.name,
                InputTextMessageContent(
                    String.format("%.2f ", valFrom * crossCurrency) +
                            key.lowercase(Locale.ENGLISH)
                )
            ))
        }

        return result
    }

    fun change(valFrom: Double): List<InlineQueryResultArticle> {
        val allDollarCurrencies = currencyParser.getListAllCurrencyValues()

        val result = mutableListOf<InlineQueryResultArticle>()
        for ((id, key) in allDollarCurrencies.keys.minus("USD").withIndex()) {
            val currency = allDollarCurrencies[key]!!
            result.add(InlineQueryResultArticle(
                id.toString(),
                currency.name,
                InputTextMessageContent(String.format("%.2f ", currency.convert(valFrom)) +
                        key.lowercase(Locale.ENGLISH)
                )
            ))
        }

        return result
    }
}