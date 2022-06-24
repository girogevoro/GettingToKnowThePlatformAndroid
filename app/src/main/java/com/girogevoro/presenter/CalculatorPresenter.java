package com.girogevoro.presenter;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

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
    private CalculatorView mCalculatorView;
    private Map<ButtonCalculator, Integer> mapButtonsDigital = new HashMap<>();

    public CalculatorPresenter(CalculatorView calculatorView) {
        mCalculatorView = calculatorView;
        mCalculatorView.setOnClickButtonListener(this);
        initMapButtonsDigital();
        mCalculatorView.showResult(arg2);
    }

    public void setData(CalculatorPresenter.Data data) {
        if (data != null) {
            arg1 = data.getArg1();
            arg2 = data.getArg2();
            isDot = data.getIsDot();
            countAfterDot= data.getCountAfterDot();
            mCalculatorView.showResult(arg2);
        }
    }

    public CalculatorPresenter.Data getData() {
        return new CalculatorPresenter.Data(arg1, arg2, isDot, countAfterDot);
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
                if (isDot) {
                    countAfterDot--;
                    arg2 = arg2 + Math.pow(10, countAfterDot) * (Integer) mapButtonsDigital.get(pressedButton.getButtonCalculator());
                } else {
                    arg2 = arg2 * 10 + (Integer) mapButtonsDigital.get(pressedButton.getButtonCalculator());
                }

                Log.d("viewCalculator", "click: digital" + pressedButton.getButtonCalculator());
                mCalculatorView.showResult(arg2);
                break;
            case OPERATION:
                Log.d("viewCalculator", "click: operation" + pressedButton.getButtonCalculator());
                arg1 = arg2;
                arg2 = 0;
                isDot = false;
                countAfterDot = 0;
                break;
            case DOT:
                Log.d("viewCalculator", "click: dot " + pressedButton.getButtonCalculator());
                isDot = true;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + pressedButton.getTypeButton());
        }
    }

    public static class Data implements Parcelable {
        private final double arg1;
        private final double arg2;
        private final boolean isDot;
        private  final int countAfterDot;

        public Data(double arg1, double arg2, boolean isDot, int countAfterDot) {
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.isDot = isDot;
            this.countAfterDot = countAfterDot;
        }

        protected Data(Parcel in) {
            arg1 = in.readDouble();
            arg2 = in.readDouble();
            isDot = in.readByte() != 0;
            countAfterDot = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeDouble(arg1);
            dest.writeDouble(arg2);
            dest.writeByte((byte) (isDot ? 1 : 0));
            dest.writeInt(countAfterDot);
        }

        @Override
        public int describeContents() {
            return 0;
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

        public int getCountAfterDot() {
            return countAfterDot;
        }
    }
}
