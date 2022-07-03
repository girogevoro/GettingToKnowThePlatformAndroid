package com.girogevoro.repository;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Arrays;
import java.util.List;

public class ThemesRepositoryImpl implements ThemesRepository {


    private static final String KEY_THEME = "keyTheme";
    private static final String THEMES = "Themes";
    private static ThemesRepositoryImpl sThemesRepository;
    private SharedPreferences mSharedPreferences;

    public static ThemesRepositoryImpl getInstance(Context context) {
        if (sThemesRepository == null) {
            sThemesRepository = new ThemesRepositoryImpl(context);
        }
        return sThemesRepository;
    }

    private ThemesRepositoryImpl(Context context) {
        mSharedPreferences = context.getSharedPreferences(THEMES, Context.MODE_PRIVATE);
    }

    @Override
    public Theme getNow() {
        String keyTheme = mSharedPreferences.getString(KEY_THEME, Theme.MAIN.getKeySave());
        for (Theme theme : Theme.values()) {
            if (theme.getKeySave().equals(keyTheme)) {
                return theme;
            }
        }
        return Theme.MAIN;
    }

    @Override
    public void setNow(Theme theme) {
        mSharedPreferences.edit().putString(KEY_THEME, theme.getKeySave()).apply();
    }

    @Override
    public List<Theme> getAll() {
        return Arrays.asList(Theme.values());
    }
}
