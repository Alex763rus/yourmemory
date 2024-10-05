package org.example.tgcommons.model.button;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;

@Getter
@SuperBuilder(setterPrefix = "set", builderMethodName = "init", toBuilder = true)
public class ButtonsDescription {

    private final int countColumn;

    private final ArrayList<Button> buttons;

}
