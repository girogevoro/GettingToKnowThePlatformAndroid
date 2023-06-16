package com.girogevoro.presenter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.girogevoro.model.CalculatorModel;
import com.girogevoro.view.ButtonCalculator;
import com.girogevoro.view.CalculatorView;
import com.girogevoro.view.PressedButton;
import com.girogevoro.view.TypeButtonCalculator;

import org.junit.Before;
import org.junit.Test;

public class CalculatorPresenterTest {

    public CalculatorPresenter mCalculatorPresenter;
    public CalculatorView mCalculatorViewMock;
    public CalculatorModel mCalculatorModelMock;

    @Before
    public void setUp() {
        mCalculatorViewMock = mock(CalculatorView.class);
        mCalculatorModelMock = mock(CalculatorModel.class);
        mCalculatorPresenter = new CalculatorPresenter(mCalculatorViewMock, mCalculatorModelMock);
    }

    @Test
    public void testPresenterAdd() {

        PressedButton pressedButtonMock = mock(PressedButton.class);
        when(pressedButtonMock.getButtonCalculator()).thenReturn(ButtonCalculator.KEY_3);
        when(pressedButtonMock.getTypeButton()).thenReturn(TypeButtonCalculator.DIGITAL);
        mCalculatorPresenter.click(pressedButtonMock);
        verify(mCalculatorViewMock, times(1)).showResult(3.0);

        pressedButtonMock = mock(PressedButton.class);
        when(pressedButtonMock.getButtonCalculator()).thenReturn(ButtonCalculator.KEY_ADD);
        when(pressedButtonMock.getTypeButton()).thenReturn(TypeButtonCalculator.OPERATION);
        mCalculatorPresenter.click(pressedButtonMock);
        verify(mCalculatorViewMock, times(2)).showResult(3.0);

        pressedButtonMock = mock(PressedButton.class);
        when(pressedButtonMock.getButtonCalculator()).thenReturn(ButtonCalculator.KEY_5);
        when(pressedButtonMock.getTypeButton()).thenReturn(TypeButtonCalculator.DIGITAL);
        mCalculatorPresenter.click(pressedButtonMock);
        verify(mCalculatorViewMock, times(1)).showResult(5.0);

        pressedButtonMock = mock(PressedButton.class);
        when(pressedButtonMock.getButtonCalculator()).thenReturn(ButtonCalculator.KEY_EQUAL);
        when(pressedButtonMock.getTypeButton()).thenReturn(TypeButtonCalculator.OPERATION);
        when(mCalculatorModelMock.add(3, 5)).thenReturn(8.0);
        mCalculatorPresenter.click(pressedButtonMock);
        verify(mCalculatorViewMock).showResult(8.0);
    }

    @Test
    public void testPresenterSub() {
        when(mCalculatorModelMock.sub(3, 5)).thenReturn(-2.0);

        PressedButton pressedButtonMock = mock(PressedButton.class);
        when(pressedButtonMock.getButtonCalculator())
                .thenReturn(ButtonCalculator.KEY_3);
        when(pressedButtonMock.getTypeButton())
                .thenReturn(TypeButtonCalculator.DIGITAL,
                        TypeButtonCalculator.OPERATION,
                        TypeButtonCalculator.DIGITAL,
                        TypeButtonCalculator.OPERATION);
        mCalculatorPresenter.click(pressedButtonMock);

        when(pressedButtonMock.getButtonCalculator())
                .thenReturn(ButtonCalculator.KEY_SUBTRACT);
        mCalculatorPresenter.click(pressedButtonMock);

        when(pressedButtonMock.getButtonCalculator())
                .thenReturn(ButtonCalculator.KEY_5);
        mCalculatorPresenter.click(pressedButtonMock);

        when(pressedButtonMock.getButtonCalculator())
                .thenReturn(ButtonCalculator.KEY_EQUAL);
        mCalculatorPresenter.click(pressedButtonMock);

        verify(mCalculatorViewMock).showResult(-2.0);
    }

