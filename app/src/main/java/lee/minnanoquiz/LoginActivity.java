package lee.minnanoquiz;

import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");
    private String uid;
    private boolean uidCheck;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        uid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
             System.err.print("test");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        this.isUser();

        if(!uidCheck){
            this.createUid();
        }else if(!user.getUserName().isEmpty()){
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            intent.putExtra("user",user.getUserName());
            startActivity(intent);
        }
    }

    private void createUid(){
        DatabaseReference targetUserRef = userRef.child(uid);
        targetUserRef.setValue(new User(uid,""));
    }

    private void isUser(){
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();

                while(child.hasNext())
                {
                    String targetId = child.next().getKey();
                    System.err.println(targetId);
                    if(targetId.equals(uid))
                    {
                        uidCheck = true;
                        DatabaseReference targetUserRef = userRef.child(uid);
;                       targetUserRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            user = dataSnapshot.getValue(User.class);
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
}
