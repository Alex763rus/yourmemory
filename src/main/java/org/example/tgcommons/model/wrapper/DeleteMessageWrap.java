package org.example.tgcommons.model.wrapper;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.val;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
@Getter
@SuperBuilder(setterPrefix = "set", builderMethodName = "init", toBuilder = true)
public class DeleteMessageWrap extends MessageWrapBase {

    private Integer messageId;
    @Override
    public DeleteMessage createMessage() {
        val deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(getChaIDString());
        deleteMessage.setMessageId(messageId);
        return deleteMessage;
    }
}
