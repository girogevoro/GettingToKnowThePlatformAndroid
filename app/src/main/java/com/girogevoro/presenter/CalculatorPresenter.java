package com.girogevoro.presenter;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.girogevoro.model.CalculatorModel;
import com.girogevoro.view.ButtonCalculator;
import com.girogevoro.view.CalculatorView;
import com.girogevoro.view.OnClickButtonListener;
import com.girogevoro.view.PressedButton;

import java.util.HashMap;
import java.util.Map;

public class CalculatorPresenter implements OnClickButtonListener {


    private double arg1;
    private double arg2;
    private boolean isDot = false;
    private int countAfterDot = 0;
    private boolean isResultOnDisplay = true;
    private ButtonCalculator lastOperation = ButtonCalculator.NONE;
    private CalculatorView mCalculatorView;
    private CalculatorModel mCalculatorModel;
    private Map<ButtonCalculator, Integer> mapButtonsDigital = new HashMap<>();
    private ButtonCalculator lastButton;


    public CalculatorPresenter(CalculatorView calculatorView, CalculatorModel calculatorModel) {
        mCalculatorView = calculatorView;
        mCalculatorView.setOnClickButtonListener(this);
        initMapButtonsDigital();
        mCalculatorView.showResult(arg2);
        mCalculatorModel = calculatorModel;
    }

    public void setData(CalculatorPresenter.Data data) {
        if (data != null) {
            arg1 = data.getArg1();
            arg2 = data.getArg2();
            isDot = data.getIsDot();
            countAfterDot = data.getCountAfterDot();
            lastOperation = data.getLastOperation();
            lastButton = data.getLastButton();
            isResultOnDisplay = data.getIsResultOnDisplay();
            if (isResultOnDisplay) {
                mCalculatorView.showResult(arg1);
            } else {
                mCalculatorView.showResult(arg2);
            }
        }
    }

    public CalculatorPresenter.Data getData() {
        return new CalculatorPresenter.Data(arg1, arg2, isDot, countAfterDot, lastOperation, isResultOnDisplay, lastButton);
    }

    private void initMapButtonsDigital() {
        mapButtonsDigital.put(ButtonCalculator.KEY_1, 1);
        mapButtonsDigital.put(ButtonCalculator.KEY_2, 2);
        mapButtonsDigital.put(ButtonCalculator.KEY_3, 3);
        mapButtonsDigital.put(ButtonCalculator.KEY_4, 4);
        mapButtonsDigital.put(ButtonCalculator.KEY_5, 5);
        mapButtonsDigital.put(ButtonCalculator.KEY_6, 6);
        mapButtonsDigital.put(ButtonCalculator.KEY_7, 7);
        mapButtonsDigital.put(ButtonCalculator.KEY_8, 8);
        mapButtonsDigital.put(ButtonCalculator.KEY_9, 9);
        mapButtonsDigital.put(ButtonCalculator.KEY_0, 0);
    }

