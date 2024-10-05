package org.example.tgcommons.model.wrapper;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.val;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

import static org.example.tgcommons.constant.Constant.Telegramm.PARSE_MODE;

@Getter
@SuperBuilder(setterPrefix = "set", builderMethodName = "init", toBuilder = true)
public class SendDocumentWrap extends MessageWrapBase {

    private InputFile document;
    private String caption;
    private InlineKeyboardMarkup inlineKeyboardMarkup;

    @Override
    public SendDocument createMessage() {
        val sendDocument = new SendDocument();
        sendDocument.setChatId(getChaIDString());
        sendDocument.setReplyMarkup(inlineKeyboardMarkup);
        sendDocument.setParseMode(PARSE_MODE);
        sendDocument.setDocument(document);
        sendDocument.setCaption(caption);
        return sendDocument;
    }

}
