package com.girogevoro.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.girogevoro.repository.Theme;
import com.girogevoro.repository.ThemesRepositoryImpl;

public class SettingActivity extends AppCompatActivity {

    public static final String NEW_THEME = "newTheme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        LinearLayout container = findViewById(R.id.container);
        for (Theme theme : ThemesRepositoryImpl.getInstance(this).getAll()) {
            View line = getLayoutInflater().inflate(R.layout.line_theme, container, false);
            TextView title = line.findViewById(R.id.titleTheme);
            CheckBox check = line.findViewById(R.id.check);
            title.setText(theme.getIdTitle());
            if (theme.equals(ThemesRepositoryImpl.getInstance(this).getNow())) {
                check.setChecked(true);

            }
            container.addView(line);
            check.setOnClickListener((view -> result(theme)));
        }
    }

    private void result(Theme theme) {
        Intent data = new Intent();
        data.putExtra(NEW_THEME, theme);
        setResult(Activity.RESULT_OK, data);
        finish();
    }
}