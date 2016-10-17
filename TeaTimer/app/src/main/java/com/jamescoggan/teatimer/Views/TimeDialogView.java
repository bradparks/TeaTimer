package com.jamescoggan.teatimer.Views;

import android.view.View;
import android.widget.TextView;

import com.jamescoggan.teatimer.R;
import com.jamescoggan.teatimer.models.Timer;

import java.util.Locale;

import butterknife.ButterKnife;

public class TimeDialogView {

    private TextView time;
    private String currentTime = "";

    public TimeDialogView(View view) {
        ButterKnife.findById(view, R.id.dialog_one).setOnClickListener(this::onNumberClick);
        ButterKnife.findById(view, R.id.dialog_two).setOnClickListener(this::onNumberClick);
        ButterKnife.findById(view, R.id.dialog_three).setOnClickListener(this::onNumberClick);
        ButterKnife.findById(view, R.id.dialog_four).setOnClickListener(this::onNumberClick);
        ButterKnife.findById(view, R.id.dialog_five).setOnClickListener(this::onNumberClick);
        ButterKnife.findById(view, R.id.dialog_six).setOnClickListener(this::onNumberClick);
        ButterKnife.findById(view, R.id.dialog_seven).setOnClickListener(this::onNumberClick);
        ButterKnife.findById(view, R.id.dialog_eight).setOnClickListener(this::onNumberClick);
        ButterKnife.findById(view, R.id.dialog_nine).setOnClickListener(this::onNumberClick);
        ButterKnife.findById(view, R.id.dialog_zero).setOnClickListener(this::onNumberClick);
        ButterKnife.findById(view, R.id.dialog_backspace).setOnClickListener(this::onBackspace);
        time = ButterKnife.findById(view, R.id.dialog_time_value);
    }

    public Timer getTimer() {
        Timer timer = new Timer();
        String s = fillWithZeros();
        int hours = Integer.parseInt(String.format("%s%s", s.charAt(5), s.charAt(4)));
        int minutes = Integer.parseInt(String.format("%s%s", s.charAt(3), s.charAt(2)));
        int seconds = Integer.parseInt(String.format("%s%s", s.charAt(1), s.charAt(0)));
        long totalTime = (hours * 3600 + minutes * 60 + seconds) * 1000;
        timer.setTime(totalTime);
        timer.setName(displayTime());
        return timer;
    }

    private void onNumberClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_one:
                appendNumber(1);
                break;
            case R.id.dialog_two:
                appendNumber(2);
                break;
            case R.id.dialog_three:
                appendNumber(3);
                break;
            case R.id.dialog_four:
                appendNumber(4);
                break;
            case R.id.dialog_five:
                appendNumber(5);
                break;
            case R.id.dialog_six:
                appendNumber(6);
                break;
            case R.id.dialog_seven:
                appendNumber(7);
                break;
            case R.id.dialog_eight:
                appendNumber(8);
                break;
            case R.id.dialog_nine:
                appendNumber(9);
                break;
            case R.id.dialog_zero:
                appendNumber(0);
                break;
        }
    }

    private void appendNumber(int number) {
        if (currentTime.length() < 6) {
            currentTime = number + currentTime;
            displayTime();
        }
    }

    private void onBackspace(View v) {
        if (currentTime.length() > 0) {
            currentTime = currentTime.substring(1);
        }
        displayTime();
    }

    private String displayTime() {
        String s = fillWithZeros();
        String displayName = String.format(Locale.UK, "%s%sh %s%sm %s%ss", s.charAt(5), s.charAt(4), s.charAt(3), s.charAt(2), s.charAt(1), s.charAt(0));
        time.setText(displayName);
        return displayName;
    }

    private String fillWithZeros() {
        String s = currentTime;
        int size = 6 - s.length();
        for (int i = 0; i < size; i++) {
            s = s + 0;
        }
        return s;
    }
}
