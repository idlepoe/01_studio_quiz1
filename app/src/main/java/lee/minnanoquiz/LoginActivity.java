package lee.minnanoquiz;

import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference userRef = FirebaseDatabase.getInstance().getReference();
    private String uid;
    private List<User> targetList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String uid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        targetList= new ArrayList<>();

        if(!this.isUser()){
            this.createUid();
        }
    }
    private void createUid(){
        userRef.push().setValue(uid);
    }

    private boolean isUser(){
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<User> userList= new ArrayList<>();

                for(DataSnapshot noteSnapshot : dataSnapshot.getChildren()){
                    userList.add(noteSnapshot.getValue(User.class));
                }
                for(User target:userList) {
                    if (target.getUserId().equals(uid)) {
                        targetList.add(target);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return targetList.size()>0;
    }
}
