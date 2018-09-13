package com.example.zarkovic.testdesignpizzapro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
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

public class LoginActivity extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    ImageButton sign_id_button;

    TextView txt_username;
    TextView txt_password;

    ProgressDialog dialog;

    FirebaseAuth fireBaseAuthorization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            //Hides top bar (title navigation bar)
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_login);

        fireBaseAuthorization = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);

        //google sign in button
        sign_id_button = (ImageButton) findViewById(R.id.google_sign_in_button);

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

        txt_username = (TextView) findViewById(R.id.txt_username);
        txt_password = (TextView) findViewById(R.id.txt_password);

        Button loginButton = (Button) findViewById(R.id.button_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setMessage("Logging in, please wait");
                dialog.show();
                if(txt_username.getText().toString().trim().equals("")&&
                        txt_password.getText().toString().trim().equals("")){
                    Toast.makeText(getApplicationContext(), "Type in some data", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }else{
                    final String username = txt_username.getText().toString().trim();
                    final String password = txt_password.getText().toString().trim();
                    fireBaseAuthorization.signInWithEmailAndPassword(username, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_LONG).show();

                                        Intent i = new Intent(LoginActivity.this, UserProfileActivity.class);
                                        startActivity(i);
                                        dialog.dismiss();
                                    }else{
                                        Toast.makeText(LoginActivity.this, "Wrong data entered", Toast.LENGTH_LONG).show();
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
        dialog.setMessage("Logging in, please wait");
        dialog.show();
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        fireBaseAuthorization.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = fireBaseAuthorization.getCurrentUser();
                            Toast.makeText(getApplicationContext(), "Signing in failed", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                            Intent i = new Intent(getApplicationContext(), UserProfileActivity.class);
                            startActivity(i);


                        } else {
                            Toast.makeText(getApplicationContext(), "Signing in failed", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
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