    @Override
    public void click(PressedButton pressedButton) {
        switch (pressedButton.getTypeButton()) {
            case DIGITAL:

                if (lastButton == ButtonCalculator.KEY_EQUAL) {
                    clear();

                } else {
                    if (lastOperation != ButtonCalculator.NONE && arg2 != 0 && isResultOnDisplay) {
                        arg2 = 0;
                        lastOperation = ButtonCalculator.NONE;
                    }
                }

                if (isDot) {
                    countAfterDot--;
                    arg2 = arg2 + Math.pow(10, countAfterDot) * (Integer) mapButtonsDigital.get(pressedButton.getButtonCalculator());
                } else {
                    arg2 = arg2 * 10 + (Integer) mapButtonsDigital.get(pressedButton.getButtonCalculator());
                }
                mCalculatorView.showResult(arg2);
                isResultOnDisplay = false;

                break;
            case OPERATION:
                if (isLastButtonNotOperation()) {
                    switch (lastOperation) {
                        case KEY_ADD:
                            arg1 = mCalculatorModel.add(arg1, arg2);
                            break;
                        case KEY_SUBTRACT:
                            arg1 = mCalculatorModel.sub(arg1, arg2);
                            break;
                        case KEY_MULTIPLICATION:
                            arg1 = mCalculatorModel.mul(arg1, arg2);
                            break;
                        case KEY_DIVISION:
                            arg1 = mCalculatorModel.div(arg1, arg2);
                            break;
                        case NONE:
                            arg1 = arg2;
                            break;
                    }

                    isDot = false;
                    countAfterDot = 0;
                    mCalculatorView.showResult(arg1);
                    isResultOnDisplay = true;
                }

                if (pressedButton.getButtonCalculator() != ButtonCalculator.KEY_EQUAL) {
                    arg2 = 0;
                    lastOperation = pressedButton.getButtonCalculator();
                }

                break;
            case SING:
                if (isResultOnDisplay) {
                    arg2 = -1 * arg1;
                    arg1 = 0;
                    isResultOnDisplay = false;
                } else {
                    arg2 *= -1;
                }
                mCalculatorView.showResult(arg2);
                break;
            case DOT:
                Log.d("viewCalculator", "click: dot " + pressedButton.getButtonCalculator());
                isDot = true;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + pressedButton.getTypeButton());
        }
        lastButton = pressedButton.getButtonCalculator();
    }

    private boolean isLastButtonNotOperation() {
        return !(lastButton == ButtonCalculator.KEY_DIVISION
                || lastButton == ButtonCalculator.KEY_MULTIPLICATION
                || lastButton == ButtonCalculator.KEY_SUBTRACT
                || lastButton == ButtonCalculator.KEY_ADD);
    }

    private void clear() {
        arg1 = 0;
        arg2 = 0;
        isDot = false;
        lastOperation = ButtonCalculator.NONE;
    }

    public static class Data implements Parcelable {
        private final double arg1;
        private final double arg2;
        private final boolean isDot;
        private final int countAfterDot;
        private final ButtonCalculator lastOperation;

        private final boolean isResultOnDisplay;
        private ButtonCalculator lastButton;

        public Data(double arg1, double arg2, boolean isDot, int countAfterDot, ButtonCalculator lastOperation,
                    boolean isResultOnDisplay, ButtonCalculator lastButton) {
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.isDot = isDot;
            this.countAfterDot = countAfterDot;
            this.lastOperation = lastOperation;
            this.isResultOnDisplay = isResultOnDisplay;
            this.lastButton = lastButton;
        }


        protected Data(Parcel in) {
            arg1 = in.readDouble();
            arg2 = in.readDouble();
            isDot = in.readByte() != 0;
            countAfterDot = in.readInt();
            lastOperation = ButtonCalculator.values()[in.readInt()];
            isResultOnDisplay = in.readByte() != 0;
            lastButton = ButtonCalculator.values()[in.readInt()];
        }

        public static final Creator<Data> CREATOR = new Creator<Data>() {
            @Override
            public Data createFromParcel(Parcel in) {
                return new Data(in);
            }

            @Override
            public Data[] newArray(int size) {
                return new Data[size];
            }
        };

        public double getArg1() {
            return arg1;
        }

        public double getArg2() {
            return arg2;
        }

        public boolean getIsDot() {
            return isDot;
        }

        public boolean getIsResultOnDisplay() {
            return isResultOnDisplay;
        }

        public int getCountAfterDot() {
            return countAfterDot;
        }

        public ButtonCalculator getLastOperation() {
            return lastOperation;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeDouble(arg1);
            parcel.writeDouble(arg2);
            parcel.writeByte((byte) (isDot ? 1 : 0));
            parcel.writeInt(countAfterDot);
            parcel.writeInt(lastOperation.ordinal());
            parcel.writeByte((byte) (isResultOnDisplay ? 1 : 0));
            parcel.writeInt(lastButton.ordinal());
        }

        public ButtonCalculator getLastButton() {
            return lastButton;
        }
    }
}