    @Test
    public void testPresenterMul() {
        when(mCalculatorModelMock.mul(3, 5)).thenReturn(15.0);

        PressedButton pressedButtonMock = mock(PressedButton.class);
        when(pressedButtonMock.getButtonCalculator())
                .thenReturn(ButtonCalculator.KEY_3);
        when(pressedButtonMock.getTypeButton())
                .thenReturn(TypeButtonCalculator.DIGITAL,
                        TypeButtonCalculator.OPERATION,
                        TypeButtonCalculator.DIGITAL,
                        TypeButtonCalculator.OPERATION);
        mCalculatorPresenter.click(pressedButtonMock);

        when(pressedButtonMock.getButtonCalculator())
                .thenReturn(ButtonCalculator.KEY_MULTIPLICATION);
        mCalculatorPresenter.click(pressedButtonMock);

        when(pressedButtonMock.getButtonCalculator())
                .thenReturn(ButtonCalculator.KEY_5);
        mCalculatorPresenter.click(pressedButtonMock);

        when(pressedButtonMock.getButtonCalculator())
                .thenReturn(ButtonCalculator.KEY_EQUAL);
        mCalculatorPresenter.click(pressedButtonMock);

        verify(mCalculatorViewMock).showResult(15.0);
    }

    @Test
    public void testPresenterDiv() {
        when(mCalculatorModelMock.div(3, 5)).thenReturn(0.6);

        PressedButton pressedButtonMock = mock(PressedButton.class);
        when(pressedButtonMock.getButtonCalculator())
                .thenReturn(ButtonCalculator.KEY_3);
        when(pressedButtonMock.getTypeButton())
                .thenReturn(TypeButtonCalculator.DIGITAL,
                        TypeButtonCalculator.OPERATION,
                        TypeButtonCalculator.DIGITAL,
                        TypeButtonCalculator.OPERATION);

        mCalculatorPresenter.click(pressedButtonMock);
        when(pressedButtonMock.getButtonCalculator())
                .thenReturn(ButtonCalculator.KEY_DIVISION);

        mCalculatorPresenter.click(pressedButtonMock);
        when(pressedButtonMock.getButtonCalculator())
                .thenReturn(ButtonCalculator.KEY_5);
        mCalculatorPresenter.click(pressedButtonMock);

        when(pressedButtonMock.getButtonCalculator())
                .thenReturn(ButtonCalculator.KEY_EQUAL);
        mCalculatorPresenter.click(pressedButtonMock);

        verify(mCalculatorViewMock).showResult(0.6);
    }

    @Test
    public void testPresenterDot() {
        PressedButton pressedButtonMock = mock(PressedButton.class);
        when(pressedButtonMock.getButtonCalculator())
                .thenReturn(ButtonCalculator.KEY_3);
        when(pressedButtonMock.getTypeButton())
                .thenReturn(TypeButtonCalculator.DIGITAL);
        mCalculatorPresenter.click(pressedButtonMock);

        when(pressedButtonMock.getButtonCalculator())
                .thenReturn(ButtonCalculator.KEY_DOT);
        when(pressedButtonMock.getTypeButton())
                .thenReturn(TypeButtonCalculator.DOT);
        mCalculatorPresenter.click(pressedButtonMock);

        when(pressedButtonMock.getButtonCalculator())
                .thenReturn(ButtonCalculator.KEY_5);
        when(pressedButtonMock.getTypeButton())
                .thenReturn(TypeButtonCalculator.DIGITAL);
        mCalculatorPresenter.click(pressedButtonMock);

        verify(mCalculatorViewMock).showResult(3.5);
    }

    @Test
    public void testPresenterSing() {
        PressedButton pressedButtonMock = mock(PressedButton.class);
        when(pressedButtonMock.getButtonCalculator())
                .thenReturn(ButtonCalculator.KEY_3);
        when(pressedButtonMock.getTypeButton())
                .thenReturn(TypeButtonCalculator.DIGITAL);
        mCalculatorPresenter.click(pressedButtonMock);

        when(pressedButtonMock.getButtonCalculator())
                .thenReturn(ButtonCalculator.KEY_SING);
        when(pressedButtonMock.getTypeButton())
                .thenReturn(TypeButtonCalculator.SING);
        mCalculatorPresenter.click(pressedButtonMock);

        verify(mCalculatorViewMock).showResult(-3.0);
    }
}