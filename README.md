# Money Converter (Inline) Bot

Бот для перевода денег из одной валюты в другую. Поддерживает 167 различных валют.
Для перевода используется **кросс-курс** через доллар.

**Кросс-котировка** - выражение курсов двух валют друг к другу через курс каждой из них по отношению к третьей валюте (обычно доллару США).

Сам бот работает через **Webhook**. Располагается на бесплатном хостинге **Heroku**.

Используемые технологии:
* *Spring Boot* - для DI, Annotation и пр.
* *Jsoup* - для парсинга курса доллара
* *Telegrambots* - сама библиотека для создания бота от команды Telegram
* *Maven* - для сборки проекта, управления зависимостями и пр.

Для тестовых подключений использовал *Ngrok*.

Изначально бот работал на **Long Polling** и при каждом запросе парсил определённые курсы валют. 
Затем было принято решение перевести бот на **Webhook** и использовать кросс-курс доллара для оптимизации запросов к боту
и запросы бота к сайту с валютами.

Теперь бот просто обновляет курсы доллара каждые 30 минут с помощью **@Scheduled** (Spring).
