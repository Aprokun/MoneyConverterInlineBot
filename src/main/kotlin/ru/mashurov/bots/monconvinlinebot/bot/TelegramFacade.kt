package ru.mashurov.bots.monconvinlinebot.bot

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle

@Component
class TelegramFacade {
    @Autowired
    lateinit var changer: CurrencyChanger

    fun handleInlineQuery(update: Update): BotApiMethod<*>? = if (update.hasInlineQuery()) {
        val inlQueryParams = update.inlineQuery.query.split(" ")
        val queryOffset: Int = if (update.inlineQuery.offset.isEmpty()) 0 else update.inlineQuery.offset.toInt()
        if (inlQueryParams.size == 2 && inlQueryParams[1].length == 3) {
            AnswerInlineQuery(
                update.inlineQuery.id,
                getResultWithOffset(
                    queryOffset,
                    usdMode = (inlQueryParams[1] == "usd"),
                    inlQueryParams = inlQueryParams
                )
            )
        } else null
    } else null

    fun getResultWithOffset(
        start: Int,
        size: Int = 50,
        usdMode: Boolean,
        inlQueryParams: List<String>,
    ): List<InlineQueryResultArticle> =
        if (!usdMode) {
            val overallItems = changer.change(inlQueryParams[0].toDouble(), inlQueryParams[1])
            if (start >= overallItems.size) mutableListOf()
            else if (start + size >= overallItems.size) overallItems.subList(start, overallItems.size + 1)
            else overallItems.subList(start, size)
        } else {
            val overallItems = changer.change(inlQueryParams[0].toDouble())
            if (start >= overallItems.size) mutableListOf()
            else if (start + size >= overallItems.size) overallItems.subList(start, overallItems.size + 1)
            else overallItems.subList(start, start + size)
        }
}