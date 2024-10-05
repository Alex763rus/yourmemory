package org.example.tgcommons.model.wrapper;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.val;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import static org.example.tgcommons.constant.Constant.Telegramm.PARSE_MODE;

@Getter
@SuperBuilder(setterPrefix = "set", builderMethodName = "init", toBuilder = true)
public class SendPhotoWrapper extends MessageWrapBase {

    private String caption;
    private InputFile photo;
    private InlineKeyboardMarkup inlineKeyboardMarkup;

    @Override
    public SendPhoto createMessage() {
        val sendPhoto = new SendPhoto();
        sendPhoto.setChatId(getChaIDString());
        sendPhoto.setReplyMarkup(inlineKeyboardMarkup);
        sendPhoto.setParseMode(PARSE_MODE);
        sendPhoto.setPhoto(photo);
        sendPhoto.setCaption(caption);
        return sendPhoto;
    }
}
