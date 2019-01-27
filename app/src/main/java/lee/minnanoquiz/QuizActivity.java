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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");
    private DatabaseReference wordRef = FirebaseDatabase.getInstance().getReference().child("words");

    private String uid;
    private User user;
    Timer timer;
    TextView txtTimer;
    ProgressBar progressTimer;

    List<Words> wordsList;

    private Integer countdownValue;

    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        uid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        wordsList = new ArrayList<>();

        btnBack = findViewById(R.id.btnBack);
        progressTimer = findViewById(R.id.progressTimer);
        progressTimer.setMax(10);
        btnBack.setOnClickListener(this);

        txtTimer = findViewById(R.id.txtTimer);
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
                        txtTimer.setText("" + countdownValue);
                        countdownValue--;
                        if (countdownValue <= 0) {
                            countdownValue = getCountdownTime();
                            //quizStart();
                        }
                    }
                });
            }
        }, 0, 1000);
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
                                System.err.println(user);
                                DatabaseReference targetUserRef = userRef.child(uid);
user.setPoint(user.getPoint()+1);
                                targetUserRef.setValue(user);

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

    private void getQuizList() {

        wordRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Words word = dataSnapshot.getValue(Words.class);
                wordsList.add(word);
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
        switch (v.getId()){
            case R.id.btnBack:
                super.onBackPressed();
                break;
        }

    }
}
