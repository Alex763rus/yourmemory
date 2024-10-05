package org.example.tgcommons.model.wrapper;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.val;
import org.telegram.telegrambots.meta.api.methods.ForwardMessage;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;

import java.util.List;

@Getter
@SuperBuilder(setterPrefix = "set", builderMethodName = "init", toBuilder = true)
public class ForwardMessageWrap extends MessageWrapBase {

    private Long chatIdFromLong;
    private Integer messageId;

    @Override
    public ForwardMessage createMessage() {
        val forwardMessage = new ForwardMessage();
        forwardMessage.setChatId(getChatIdString());
        forwardMessage.setFromChatId(getChaIDString());
        forwardMessage.setMessageId(messageId);
        return forwardMessage;
    }
}
