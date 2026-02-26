# MVP Level 1 реализация микросервиса Калькулятор
## Реализация

Добавлено логирование для расчета возможных предложений и всегосвязаного с ними, 
для этого применил следующие стратегию: DTO объекты и результаты расчетов логируются на уровне debug,
оповещение о начале/окончании расчетаа - на уровне info. Логи сохраняются в файл [app.log](app.log).
Логирование выполнено с помощью logback + Slf4j.

Прескоринг реализован на уровне валидации и при неудаче МС возвращает [ErrorMessageDto](src/main/java/calculator/dto/response/ErrorMessageDto.java)
c возможной причиной. На основании [LoanStatementRequestDto](src/main/java/calculator/dto/request/LoanStatementRequestDto.java) 
происходит прескоринг, создаётся 4 кредитных предложения [LoanOfferDto](src/main/java/calculator/dto/response/LoanOfferDto.java)
на основании всех возможных комбинаций булевских полей isInsurance и isSalaryClient (false-false, false-true, true-false, true-true).

Используется метод расчета с аннуитетными выплатами(ежемесячные выплаты одинаковы).
При скоринге данных в класса [AnnuityCreditCalculator](src/main/java/calculator/service/creditCalculator/AnnuityCreditCalculator.java)
вычисляется итоговая ставки(rate), полная стоимости кредита(psk), размер ежемесячного 
платежа(monthlyPayment), график ежемесячных платежей (List<PaymentScheduleElementDto>)
Скоринг реализован в сервисном слое и при неудаче МС возвращает [ErrorMessageDto](src/main/java/calculator/dto/response/ErrorMessageDto.java)
с ошибкой [ScoringException](src/main/java/calculator/exceptions/ScoringException.java), причина, в отличие от прескоринга
в мессендж не передается, а лишь логируется. Фильтры поделены на hard, отказывающие при невыполнении условий фильтров, и
soft, изменяющие процентную ставку и добавляющие стоимость доп. услуг (страховка).

Отчеты по покрытию тестами составляет 97%.

Ссылка на Сваггер http://localhost:8080/calculator/swagger-ui/index.htm <br/>
Ссылка на OAS http://localhost:8080/calculator/api-docs