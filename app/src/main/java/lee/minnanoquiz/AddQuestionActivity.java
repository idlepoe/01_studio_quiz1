package lee.minnanoquiz;

import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AddQuestionActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");
    private DatabaseReference wordRef = FirebaseDatabase.getInstance().getReference().child("words");
    private String uid;

    private User user;

    EditText editWord;
    EditText editPronounce;
    EditText editMeaning;
    EditText editComment;

    Boolean isQuiz;

    TextView txtNotification2;

    Button btnAddQuestion;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        uid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        Intent intent = getIntent();

        String name = intent.getExtras().getString("user");

        user = new User();
        user.setUserName(name);

        editWord = findViewById(R.id.editWord);
        editPronounce = findViewById(R.id.editPronounce);
        editMeaning = findViewById(R.id.editMeaning);
        editComment = findViewById(R.id.editComment);

        txtNotification2 = findViewById(R.id.txtNotification2);
        btnAddQuestion = findViewById(R.id.btnAddQuestion);
        btnBack = findViewById(R.id.btnBack);

        btnAddQuestion.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAddQuestion:
                if (editWord.getText().toString().length() == 0) {
                    txtNotification2.setText(txtNotification2.getText().toString().length() > 0 ? txtNotification2.getText().toString() + "単語を入力して下さい。" : "単語を入力して下さい。");
                }
                if (editPronounce.getText().toString().length() == 0) {
                    txtNotification2.setText(txtNotification2.getText().toString().length() > 0 ? txtNotification2.getText().toString() + "¥n読み方を入力して下さい。" : "読み方を入力して下さい。");
                }
                if (editMeaning.getText().toString().length() == 0) {
                    txtNotification2.setText(txtNotification2.getText().toString().length() > 0 ? txtNotification2.getText().toString() + "¥n意味を入力して下さい。" : "意味を入力して下さい。");
                }
                InsertQuiz();
                break;
            case R.id.btnBack:
                super.onBackPressed();
                break;
        }
    }

    void InsertQuiz() {
        isQuiz = false;
        //DatabaseReference targetWordRef = wordRef;
        wordRef.addListenerForSingleValueEvent(new ValueEventListener() {

                                                   @Override
                                                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                       Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();

                                                       while (child.hasNext()) {
                                                           String word = child.next().getKey();
                                                           System.out.println(word + " = " + editWord.getText().toString());
                                                           if (word.equals(editWord.getText().toString())) {
                                                               isQuiz = true;
                                                           }
                                                       }
                                                   }

                                                   @Override
                                                   public void onCancelled(@NonNull DatabaseError databaseError) {

                                                   }
                                               }
        );
        if (!isQuiz) {
            //wordRef.setValue(editWord.getText().toString());

            String key = wordRef.child(editWord.getText().toString()).push().getKey();
            Map<String, Object> childUpdates = new HashMap<>();

            childUpdates.put(editWord.getText().toString(), new Words(editWord.getText().toString(), editPronounce.getText().toString(), editMeaning.getText().toString(), editComment.getText().toString()
                    , uid, user.getUserName(), "20180901", 0, 0));
            //databaseReference.child("chat").child(CHAT_NAME).push().setValue(chat);
            wordRef.updateChildren(childUpdates);


        }

    }
}
