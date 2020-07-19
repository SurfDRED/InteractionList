package com.example.interactionlist;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataList {
    static List<Map<String, String>> content = new ArrayList<>();
    static final String TITLE = "title";
    static final String SUBTITLE = "subtitle";
    String sText;    //текст сохраненный в SharedPreferences

    void updateList() {
        String[] title = prepareContent();
        Map<String, String> titleText;
        for (int i = 0; i < title.length; i++) {
            titleText = new HashMap<>();
            titleText.put(TITLE, title[i]);
            titleText.put(SUBTITLE, String.valueOf(title[i].length()));
            content.add(titleText);
        }
    }

    @NonNull
    private String[] prepareContent() {
        return sText.split("\n\n ");
    }
}