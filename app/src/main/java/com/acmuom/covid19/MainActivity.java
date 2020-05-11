package com.acmuom.covid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView text;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference();
//            .getRoot();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
////        DatabaseReference md;
//        Date date = new Date();//date
//        String stringDate = formatter.format(date);
////        System.out.println(formatter.format(date));

//        writeNewUser( "date", "name", "email");


         button = findViewById(R.id.button);
         text= findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createnew();
            }
        });




    }

    private void createnew() {

        String words = text.getText().toString();
        if(TextUtils.isEmpty(words)){
            Toast.makeText(this,"can't be blank",Toast.LENGTH_SHORT).show();
        }
        else{
            putdata(words);

        }


    }

    private void putdata(final String words) {


        final DatabaseReference dr;
        dr = FirebaseDatabase.getInstance().getReference();
        dr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.child("users").child(words).exists())
                {
                    HashMap<String,Object> map = new HashMap<>();
                    map.put("name",words);

                    dr.child("users").child(words).
                            updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_SHORT).show();
                            }
                            else{

                            }
                        }
                    });
                }
                else{

                    Toast.makeText(MainActivity.this,"alredy exists   ",Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

//    private void writeNewUser(String userId, String name, String email) {
//        User user = new User(name, email);
//
//        root.child("users").child(userId).setValue(user);
//    }

}






//@IgnoreExtraProperties
//class User {
//
//    public String username;
//    public String email;
//
//    public User() {
//        // Default constructor required for calls to DataSnapshot.getValue(User.class)
//    }
//
//    public User(String username, String email) {
//        this.username = username;
//        this.email = email;
//    }
//
//}