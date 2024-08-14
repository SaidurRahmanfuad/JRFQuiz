package com.example.saidur.supperquiz.presentation.views;

import static com.example.saidur.supperquiz.utils.Custom.disableClick;
import static com.example.saidur.supperquiz.utils.Custom.dlgExit;
import static com.example.saidur.supperquiz.utils.Custom.enaableClick;
import static com.example.saidur.supperquiz.utils.Custom.gotopage;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.saidur.supperquiz.R;
import com.example.saidur.supperquiz.databinding.ActivityQuestionAnswerPageBinding;
import com.example.saidur.supperquiz.databinding.PopCongratesBinding;
import com.example.saidur.supperquiz.databinding.PopExitBinding;
import com.example.saidur.supperquiz.domain.Model_Question;
import com.example.saidur.supperquiz.presentation.viewModel.Question_viewmodel;
import com.example.saidur.supperquiz.utils.AppSession;
import com.example.saidur.supperquiz.utils.Custom;
import com.google.gson.Gson;

import java.util.List;
import java.util.Objects;

public class Question_answer_page extends AppCompatActivity implements View.OnClickListener {
    ActivityQuestionAnswerPageBinding binding;
    CountDownTimer qsTimer;
    private Question_viewmodel qsViewModel;
    Gson gson;
    int currentQuestion = 0,init_progress=0;
    int ansQs, gainPoint=0;
    private List<Model_Question> question;
    AppSession session;
    Dialog dcongress,exitDialog;
    PopCongratesBinding pop_congress;
    PopExitBinding pop_exit;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityQuestionAnswerPageBinding.inflate(getLayoutInflater());
        pop_congress = PopCongratesBinding.inflate(getLayoutInflater());
        pop_exit = PopExitBinding.inflate(getLayoutInflater());
        pd=new ProgressDialog(this);
        session = new AppSession(this);
        if (session.getAnsweredQs() != 0) {
            ansQs = session.getAnsweredQs();
        } else {
            ansQs = 0;
        }
        exitDialog = new Dialog(this);
        dlgExit(exitDialog);

        setContentView(binding.getRoot());
        setUpOnBackPress();
        qsViewModel=new Question_viewmodel(this);

        //init congratulation custom dialog
        dcongress = new Dialog(Question_answer_page.this);
        dcongress.setContentView(pop_congress.getRoot());
        Objects.requireNonNull(dcongress.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dcongress.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
        dcongress.getWindow().getAttributes().gravity = Gravity.CENTER;

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getData();
        binding.top.ivBackBtn.setOnClickListener(v -> {
            gotopage(Question_answer_page.this,Main_menu_page.class);
            qsTimer.cancel();
            session.clear_saved();
            finish();
        });

    }

    //observe data from remote json
    public void getData() {
        pd.setTitle("Data Loading");
        pd.setMessage("Please wait...");
        pd.show();
        qsViewModel.getFinalQs().observe(this, modelQuiz -> {

            if(modelQuiz.getQuestions()!=null)
            {
                pd.dismiss();
                gson = new Gson();
                String data = gson.toJson(modelQuiz.getQuestions());
                Log.d("QS data", "getData: "+data);
                question = modelQuiz.getQuestions();
                setupViewData(modelQuiz.getQuestions());
            }
        });
    }

    //Setup ui datas
    private void setupViewData(List<Model_Question> questions) {
        if(!TextUtils.isEmpty(questions.get(currentQuestion).getQuestion()))
        {
            setupQsCountdown();
        }
        binding.layQs.tvCurrentQsNumber.setText((currentQuestion + 1) + "/" + questions.size());
        binding.layQs.tvQuestion.setText(questions.get(currentQuestion).getQuestion());
        binding.layQs.tvScore.setText(String.valueOf(questions.get(currentQuestion).getScore()));

        binding.layQs.answerLayout.tvAnswerA.setText("A. " + questions.get(currentQuestion).getAnswers().getA());
        binding.layQs.answerLayout.tvAnswerB.setText("B. " + questions.get(currentQuestion).getAnswers().getB());
        binding.layQs.answerLayout.tvAnswerC.setText("C. " + questions.get(currentQuestion).getAnswers().getC());
        binding.layQs.answerLayout.tvAnswerD.setText("D. " + questions.get(currentQuestion).getAnswers().getD());

        if (questions.get(currentQuestion).getQuestionImageUrl() != null && !questions.get(currentQuestion).getQuestionImageUrl().trim().isEmpty()) {
            Glide.with(this).load(questions.get(currentQuestion).getQuestionImageUrl()).into(binding.layQs.ivQsImage);
        } else {
            binding.layQs.ivQsImage.setImageResource(R.drawable.img_prev);
        }

    }

