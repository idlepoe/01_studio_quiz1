package lee.minnanoquiz;

import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String uid;
    User user;
    TextView txtUserName;

    Button btnAddQuestion;
    Button btnQuizList;
    Button btnQuiz;

    private DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        Intent intent = getIntent();

        String name = intent.getExtras().getString("user");

        user = new User();
        user.setUserName(name);

        txtUserName = findViewById(R.id.txtUserName);
        txtUserName.setText(name);

        btnAddQuestion = findViewById(R.id.btnAddQuestion);
        btnQuizList = findViewById(R.id.btnQuizList);
        btnQuiz = findViewById(R.id.btnQuiz);

        btnAddQuestion.setOnClickListener(this);
        btnQuizList.setOnClickListener(this);
        btnQuiz.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddQuestion:
                Intent intent=new Intent(MainActivity.this,AddQuestionActivity.class);
                intent.putExtra("user",this.user.getUserName());
                startActivity(intent);
                break;
            case R.id.btnQuizList:
                Intent intent1=new Intent(MainActivity.this,QuizListActivity.class);
                intent1.putExtra("user",this.user.getUserName());
                startActivity(intent1);
                break;
            case R.id.btnQuiz:
                Intent intent2=new Intent(MainActivity.this,QuizActivity.class);
                intent2.putExtra("user",this.user.getUserName());
                startActivity(intent2);
                break;


        }

    }
}
