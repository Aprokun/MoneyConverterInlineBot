package ru.mashurov.bots.monconvinlinebot.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook
import ru.mashurov.bots.monconvinlinebot.bot.Bot
import ru.mashurov.bots.monconvinlinebot.bot.BotProperties
import ru.mashurov.bots.monconvinlinebot.bot.TelegramFacade

@Configuration
class BeanConfiguration {
    @Bean
    fun botProperties(): BotProperties = BotProperties()

    @Bean
    fun setWebhookInstance(botProperties: BotProperties): SetWebhook = SetWebhook(botProperties.webhookPath)

    @Bean
    fun bot(setWebhook: SetWebhook, telegramFacade: TelegramFacade, botProperties: BotProperties): Bot {
        val bot = Bot(setWebhook, telegramFacade)
        bot.path = botProperties.webhookPath
        bot.name = botProperties.name
        bot.token = botProperties.token
        return bot
    }

}