package lee.minnanoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddQuestionActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");
    private DatabaseReference wordRef = FirebaseDatabase.getInstance().getReference().child("words");

    EditText editWord;
    EditText editPronounce;
    EditText editMeaning;
    EditText editComment;

    TextView txtNotification2;

    Button btnAddQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        editWord = findViewById(R.id.editWord);
        editPronounce = findViewById(R.id.editPronounce);
        editMeaning = findViewById(R.id.editMeaning);
        editComment = findViewById(R.id.editComment);

        txtNotification2 = findViewById(R.id.txtNotification2);

        btnAddQuestion.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAddQuestion:
                if(editWord.getText().toString().length()==0){
                    txtNotification2.setText(txtNotification2.getText().toString().length()>0?txtNotification2.getText().toString()+"単語を入力して下さい。":"単語を入力して下さい。");
                }
                if(editPronounce.getText().toString().length()==0){
                    txtNotification2.setText(txtNotification2.getText().toString().length()>0?txtNotification2.getText().toString()+"¥n読み方を入力して下さい。":"読み方を入力して下さい。");
                }
                if(editMeaning.getText().toString().length()==0){
                    txtNotification2.setText(txtNotification2.getText().toString().length()>0?txtNotification2.getText().toString()+"¥n意味を入力して下さい。":"意味を入力して下さい。");
                }
            break;
        }
    }
}
