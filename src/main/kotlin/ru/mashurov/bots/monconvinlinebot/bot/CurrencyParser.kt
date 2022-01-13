package ru.mashurov.bots.monconvinlinebot.bot

import org.jsoup.Jsoup
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.io.*

@Component
class CurrencyParser {
    @Value("\${source.currency.data.url}")
    private lateinit var url: String

    @Value("\${source.currency.data.file}")
    private lateinit var fileName: String

    fun getListAllCurrencyValues(): List<CurrencyElement> = readAllDollarCurrencies()

    @Scheduled(fixedDelay = 1800000)
    private fun writeAllDollarCurrencies() {
        val document = Jsoup.connect(url).get()
        val allCurrencyElements = document.select("div.item.row").subList(0, 20)

        val allCurrencies = mutableListOf<CurrencyElement>()
        for (elem in allCurrencyElements) {
            val name = elem.children()[0].text()
            val code = elem.children()[1].text()
            val value = elem.children()[3].children()[0]
                .text()
                .replace(",", ".")
                .replace(" ", "")
                .toDouble()

            allCurrencies.add(CurrencyElement(name, code, value))
        }

        val file = File(fileName)
        file.createNewFile()

        FileOutputStream(file).use { fos ->
            ObjectOutputStream(fos).use { oos ->
                for (curr in allCurrencies) {
                    oos.writeObject(curr)
                }
            }
        }
    }

    private fun readAllDollarCurrencies(): List<CurrencyElement> {
        val allCurrencies = mutableListOf<CurrencyElement>()
        FileInputStream(File(fileName)).use { fin ->
            ObjectInputStream(fin).use { ois ->
                var cnt = 20
                while (cnt-- > 0) allCurrencies.add(ois.readObject() as CurrencyElement)
            }
        }

        return allCurrencies
    }

}