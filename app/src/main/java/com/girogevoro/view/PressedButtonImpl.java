package com.girogevoro.view;

public class PressedButtonImpl implements PressedButton{
    private final ButtonCalculator mButtonCalculator;
    private final TypeButtonCalculator mTypeButton;

    public PressedButtonImpl(ButtonCalculator buttonCalculator, TypeButtonCalculator typeButton) {
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
