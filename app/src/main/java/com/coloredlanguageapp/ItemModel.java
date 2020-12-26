package com.coloredlanguageapp;

import org.sufficientlysecure.htmltextview.HtmlTextView;

public class ItemModel {
    private String emoji, english, turkish, mean;

    public ItemModel() {
    }

    public ItemModel(String emoji, String english, String turkish, String mean) {
        this.emoji = emoji;
        this.english = english;
        this.turkish = turkish;
        this.mean = mean;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getTurkish() {
        return turkish;
    }

    public void setTurkish(String turkish) {
        this.turkish = turkish;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }
}
