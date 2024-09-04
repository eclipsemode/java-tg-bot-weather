package ru.daniel.javatgweather.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniel.javatgweather.service.handler.CallbackQueryHandler;
import ru.daniel.javatgweather.service.handler.CommandHandler;
import ru.daniel.javatgweather.service.handler.MessageHandler;
import ru.daniel.javatgweather.telegram.Bot;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UpdateDispatcher {
    CommandHandler commandHandler;
    MessageHandler messageHandler;
    CallbackQueryHandler callbackQueryHandler;

    public BotApiMethod<?> distribute(Update update, Bot bot) {
        if (update.hasCallbackQuery()) {
            return callbackQueryHandler.answer(update.getCallbackQuery(), bot);
        }
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                String text = message.getText();
                if (text.charAt(0) == '/') {
                    return commandHandler.answer(update.getMessage(), bot);
                }
                return messageHandler.answer(update.getMessage(), bot);
            }
        }

        log.warn("Unsupported update type: {}", update);
        return null;
    }
}
