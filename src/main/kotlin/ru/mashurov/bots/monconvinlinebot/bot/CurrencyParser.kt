package ru.mashurov.bots.monconvinlinebot.bot

import org.jsoup.Jsoup
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class CurrencyParser {
    @Value("\${source.currency.data.url}")
    private lateinit var url: String

    fun getListAllCurrencyValues(curFrom: String): List<CurrencyElement> {
        val document = Jsoup.connect("$url$curFrom").get()
        val allCurrencyElements = document.select("div.item.row").subList(0, 20)

        val allCurrencies = mutableListOf<CurrencyElement>()
        for (elem in allCurrencyElements) {
            val name = elem.children()[0].text().toString()
            val code = elem.children()[1].text().toString()
            val value = elem.children()[3].children()[0]
                .text()
                .toString()
                .replace(",", ".")
                .replace(" ", "")
                .toDouble()

            allCurrencies.add(CurrencyElement(name, code, value))
        }

        return allCurrencies
    }

}