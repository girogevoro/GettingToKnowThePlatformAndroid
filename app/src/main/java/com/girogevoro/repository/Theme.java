package com.girogevoro.repository;

import com.girogevoro.view.R;

public enum Theme {
    MAIN(R.style.Theme_Calculator, R.string.theme_main,"Theme_main"),
    ONE(R.style.Theme_Calculator_V1,R.string.theme_One,"Theme_one"),
    TWO(R.style.Theme_Calculator_V2, R.string.theme_Two, "Theme_two"),
    THREE(R.style.Theme_Calculator_V3, R.string.theme_three,"Theme_three");

    private int idTheme;
    private int idTitle;
    private String keySave;

    Theme(int idTheme, int idTitle, String keySave) {
        this.idTheme = idTheme;
        this.idTitle = idTitle;
        this.keySave = keySave;
    }

    public int getIdTheme() {
        return idTheme;
    }

    public int getIdTitle() {
        return idTitle;
    }

    public String getKeySave() {
        return keySave;
    }
}
