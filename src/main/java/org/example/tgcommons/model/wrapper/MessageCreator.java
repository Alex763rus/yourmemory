package org.example.tgcommons.model.wrapper;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;

import java.util.List;

public interface MessageCreator {

    PartialBotApiMethod createMessage();

    default List<PartialBotApiMethod> createMessageList() {
        return List.of(createMessage());
    }
}
