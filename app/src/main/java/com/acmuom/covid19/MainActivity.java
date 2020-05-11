package com.acmuom.covid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView text;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference();
    private FirebaseFirestore firestoredb = FirebaseFirestore.getInstance();
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
        System.out.println(words);
        if(TextUtils.isEmpty(words)){
            Toast.makeText(this,"can't be blank",Toast.LENGTH_SHORT).show();
        }
        else{
//            putdata(words);
//            create();
//            read();
//            update("");
            delete("qQOqyzeE9n33yoQRSahW");
        }


    }

    private void putdata(final String words) {

//        System.out.println("PUt");
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

    private void create(){
        // Access a Cloud Firestore instance from your Activity
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);

// Add a new document with a generated ID
        firestoredb.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        System.out.println("done");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG, "Error adding document", e);
                        System.out.println("fail");
                    }
                });

    }

    private void read(){
        firestoredb.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println(document.getId() + " => " + document.getData());
                            }
                        } else {
                            System.out.println("Error getting documents." + task.getException());
                        }
                    }
                });
    }

    private void update(String docId){
        docId = "3vjixVvazBkLPKsbVqLv";
        Map<String, Object> user = new HashMap<>();
        user.put("first", "123");
        user.put("born", 9999);

        firestoredb.collection("users")
                .document(docId)
                .update(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                                System.out.println("done");

                        } else {
                            System.out.println("Error");
                        }
                    }
                });

    }

    private void delete(String docId){
        firestoredb.collection("users").document(docId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error deleting document");
                    }
                });
    }
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