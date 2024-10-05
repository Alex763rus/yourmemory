package org.example.tgcommons.model.wrapper;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.val;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import static org.example.tgcommons.constant.Constant.Telegramm.PARSE_MODE;

@Getter
@SuperBuilder(setterPrefix = "set", builderMethodName = "init", toBuilder = true)
public class SendMessageWrap extends MessageWrapBase {

    private String text;
    private InlineKeyboardMarkup inlineKeyboardMarkup;

    @Override
    public SendMessage createMessage() {
        val sendMessage = new SendMessage();
        sendMessage.setChatId(getChaIDString());
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        sendMessage.setParseMode(PARSE_MODE);
        return sendMessage;
    }
}
