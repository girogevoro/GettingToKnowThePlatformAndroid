package com.girogevoro.view;

public class PressedButton {
    private final ButtonCalculator mButtonCalculator;
    private final TypeButtonCalculator mTypeButton;

    public PressedButton(ButtonCalculator buttonCalculator, TypeButtonCalculator typeButton) {
        mButtonCalculator = buttonCalculator;
        mTypeButton = typeButton;
    }

    public ButtonCalculator getButtonCalculator() {
        return mButtonCalculator;
    }

    public TypeButtonCalculator getTypeButton() {
        return mTypeButton;
    }
}
