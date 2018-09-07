package com.example.zarkovic.testdesignpizzapro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText txt_useraname;
    EditText txt_password;

    FirebaseAuth FireBaseAuthorization;
    FirebaseUser firebaseUser;

    //firebase reference
    DatabaseReference rootReference;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dialog = new ProgressDialog(this);

        txt_useraname = (EditText) findViewById(R.id.txt_username);
        txt_password = (EditText) findViewById(R.id.txt_password);
        Button btn_register = (Button) findViewById(R.id.button_register);

        FireBaseAuthorization = FirebaseAuth.getInstance();

        //pointing to te root reference of an firebase database example: pizzaapp-ef707: null
        rootReference = FirebaseDatabase.getInstance().getReference();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });
    }

    private void createUser(){
        dialog.setMessage("Registering. Please wait Nenad");
        dialog.show();
        if(txt_useraname.getText().toString().trim().equals("")&&
                txt_password.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(), "Type some data", Toast.LENGTH_LONG).show();
            dialog.dismiss();
        }else{
            final String username = txt_useraname.getText().toString().trim();
            final String password = txt_password.getText().toString().trim();
            Log.i("user", username);
            Log.i("pass", password);
            FireBaseAuthorization.createUserWithEmailAndPassword(username,password).
                    addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "User created successfully", Toast.LENGTH_LONG).show();

                        //inserting data to firebase database
                        firebaseUser = FireBaseAuthorization.getCurrentUser();

                        User myUserInserObj = new User(username, password);
                        rootReference.child(firebaseUser.getUid()).setValue(myUserInserObj)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            dialog.dismiss();
                                            Toast.makeText(getApplicationContext(), "Values stored to database", Toast.LENGTH_LONG).show();
                                            Intent i = new Intent(RegisterActivity.this, UserProfileActivity.class);
                                            startActivity(i);
                                        }else{

                                            Toast.makeText(getApplicationContext(), "Values are not stored", Toast.LENGTH_LONG).show();
                                            dialog.dismiss();
                                        }
                                    }
                                });




                    }else{
                        dialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "User cannot be created", Toast.LENGTH_LONG).show();}
                }
            });
        }

    }
}
