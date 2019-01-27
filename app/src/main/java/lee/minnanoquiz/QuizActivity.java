package lee.minnanoquiz;

import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.otto.ThreadEnforcer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");
    private DatabaseReference wordRef = FirebaseDatabase.getInstance().getReference().child("words");

    private String uid;
    private User user;
    Timer timer;
    TextView txtTimer;
    TextView txtQuiz;
    ProgressBar progressTimer;

    public List<Words> wordsList;

    private Integer countdownValue;

    Button btnBack;

    public static Bus bus;

    Integer correctIndex;

    Integer answerCorrectIdx;

    Button btnAnswer1;
    Button btnAnswer2;
    Button btnAnswer3;

    Integer cnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        uid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        wordsList = new ArrayList<Words>();

        btnBack = findViewById(R.id.btnBack);
        progressTimer = findViewById(R.id.progressTimer);
        txtQuiz = findViewById(R.id.txtQuiz);


        btnAnswer1 = findViewById(R.id.btnAnswer1);
        btnAnswer2 = findViewById(R.id.btnAnswer2);
        btnAnswer3 = findViewById(R.id.btnAnswer3);

        progressTimer.setMax(10);
        btnBack.setOnClickListener(this);

        btnAnswer1.setOnClickListener(this);
        btnAnswer2.setOnClickListener(this);
        btnAnswer3.setOnClickListener(this);

        txtTimer = findViewById(R.id.txtTimer);
        bus = new Bus(ThreadEnforcer.MAIN);
        bus.register(this);
cnt =0;
        getQuizList();


        this.time();
    }

    void time() {

        countdownValue = getCountdownTime();

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressTimer.setProgress(countdownValue);
                        if(cnt==1){
                            DatabaseReference targetUserRef = userRef.child(uid);
                            if(user!=null) {
                                user.setPoint((user.getPoint() == null ? 0 : user.getPoint()) + 1);
                                targetUserRef.setValue(user);
                            }
                            cnt=0;
                        }

                        if ((txtQuiz.getText() + "").equalsIgnoreCase("textView") && wordsList.size() > 0) {
                            quizStart();
                        }

                        txtTimer.setText("" + countdownValue);
                        countdownValue--;
                        if (countdownValue <= 0) {

                            quizStart();
                        }
                    }
                });
            }
        }, 0, 1000);
    }

    void quizStart() {
        cnt=0;
        countdownValue = getCountdownTime();
        Random rand = new Random();

        correctIndex = rand.nextInt((wordsList.size()));
        Integer incorrectIndex1 = rand.nextInt((wordsList.size() - correctIndex));
        Integer incorrectIndex2 = rand.nextInt((wordsList.size() - correctIndex));

        txtQuiz.setText(wordsList.get(correctIndex).getWord());

        answerCorrectIdx = rand.nextInt(4);

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
            default:
                // ??
        }
    }

    int getCountdownTime() {

        int result = 10;

        return result;
    }

    private void userPointUp() {

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();

                while (child.hasNext()) {
                    String targetId = child.next().getKey();
                    System.out.println(targetId + " = " + uid);
                    if (targetId.equals(uid)) {

                        DatabaseReference targetUserRef = userRef.child(uid);


                        targetUserRef.addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                user = dataSnapshot.getValue(User.class);
                                pointup(user);




                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    void pointup(User user1){
        user = user1;
        cnt=1;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                super.onBackPressed();
                break;
            case R.id.btnAnswer1:
                if(wordsList.get(correctIndex).getMeaning().equalsIgnoreCase(btnAnswer1.getText()+"")){
                    userPointUp();
                }
                quizStart();
                break;
            case R.id.btnAnswer2:
                if(wordsList.get(correctIndex).getMeaning().equalsIgnoreCase(btnAnswer2.getText()+"")){
                    userPointUp();
                }
                quizStart();
                break;
            case R.id.btnAnswer3:
                if(wordsList.get(correctIndex).getMeaning().equalsIgnoreCase(btnAnswer3.getText()+"")){
                    userPointUp();
                }
                quizStart();
                break;
        }

    }

    @Subscribe
    public void nextMethod(Words o) {
        wordsList.add(o);
    }

}
