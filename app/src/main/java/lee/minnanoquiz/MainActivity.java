package lee.minnanoquiz;

import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    String uid;
    User user;
    TextView txtUserName;
    private DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        Intent intent = getIntent();

        String name = intent.getExtras().getString("user");

        txtUserName = findViewById(R.id.txtUserName);
        txtUserName.setText(name);
    }



}
