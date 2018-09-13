package com.example.zarkovic.testdesignpizzapro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_main);
        FireBaseAuthorization = FirebaseAuth.getInstance();

        dialog = new ProgressDialog(this);

        try
        {
            //Hides top bar (title navigation bar)
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_main);

        //Setting background color to black
        View v = (View) findViewById(R.id.theme);
        v.setBackgroundColor(Color.rgb(25, 25, 25));


        Button btn_login = (Button) findViewById(R.id.button_login);
        Button btn_register = (Button) findViewById(R.id.button_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog.setMessage("Please wait...");
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                dialog.dismiss();
            }
        });
    }

}
