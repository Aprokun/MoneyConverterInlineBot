package ru.mashurov.bots.monconvinlinebot.bot

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle

@Component
class CurrencyChanger {
    @Autowired
    private lateinit var currencyParser: CurrencyParser

    fun change(valFrom: Double, curFrom: String): List<InlineQueryResultArticle> {
        val allCurrencies = currencyParser.getListAllCurrencyValues(curFrom)
        val result = mutableListOf<InlineQueryResultArticle>()
        for ((id, cur) in allCurrencies.withIndex()) {
            result.add(InlineQueryResultArticle(
                id.toString(), cur.name, InputTextMessageContent(cur.convert(valFrom).toString())
            ))
        }
        return result
    }
}