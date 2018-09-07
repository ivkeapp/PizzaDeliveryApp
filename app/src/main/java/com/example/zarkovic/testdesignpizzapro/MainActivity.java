package com.example.zarkovic.testdesignpizzapro;

import android.app.ProgressDialog;
import android.content.Intent;
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

    GoogleSignInClient mGoogleSignInClient;
    SignInButton sign_id_button;

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

        txt_useraname = (EditText) findViewById(R.id.txt_username);
        txt_password = (EditText) findViewById(R.id.txt_password);
        Button btn_login = (Button) findViewById(R.id.button_login);
        Button btn_register = (Button) findViewById(R.id.button_register);

        sign_id_button = (SignInButton) findViewById(R.id.google_sign_in_button);

        sign_id_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

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
                dialog.setMessage("Logging in, please wait");
                dialog.show();
                if(txt_useraname.getText().toString().trim().equals("")&&
                        txt_password.getText().toString().trim().equals("")){
                    Toast.makeText(getApplicationContext(), "Type in some data", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }else{
                    final String username = txt_useraname.getText().toString().trim();
                    final String password = txt_password.getText().toString().trim();
                    FireBaseAuthorization.signInWithEmailAndPassword(username, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_LONG).show();
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
                                                    Intent i = new Intent(MainActivity.this, UserProfileActivity.class);
                                                    startActivity(i);
                                                }else{

                                                    Toast.makeText(getApplicationContext(), "Values are not stored", Toast.LENGTH_LONG).show();
                                                    dialog.dismiss();
                                                }
                                            }
                                        });
                                Intent i = new Intent(MainActivity.this, UserProfileActivity.class);
                                startActivity(i);
                                dialog.dismiss();
                            }else{
                                Toast.makeText(MainActivity.this, "Wrong data entered", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        }

                    });
            }
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 100);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        FireBaseAuthorization.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = FireBaseAuthorization.getCurrentUser();
                            finish();
                            Intent i = new Intent(getApplicationContext(), UserProfileActivity.class);
                            startActivity(i);


                        } else {
                            // If sign in fails, display a message to the user.

                        }

                        // ...
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately

                // ...
            }
        }
    }
}
