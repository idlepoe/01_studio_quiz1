package lee.minnanoquiz;

import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.otto.ThreadEnforcer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");
    private DatabaseReference targetUserRef;
    private DatabaseReference targetUserPointRef;
    private DatabaseReference wordRef = FirebaseDatabase.getInstance().getReference().child("words");

    private String uid;
    User user;

    Timer timer;
    TextView txtTimer;
    TextView txtQuiz;
    ProgressBar progressTimer;

    TextView txtUserName;
    ProgressBar prgScore;
    TextView txtPoint;

    public List<Words> wordsList;

    private Integer countdownValue;

    Button btnBack;

    public static Bus bus;

    Integer correctIndex;

    Integer answerCorrectIdx;

    Button btnAnswer1;
    Button btnAnswer2;
    Button btnAnswer3;


    Integer score;

    ImageView imgMaru;
    ImageView imgBachi;

    boolean result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        uid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        targetUserRef = userRef.child(uid);
        targetUserPointRef = targetUserRef.child("point");

        user = new User();
        wordsList = new ArrayList<Words>();

        // ヘッダーID
        txtUserName = findViewById(R.id.txtUserName);
        prgScore = findViewById(R.id.prgScore);
        txtPoint = findViewById(R.id.txtPoint);

        // クイズID
        txtQuiz = findViewById(R.id.txtQuiz);

        progressTimer = findViewById(R.id.progressTimer);

        // タイマーID
        txtTimer = findViewById(R.id.txtTimer);

        // 正当、バツのイメージID
        imgMaru = findViewById(R.id.imgMaru);
        imgBachi = findViewById(R.id.imgBachi);

        // 正当ボタン１，２，３
        btnAnswer1 = findViewById(R.id.btnAnswer1);
        btnAnswer2 = findViewById(R.id.btnAnswer2);
        btnAnswer3 = findViewById(R.id.btnAnswer3);

        // 戻るボタン
        btnBack = findViewById(R.id.btnBack);

        txtUserName.setText(uid);


        prgScore.setMax(100);

        // クイズの制限
        progressTimer.setMax(10);

        //戻るボタンのリスナー
        btnBack.setOnClickListener(this);

        // 正当ボタンのリスナー
        btnAnswer1.setOnClickListener(this);
        btnAnswer2.setOnClickListener(this);
        btnAnswer3.setOnClickListener(this);


        bus = new Bus(ThreadEnforcer.MAIN);
        bus.register(this);

        score = 0;
        getUserInfo();
        getQuizList();

        this.repeatQuiz();
    }

    void repeatQuiz() {

        countdownValue = 10;

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressTimer.setProgress(countdownValue);

                        if (countdownValue <= 9) {
                            imgMaru.setVisibility(View.INVISIBLE);
                            imgBachi.setVisibility(View.INVISIBLE);
                        }

                        if ((txtQuiz.getText() + "").equalsIgnoreCase("textView") && wordsList.size() > 0) {
                            makeQuiz();
                        }

                        txtTimer.setText("" + countdownValue);
                        countdownValue--;
                        if (countdownValue <= 0) {
                            makeQuiz();
                        }
                    }
                });
            }
        }, 0, 1000);
    }

    void makeQuiz() {
        countdownValue = 10;
        Random rand = new Random();

        correctIndex = rand.nextInt(wordsList.size()) + 0;

        Integer incorrectIndex1 = rand.nextInt(wordsList.size()-1 - 0 + 1) + 0;
        Integer incorrectIndex2 = rand.nextInt((wordsList.size()-1 - 0 + 1)) + 0;

        while (incorrectIndex1 == correctIndex) {
            incorrectIndex1 = rand.nextInt((wordsList.size()-1 - 0 + 1)) + 0;
        }

        while (incorrectIndex2 == correctIndex || incorrectIndex2 == incorrectIndex1) {
            incorrectIndex2 = rand.nextInt((wordsList.size()-1 - 0 + 1)) + 0;
        }

        txtQuiz.setText(wordsList.get(correctIndex).getWord());
        answerCorrectIdx = rand.nextInt(3 - 1 + 1) + 1;

        switch (answerCorrectIdx) {
            case 1:
                btnAnswer1.setText(wordsList.get(correctIndex).getMeaning());
                btnAnswer2.setText(wordsList.get(incorrectIndex1).getMeaning());
                btnAnswer3.setText(wordsList.get(incorrectIndex2).getMeaning());
                break;
            case 2:
                btnAnswer2.setText(wordsList.get(correctIndex).getMeaning());
                btnAnswer1.setText(wordsList.get(incorrectIndex1).getMeaning());
                btnAnswer3.setText(wordsList.get(incorrectIndex2).getMeaning());
                break;
            case 3:
                btnAnswer3.setText(wordsList.get(correctIndex).getMeaning());
                btnAnswer2.setText(wordsList.get(incorrectIndex1).getMeaning());
                btnAnswer1.setText(wordsList.get(incorrectIndex2).getMeaning());
                break;
        }
    }

    public void getUserInfo() {
        targetUserRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //user = dataSnapshot.getValue(User.class);
                switch (dataSnapshot.getKey()){
                    case "point":
                        txtPoint.setText(dataSnapshot.getValue()+"");
                        prgScore.setProgress(Integer.parseInt(dataSnapshot.getValue()+""));
                        break;
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                switch (dataSnapshot.getKey()){
                    case "point":
                        txtPoint.setText(dataSnapshot.getValue()+"");
                        prgScore.setProgress(Integer.parseInt(dataSnapshot.getValue()+""));
                        break;
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getQuizList() {
        wordRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Words word = dataSnapshot.getValue(Words.class);
                bus.post(word);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    void setPoint(){
        targetUserPointRef.setValue(Integer.parseInt(txtPoint.getText()+"")+1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                super.onBackPressed();
                break;
            case R.id.btnAnswer1:
                if (wordsList.get(correctIndex).getMeaning().equalsIgnoreCase(btnAnswer1.getText() + "")) {
                    result = true;
                    setPoint();
                }
            case R.id.btnAnswer2:
                if (wordsList.get(correctIndex).getMeaning().equalsIgnoreCase(btnAnswer2.getText() + "")) {
                    result = true;
                    setPoint();
                }
            case R.id.btnAnswer3:
                if (wordsList.get(correctIndex).getMeaning().equalsIgnoreCase(btnAnswer3.getText() + "")) {
                    result = true;
                    setPoint();
                }

                if (result) {
                    imgMaru.setVisibility(View.VISIBLE);
                    score = score + 1;
                    prgScore.setProgress(score);
                } else {
                    result = false;
                    imgBachi.setVisibility(View.VISIBLE);
                }
                makeQuiz();
        }
    }

    @Subscribe
    public void nextMethod(Words o) {
        wordsList.add(o);
    }

}
