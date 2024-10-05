package org.example.tgcommons.model.wrapper;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(setterPrefix = "set", builderMethodName = "init", toBuilder = true)
public abstract class MessageWrapBase implements MessageCreator{

    private String chatIdString;
    private Long chatIdLong;

    protected String getChaIDString(){
        return chatIdString == null ? String.valueOf(chatIdLong) : chatIdString;
    }
}
