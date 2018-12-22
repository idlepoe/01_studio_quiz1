package lee.minnanoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class AddQuestionActivity extends AppCompatActivity {

    EditText editWord;
    EditText editPronounce;
    EditText editMeaning;
    EditText editComment;

    Button btnAddQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        editWord = findViewById(R.id.editWord);
        editPronounce = findViewById(R.id.editPronounce);
    }
}