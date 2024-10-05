package org.example.tgcommons.model.button;


import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(setterPrefix = "set", builderMethodName = "init", toBuilder = true)
public class Day {

    private final String key;
    private final String value;

}
