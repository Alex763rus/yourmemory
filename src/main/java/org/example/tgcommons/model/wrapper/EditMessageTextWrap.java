package org.example.tgcommons.model.wrapper;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.val;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

import java.util.List;

import static org.example.tgcommons.constant.Constant.Telegramm.PARSE_MODE;

@Getter
@SuperBuilder(setterPrefix = "set", builderMethodName = "init", toBuilder = true)
public class EditMessageTextWrap extends MessageWrapBase {

    private Integer messageId;
    private String text;

    @Override
    public PartialBotApiMethod createMessage() {
        val editMessageText = new EditMessageText();
        editMessageText.setMessageId(messageId);
        editMessageText.setChatId(getChaIDString());
        editMessageText.setText(text);
        editMessageText.setParseMode(PARSE_MODE);
        return editMessageText;
    }
}