    //Manage click actions
    @Override
    public void onClick(View va) {
        if (va.getId() == R.id.tv_answer_a) {
            checkAnswer("A", binding.layQs.answerLayout.tvAnswerA);
        }
        if (va.getId() == R.id.tv_answer_b) {
            checkAnswer("B", binding.layQs.answerLayout.tvAnswerB);
        }
        if (va.getId() == R.id.tv_answer_c) {
            checkAnswer("C", binding.layQs.answerLayout.tvAnswerC);
        }
        if (va.getId() == R.id.tv_answer_d) {
            checkAnswer("D", binding.layQs.answerLayout.tvAnswerD);
        }

        if (va.getId() == R.id.btn_next) {
            binding.layQs.btnNext.setClickable(false);
            netxAction();
        }
        if (va.getId() == R.id.tv_exitYes) {
            session.clear_saved();
            gotopage(Question_answer_page.this, Main_menu_page.class);
            qsTimer.cancel();
            finish();
        }
        if (va.getId() == R.id.tv_exitNo) {
            exitDialog.dismiss();
        }

    }

    //Checking Answer is right or wrong
    private void checkAnswer(String ans, TextView tv) {
        disableClick(binding.layQs.answerLayout.tvAnswerA,
                binding.layQs.answerLayout.tvAnswerB,
                binding.layQs.answerLayout.tvAnswerC,
                binding.layQs.answerLayout.tvAnswerD);
        if (!ans.equals(question.get(currentQuestion).getCorrectAnswer())) {
            tv.setBackgroundResource(R.drawable.shape_wrong_ans);
            tv.setTextColor(getResources().getColor(R.color.white));
            showRightAns(question.get(currentQuestion).getCorrectAnswer());

        } else {
            tv.setBackgroundResource(R.drawable.shape_correct_ans);
            if (session.getGainedPoint() != 0) {
                gainPoint = session.getGainedPoint();
            }
            StoreScore(question.get(currentQuestion).getScore());
        }
    }

    //Store score,correct answer,current points to shared pref
    private void StoreScore(int achieveScore) {
        ansQs++;
        int currentScore=gainPoint+achieveScore;
        session.saveAnswerQs(ansQs);
        session.savePoint(currentScore);
        binding.top.tvCurrentScore.setText(String.valueOf(currentScore));
        if(session.getHighScore()<=currentScore)
        {
            session.saveHighscoreAppSession(currentScore);
        }

    }

    //Change background of Correct Answer
    private void showRightAns(String correctAnswer) {
        switch (correctAnswer) {
            case "A":
                binding.layQs.answerLayout.tvAnswerA.setBackgroundResource(R.drawable.shape_correct_ans);
                break;
            case "B":
                binding.layQs.answerLayout.tvAnswerB.setBackgroundResource(R.drawable.shape_correct_ans);
                break;
            case "C":
                binding.layQs.answerLayout.tvAnswerC.setBackgroundResource(R.drawable.shape_correct_ans);
                break;
            case "D":
                binding.layQs.answerLayout.tvAnswerD.setBackgroundResource(R.drawable.shape_correct_ans);
                break;
        }
    }


    //Question Timer progressbar
    private void setupQsCountdown(){
        binding.layQs.progress.setProgress(init_progress);
        qsTimer=new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                init_progress++;
                binding.layQs.progress.setProgress((int)init_progress*100/(10000/1000));
                binding.layQs.tvAnswerTime.setText(init_progress * 10 / (10000 / 1000) +"sec");
            }

            @Override
            public void onFinish() {
                binding.layQs.progress.setProgress(100);
                binding.layQs.btnNext.setClickable(false);
                netxAction();
            }
        };
        qsTimer.start();
    }

    //Handle next button click action
    private void netxAction(){
        qsTimer.cancel();
        init_progress=0;

        new Handler().postDelayed(() -> {
            if (currentQuestion != question.size() - 1) {
                currentQuestion = currentQuestion + 1;
                enaableClick(binding.layQs.answerLayout.tvAnswerA,
                        binding.layQs.answerLayout.tvAnswerB,
                        binding.layQs.answerLayout.tvAnswerC,
                        binding.layQs.answerLayout.tvAnswerD);


                binding.layQs.btnNext.setClickable(true);
                binding.layQs.answerLayout.tvAnswerA.setBackgroundResource(R.drawable.shape_sample_ans);
                binding.layQs.answerLayout.tvAnswerB.setBackgroundResource(R.drawable.shape_sample_ans);
                binding.layQs.answerLayout.tvAnswerC.setBackgroundResource(R.drawable.shape_sample_ans);
                binding.layQs.answerLayout.tvAnswerD.setBackgroundResource(R.drawable.shape_sample_ans);
                setupViewData(question);

            } else {
              // dcongress.show();
                dcongress.show();
               setupCongressPopup(question);
            }

        }, 2000);
    }

    //Setup congratulation dialog with correct answer and gained point
    private void setupCongressPopup(List<Model_Question> question) {
        pop_congress.tvCongressTag.setText("You have complete your Quiz. Correct answer " + String.valueOf(session.getAnsweredQs()) + "/" + String.valueOf(question.size()) + " and you earn " + String.valueOf(session.getGainedPoint()) + " coin");
        pop_congress.congressYes.setOnClickListener(v -> {
            session.clear_saved();
            Custom.gotopage(this, Main_menu_page.class);
            finish();
        });
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

