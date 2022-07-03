package com.girogevoro.repository;
import java.util.List;

public interface ThemesRepository {
    Theme getNow();
    void setNow(Theme theme);
    List<Theme> getAll();
}
