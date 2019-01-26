package lee.minnanoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class QuizListActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference wordRef = FirebaseDatabase.getInstance().getReference().child("words");

    Button btnBack;
    private ListView quiz_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_list);
        quiz_view = findViewById(R.id.quiz_view);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(this);

        getQuizList();
    }


    private void getQuizList() {

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        quiz_view.setAdapter(adapter);

        wordRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Words word = dataSnapshot.getValue(Words.class);
                adapter.add(word.getWord() + " : " + word.getMeaning()+":" + word.getRegYMD() + ":"+ word.getCorrect() + "|"+word.getIncorrect());
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
