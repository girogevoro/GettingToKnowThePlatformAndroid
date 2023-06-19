package com.girogevoro.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.girogevoro.model.CalculatorModel;
import com.girogevoro.model.CalculatorOperations;
import com.girogevoro.presenter.CalculatorPresenter;
import com.girogevoro.repository.Theme;
import com.girogevoro.repository.ThemesRepositoryImpl;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements CalculatorView {

    private static final String CALCULATOR_PRESENTER = "CalculatorPresenter";
    private static final String DATA = "data";
    private CalculatorPresenter mCalculatorPresenter;
    private CalculatorModel mCalculatorModel;
    private OnClickButtonListener mOnClickButtonListener;
    private TextView mDisplay;
    private DecimalFormat mDecimalFormat = new DecimalFormat("0.##########");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Theme nowTheme = ThemesRepositoryImpl.getInstance(this).getNow();
        setTheme(nowTheme.getIdTheme());
        setContentView(R.layout.activity_main);

        mDisplay = findViewById(R.id.display_calculator);


        mCalculatorModel = new CalculatorOperations();
        mCalculatorPresenter = new CalculatorPresenter(this, mCalculatorModel);
        setOnClickButtonListener(mCalculatorPresenter);
        initButtonListener();

        initButtonTheme();

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(DATA)) {
                mDisplay.setText(intent.getStringExtra(DATA));
            }
        }

    }

    private void initButtonTheme() {

        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Theme theme = (Theme) data.getSerializableExtra(SettingActivity.NEW_THEME);
                    ThemesRepositoryImpl.getInstance(MainActivity.this).setNow(theme);
                    recreate();
                }
            }
        });

        findViewById(R.id.key_theme).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                launcher.launch(intent);
            }
        });


    }

    private void initButtonListener() {

        Map<Integer, ButtonCalculator> map = new HashMap<>();
        initMapButtonCalculator(map);

        View.OnClickListener onClickDigital = view -> mOnClickButtonListener.click(
                new PressedButton(
                        ((ButtonCalculator) map.get(view.getId())),
                        TypeButtonCalculator.DIGITAL));

        View.OnClickListener onClickOperation = view -> mOnClickButtonListener.click(
                new PressedButton(
                        ((ButtonCalculator) map.get(view.getId())),
                        TypeButtonCalculator.OPERATION));
        View.OnClickListener onClickSing = view -> mOnClickButtonListener.click(
                new PressedButton(
                        ((ButtonCalculator) map.get(view.getId())),
                        TypeButtonCalculator.SING));

        View.OnClickListener onClickDot = view -> mOnClickButtonListener.click(
                new PressedButton(
                        ((ButtonCalculator) map.get(view.getId())),
                        TypeButtonCalculator.DOT));

        findViewById(R.id.key_1).setOnClickListener(onClickDigital);
        findViewById(R.id.key_2).setOnClickListener(onClickDigital);
        findViewById(R.id.key_3).setOnClickListener(onClickDigital);
        findViewById(R.id.key_4).setOnClickListener(onClickDigital);
        findViewById(R.id.key_5).setOnClickListener(onClickDigital);
        findViewById(R.id.key_6).setOnClickListener(onClickDigital);
        findViewById(R.id.key_7).setOnClickListener(onClickDigital);
        findViewById(R.id.key_8).setOnClickListener(onClickDigital);
        findViewById(R.id.key_9).setOnClickListener(onClickDigital);
        findViewById(R.id.key_0).setOnClickListener(onClickDigital);

        findViewById(R.id.key_division).setOnClickListener(onClickOperation);
        findViewById(R.id.key_multiplication).setOnClickListener(onClickOperation);
        findViewById(R.id.key_subtract).setOnClickListener(onClickOperation);
        findViewById(R.id.key_add).setOnClickListener(onClickOperation);
        findViewById(R.id.key_equally).setOnClickListener(onClickOperation);

        findViewById(R.id.key_sing).setOnClickListener(onClickSing);
        findViewById(R.id.key_dot).setOnClickListener(onClickDot);

    }

    private void initMapButtonCalculator(Map<Integer, ButtonCalculator> map) {
        map.put(R.id.key_0, ButtonCalculator.KEY_0);
        map.put(R.id.key_1, ButtonCalculator.KEY_1);
        map.put(R.id.key_2, ButtonCalculator.KEY_2);
        map.put(R.id.key_3, ButtonCalculator.KEY_3);
        map.put(R.id.key_4, ButtonCalculator.KEY_4);
        map.put(R.id.key_5, ButtonCalculator.KEY_5);
        map.put(R.id.key_6, ButtonCalculator.KEY_6);
        map.put(R.id.key_7, ButtonCalculator.KEY_7);
        map.put(R.id.key_8, ButtonCalculator.KEY_8);
        map.put(R.id.key_9, ButtonCalculator.KEY_9);

        map.put(R.id.key_division, ButtonCalculator.KEY_DIVISION);
        map.put(R.id.key_multiplication, ButtonCalculator.KEY_MULTIPLICATION);
        map.put(R.id.key_subtract, ButtonCalculator.KEY_SUBTRACT);
        map.put(R.id.key_add, ButtonCalculator.KEY_ADD);
        map.put(R.id.key_sing, ButtonCalculator.KEY_SING);
        map.put(R.id.key_equally, ButtonCalculator.KEY_EQUAL);

        map.put(R.id.key_dot, ButtonCalculator.KEY_DOT);
    }

    @Override
    public void showResult(double result) {
        mDisplay.setText(mDecimalFormat.format(result));
    }

    @Override
    public void setOnClickButtonListener(OnClickButtonListener listener) {
        mOnClickButtonListener = listener;
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCalculatorPresenter.setData(savedInstanceState.getParcelable(CALCULATOR_PRESENTER));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(CALCULATOR_PRESENTER, mCalculatorPresenter.getData());
    }
}