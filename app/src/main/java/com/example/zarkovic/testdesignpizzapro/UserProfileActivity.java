package com.example.zarkovic.testdesignpizzapro;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity {
    FirebaseAuth fba;
    FirebaseUser user;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        fba = FirebaseAuth.getInstance();

        user = fba.getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("Pizza");

        //reference = FirebaseDatabase.getInstance().getReference();

        //String s = reference.child("name").toString();
        //Log.i("stringdb", s);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<Pizza> pizzas =  new ArrayList<>();

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    String s = ds.child("name").getValue().toString();
                    Log.i("cheese", s);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Button btn_logout = (Button) findViewById(R.id.button_logout);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fba.signOut();
                finish();

                Intent i = new Intent(UserProfileActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

    }
}
