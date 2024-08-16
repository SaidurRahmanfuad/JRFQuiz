package com.example.saidur.supperquiz.presentation.views;


import static com.example.saidur.supperquiz.utils.Custom.dlgExit;
import static com.example.saidur.supperquiz.utils.Custom.dlgStart;



import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.saidur.supperquiz.R;
import com.example.saidur.supperquiz.databinding.ActivityMainBinding;
import com.example.saidur.supperquiz.utils.AppSession;
import com.example.saidur.supperquiz.utils.Custom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Main_menu_page extends AppCompatActivity implements View.OnClickListener {
    ActivityMainBinding binding;
    AppSession session;
    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
    long milliseconds, startTime;
    CountDownTimer countTimer;
    Dialog startDialog, exitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        session = new AppSession(this);
        startDialog = new Dialog(this);
        exitDialog = new Dialog(this);
        dlgStart(startDialog);
        dlgExit(exitDialog);

        ShowHighScore(session.getHighScore());
        ShowCountDown();

        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        setUpOnBackPress();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.btnPlayNow.setOnClickListener(v -> {
            startDialog.show();
        });
    }

    //Quiz end countdown
    private void ShowCountDown() {
        formatter.setLenient(false);
        String endTime = "16.08.2024, 11:59:59";
        Date endDate;
        try {
            endDate = formatter.parse(endTime);
            milliseconds = endDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        startTime = System.currentTimeMillis();
        countTimer = new CountDownTimer(milliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                startTime = startTime - 1;
                long systemtimeSeconds = (millisUntilFinished - startTime) / 1000;

                long day2hour = (systemtimeSeconds % 86400) / 1440;
                long hour = (systemtimeSeconds % 86400) / 3600;
                long min = ((systemtimeSeconds % 86400) % 3600) / 60;
                long sec = ((systemtimeSeconds % 86400) % 3600) % 60;
                long totalHour = day2hour + hour;

                String hoursLeft = String.format("%d", totalHour);
                binding.tvHour.setText(hoursLeft);

                String minutesLeft = String.format("%d", min);
                binding.tvMinute.setText(minutesLeft);

                String secondsLeft = String.format("%d", sec);
                binding.tvSec.setText(secondsLeft);
            }

            @Override
            public void onFinish() {
                countTimer.cancel();
            }
        }.start();
    }

    //Show stored highscore from Shared pref
    private void ShowHighScore(int highScore) {
        if (highScore == 0) {
            binding.tvHighScore.setText("0");
        } else {
            binding.tvHighScore.setText(String.valueOf(highScore));
        }
    }

    //Manage click actions
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_startYes) {
            Custom.gotopage(this, Question_answer_page.class);
            startDialog.dismiss();
            finish();
        }
        if (v.getId() == R.id.tv_startNo) {
            startDialog.dismiss();
        }
        if (v.getId() == R.id.tv_exitYes) {
            exitDialog.dismiss();
            finish();
        }
        if (v.getId() == R.id.tv_exitNo) {
            exitDialog.dismiss();
        }
    }

    //Handle back press
    private void setUpOnBackPress() {
        this.getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                exitDialog.show();
            }
        });
    }
}
